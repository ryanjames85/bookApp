package utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.devstream.booksapplication.app.PayPalActivity;
import com.devstream.booksapplication.app.R;
import constants.Constant;
import models.Book;

/**
 * Created by Barry Dempsey on 20/08/15.
 * Utility class to present an Alert Dialog
 * to the user.
 */
public class AlertUtil {
    private static final String KEY_STRING_ORDERS = "books_order";
    private static int counter = 0;
    private static double totalPrice = 0;

    /**
     * Show an Alert Dialog to the User
     * while giving them options to purchaase or
     * continue shopping.
     * @param context
     * @param title
     * @param book
     */

    public static void showAlertToUser(final Context context,
                                       int title, final Book book) {
        counter = BooksSingleton.getSingleton().getBooksOrder().size();
        final Dialog dialog = new Dialog(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_dialog, null);
        dialog.setContentView(view);
        dialog.setTitle(title);
        ((TextView) view.findViewById(R.id.text)).setText(book.toString() + " \n"
                + (double)book.getPrice()/100+ " " + BooksSingleton.getSingleton().getUSER_CURRENCY_PREFERENCE());
        ((TextView) view.findViewById(R.id.text_alert)).setText(R.string.OK_message);
        Button okBtn = (Button)view.findViewById(R.id.ok_button);
        Button addBtn = (Button)view.findViewById(R.id.cancel_button);
        okBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                totalPrice += (double)book.getPrice()/100;
                context.startActivity(setIntent(context, book));
                dialog.dismiss();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                /**
                 * user wants to continue shopping
                 * add book to their current order
                 * and alert them
                 */

                if(BooksSingleton.getSingleton().addToOrdersList(book)){
                    totalPrice += (double)book.getPrice()/100;
                    String currCode = BooksSingleton.getSingleton().getUSER_CURRENCY_PREFERENCE();
                    String title = book.getTitle();
                    String bookDetail = title.substring(0, title.length()-5)
                            + ". . . \n " + (double)book.getPrice()/100
                            + " " + currCode;
                    new ToastAlert(context, Constant.ORDER_ADDED + "\n " + bookDetail , true);
                }else{
                    /**
                     * just for demo purposes we will allow one title per user
                     */
                    new ToastAlert(context, Constant.ORDER_ALREADY_ADDED, true);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Create an Intent Object to start PayPal
     * and pass the price for check-out
     * @param context
     * @param book
     * @return
     */
    private static Intent setIntent(Context context, Book book) {
        Intent i = new Intent(context, PayPalActivity.class);
        i.putExtra(Constant.PACKAGE_NAME+"book_price", DecimalPlacer.round(totalPrice, 2));
        i.putExtra(Constant.PACKAGE_NAME+"book_details", book.titleAuthorToString());
        return i;
    }
}