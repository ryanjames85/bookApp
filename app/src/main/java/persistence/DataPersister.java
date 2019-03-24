package persistence;

import android.content.Context;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import constants.Constant;
import models.Book;

import java.util.List;

/**
 * Created by johndoe on 23/08/15.
 */
public class DataPersister {

    public static void setStoredBooks(Context context, List<Book> books, final String keyString) {
        Gson gson = new Gson();
        String json = gson.toJson(books);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(Constant.PACKAGE_NAME+keyString, json)
                .apply();
    }

    public static List<Book> getStoredBooks(Context context, final String keyString){
        Gson gson = new Gson();
        String json = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constant.PACKAGE_NAME+keyString, "");
        final List<Book> books = gson.fromJson(json, List.class);
        return books;
    }
}
