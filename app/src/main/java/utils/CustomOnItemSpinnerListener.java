package utils;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Barry Dempsey on 21/08/15.
 */
public class CustomOnItemSpinnerListener implements AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        /**
         * used to set the currency preference of this user.
         */
        BooksSingleton.getSingleton().setUSER_CURRENCY_PREFERENCE(adapterView.getItemAtPosition(i).toString());

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
