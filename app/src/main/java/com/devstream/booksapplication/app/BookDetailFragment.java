package com.devstream.booksapplication.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import constants.Constant;
import models.Book;
import utils.AlertUtil;
import utils.BooksSingleton;
import utils.CurrencyConverter;

/**
 * A fragment representing a single Book detail screen.
 * This fragment is either contained in a {@link BookListActivity}
 * in two-pane mode (on tablets) or a {@link BookDetailActivity}
 * on handsets.
 */
public class BookDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The Books content this fragment is presenting.
     */
    private Book book;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the data content specified by the fragment
            // arguments.
            Integer id  = getArguments().getInt(ARG_ITEM_ID);
            book = BooksSingleton.getSingleton().getBOOK_MAP().get(id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book_detail, container, false);

        // Show the book content as text in a TextView.
        if (book != null) {
            ((TextView) rootView.findViewById(R.id.book_detail)).setText(book.toString());
            ((TextView) rootView.findViewById(R.id.book_sub_detail)).setText("ISBN "
                    + book.getIsbn()
                    + " \nItem ID: "
                    + book.getId());
            Button payButton = (Button)rootView.findViewById(R.id.pay_button);
            Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            payButton.startAnimation(shake);


            String USER_CURRENCY_PREF = BooksSingleton.getSingleton().getUSER_CURRENCY_PREFERENCE();
            double price = CurrencyConverter.convert(book, USER_CURRENCY_PREF);
            payButton.setText(String.valueOf(price).substring(0,5) + " " + Constant.EURO);
            payButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    AlertUtil.showAlertToUser(getActivity(), R.string.payment, book);
                }
            });
        }
        return rootView;
    }
}
