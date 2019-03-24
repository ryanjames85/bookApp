package com.devstream.booksapplication.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import constants.Constant;
import utils.*;

/**
 * Created by Barry Dempsey on 19/08/15.
 */
public class SplashActivity extends Activity implements LocationListener {
    private static final String IRE = "Ireland";
    private static final String UK = "United Kingdom";
    private static final String US = "United States";
    private ProgressBar progBar;
    private String API = Constant.BASE_URL;
    private LocationManager locationManager;
    private String provider;
    private String country;
    private String currency;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progBar = (ProgressBar)findViewById(R.id.prog_bar);
        progBar.setVisibility(View.VISIBLE);
        setUpLocationServices();

        APICaller.makeApiCall(SplashActivity.this, API);

        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent i = new Intent(SplashActivity.this, BookListActivity.class);
                startActivity(i);
            }
        }.start();

        /**
         * let the user know that their currency has been set
         */
        if (InternetConnectivityChecker.isNetworkAvailableAndConnected(this)) {
            new ToastAlert(context, Constant.WELCOME_NOTE + country +
                    "\n Preferred currency has been changed to " + currency, true );
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        /**
         * use Location Listener to find what country
         * the user is in and set the currency accordingly
         */
        try {
            country = CountryFinder.getCountryName(context, location.getLatitude(), location.getLongitude());
            currency = null;
            if(country != null) {
                switch (country){
                    case IRE: // user is in Ireland set currency to EUR
                        BooksSingleton.getSingleton().setUSER_CURRENCY_PREFERENCE(Constant.EURO);
                        currency = Constant.EURO;
                        break;
                    case US : // user is in the US set currency to USD
                        BooksSingleton.getSingleton().setUSER_CURRENCY_PREFERENCE(Constant.USD);
                        currency = Constant.USD;
                        break;
                    case UK : // user is in UK set currency to GBP
                        BooksSingleton.getSingleton().setUSER_CURRENCY_PREFERENCE(Constant.GBP);
                        currency = Constant.GBP;
                        break;
                }

            }else {
                new ToastAlert(context, Constant.NO_INTERNET_WARNING , true);
                BooksSingleton.getSingleton().setInternetConnection(false);
            }
        }catch (NullPointerException npe){
            new ToastAlert(context, Constant.NO_INTERNET_WARNING , true);

        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void setUpLocationServices() {
        locationManager = (LocationManager) getSystemService(SplashActivity.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if(provider!=null && !provider.equals("")) {
            /*
            Get the location from the given provider
            We need this so we can set the right currency
             */
            Location location = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 20000, 1.0f, this);
            if(location != null)
                onLocationChanged(location);
            else
                new ToastAlert(context, Constant.LOCATION_WARNING, true);
        }else {
            Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
        }
    }
}
