package com.devstream.booksapplication.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import persistence.DataPersister;
import persistence.ObjectMapper;
import utils.BooksSingleton;
import utils.InternetConnectivityChecker;


/**
 * An activity representing a list of Books. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BookDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link BookListFragment} and the item details
 * (if present) is a {@link BookDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link BookListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class BookListActivity extends FragmentActivity
        implements BookListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private static final String KEY_STRING_ORDERS = "books_order";
    private static final String KEY_STRING_BOOKS = "books_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        if (findViewById(R.id.book_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
            BooksSingleton.getSingleton().setTwoPaneDevice(true);

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
                    ((BookListFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.book_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataPersister.setStoredBooks(this, BooksSingleton.getSingleton().getBooks(), KEY_STRING_BOOKS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!InternetConnectivityChecker.isNetworkAvailableAndConnected(this)) {
            /**
             * no internet connection or airplane mode
             * so retrieve previous data if we have it
             */
            if(DataPersister.getStoredBooks(this, KEY_STRING_BOOKS) != null) {
                ObjectMapper.mapJSONToObject(this, KEY_STRING_BOOKS);
            }
        }
    }

    /**
     * Callback method from {@link BookListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(Integer id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(BookDetailFragment.ARG_ITEM_ID, id);
            BookDetailFragment fragment = new BookDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.book_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, BookDetailActivity.class);
            detailIntent.putExtra(BookDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    @Override
    public void onBackPressed() {
        /**
         * go back Home on back press
         */
        DataPersister.setStoredBooks(this, BooksSingleton.getSingleton().getBooks(), KEY_STRING_BOOKS);
        moveTaskToBack(true);
    }
}
