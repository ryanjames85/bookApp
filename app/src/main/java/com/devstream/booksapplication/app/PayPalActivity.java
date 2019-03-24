package com.devstream.booksapplication.app;

/**
 * Created by Barry Dempsey on 20/08/15.
 * PayPal Activity facilitates the payment of
 * a product via the PayPal SDK
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.paypal.android.sdk.payments.*;
import constants.Constant;
import org.json.JSONException;
import org.json.JSONObject;
import utils.BooksSingleton;
import utils.ToastAlert;

import java.math.BigDecimal;

public class PayPalActivity extends Activity implements OnClickListener{
    private ProgressBar progBar;
    private Button btnPay;
    private String payState;
    private String paymentId;
    private double price;
    private String bookDetails;

    //set the environment for production/sandbox/no netowrk
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    private static final String CONFIG_CLIENT_ID = Constant.CLIENT_ID;

    private static final int REQUEST_PAYPAL_PAYMENT = 1;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
                    // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Book-a-Babysitter")
            .merchantPrivacyPolicyUri(Uri.parse("http://bookababysitter.ie/"))
            .merchantUserAgreementUri(Uri.parse("http://bookababysitter.ie/"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.layout_paypal);
        price = getIntent().getDoubleExtra(Constant.PACKAGE_NAME+"book_price", 0.0);
        bookDetails = getIntent().getStringExtra(Constant.PACKAGE_NAME+"book_details");

        /**
         * call pay pal services
         */

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        CountDownTimer timer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(price),"EUR", bookDetails,
                        PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent = new Intent(PayPalActivity.this, PaymentActivity.class);

                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

                startActivityForResult(intent, REQUEST_PAYPAL_PAYMENT);
            }
        }.start();

    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()){
            case R.id.search_button :
//                PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(6.50),"EUR", "www.bookababysitter.ie",
//                        PayPalPayment.PAYMENT_INTENT_SALE);
//
//                Intent intent = new Intent(PayPalActivity.this, PaymentActivity.class);
//
//                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
//
//                startActivityForResult(intent, REQUEST_PAYPAL_PAYMENT);

                /**
                new AlertDialog.Builder(PayPalActivity.this)
                        .setTitle("Book-a-Babysitter")
                        .setMessage("Proceed with PayPal payment ?")
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        }).create().show();
                 **/
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PAYPAL_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        System.out.println("Response" + confirm);
                        Log.i("paymentExample", confirm.toJSONObject().toString());

                        JSONObject jsonObj=new JSONObject(confirm.toJSONObject().toString());

                        payState = jsonObj.getJSONObject("response").getString("state");
                        paymentId = jsonObj.getJSONObject("response").getString("id");
                        new ToastAlert(PayPalActivity.this, Constant.THANKS_FOR_ORDER, true);
                        BooksSingleton.getSingleton().getBooksOrder().clear();
                        waitBeforeFinish();

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
                Toast.makeText(getApplicationContext(), "Payment Canceled", Toast.LENGTH_LONG).show();
                finish();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment was submitted. Please see the docs.");
                Toast.makeText(getApplicationContext(), "Invalid Payment Submitted", Toast.LENGTH_LONG).show();
                waitBeforeFinish();
            }
        }
    }

    private void waitBeforeFinish() {
        CountDownTimer timer = new CountDownTimer(3 * 1000, 1 * 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

