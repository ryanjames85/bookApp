package utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.devstream.booksapplication.app.R;


public class ToastAlert{
	private TextView tv;
	private View layout;
    private LinearLayout toastLayout;
	private ProgressBar bar;

	public ToastAlert(Context context, String message, boolean hasMessage) {
		Toast ImageToast = new Toast(context);
	    toastLayout = new LinearLayout(
	            context);
	    toastLayout.setOrientation(LinearLayout.HORIZONTAL);
	    toastLayout.setBackgroundColor(context.getResources().getColor(R.color.black));
	    ImageView image = new ImageView(context);
	    TextView tv = new TextView(context);
	    tv.setTextColor(context.getResources().getColor(R.color.cool_grey));
	    tv.setText(" " + message);
		tv.setPadding(0,8,2,6);
		tv.setTextSize(16.0f);
        tv.setTypeface(null, Typeface.BOLD);
	    image.setImageResource(R.mipmap.ic_launcher);
	    ImageToast.setGravity(Gravity.BOTTOM, 0, 100);
		bar = new ProgressBar(context);
		if(hasMessage) {
			toastLayout.addView(bar);
			toastLayout.addView(tv);
			toastLayout.addView(image);
		}else {
			toastLayout.addView(bar);
		}
	    ImageToast.setView(toastLayout);
	    ImageToast.setDuration(Toast.LENGTH_LONG);
	    ImageToast.show();
	}

    public void hideTheBanner() {
        toastLayout.setVisibility(View.INVISIBLE);
    }
}
