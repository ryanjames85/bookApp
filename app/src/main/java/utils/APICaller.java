package utils;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import com.devstream.booksapplication.app.BookListFragment;
import com.devstream.booksapplication.app.BooksAPI;
import constants.Constant;
import models.Book;
import persistence.DataPersister;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;

/**
 * Created by johndoe on 18/08/15.
 */
public class APICaller {
    private static final String TAG = "API_CONNECTIVITY";
    private static final String KEY_STRING_BOOKS = "books_list";

    public static void makeApiCall(final Context context, String API){

        if(!InternetConnectivityChecker.isNetworkAvailableAndConnected(context)) {
            return;
        }

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API)
                .build();

        BooksAPI booksAPI = restAdapter.create(BooksAPI.class);
        booksAPI.getBooksList(new Callback<List<Book>>() {
            @Override
            public void success(List<Book> books, Response response) {
                Log.d(TAG, books.toString());
                BooksSingleton.getSingleton().setBooks(books);
                /*
                update the book list with the response
                from the API call
                 */
                final ArrayAdapter<Book>adapter = BookListFragment.adapter;
                if(adapter != null){
                    adapter.addAll(books);
                    adapter.notifyDataSetChanged();
                    BooksSingleton.getSingleton().setIsDownloaded(true);
                }

                /*
                create a hashmap of books with id key
                 */
                BooksSingleton.getSingleton().setBOOK_MAP(MapMaker.createMap(books));
                BooksSingleton.getSingleton().setIsDownloaded(true);
                DataPersister.setStoredBooks(context, BooksSingleton.getSingleton().getBooks(), KEY_STRING_BOOKS );

                Log.d(TAG, DataPersister.getStoredBooks(context, KEY_STRING_BOOKS).toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, Constant.NO_INTERNET_WARNING);
                new ToastAlert(context, Constant.NO_INTERNET_WARNING, true);
                BooksSingleton.getSingleton().setIsDownloaded(false);
            }
        });
    }
}
