package persistence;

import android.content.Context;
import android.util.Log;
import models.Book;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.BooksSingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barry Dempsey on 24/08/15.
 * Maps Object of type Book from JSONArray
 * retrieved from persistent data storage
 */
public class ObjectMapper {

    public static void mapJSONToObject(Context context, String KEY_STRING_BOOKS)  {
        final List<Book> books = DataPersister.getStoredBooks(context, KEY_STRING_BOOKS);
        final List<Book> bookList = new ArrayList<>();
        JSONArray array = new JSONArray(books);
        Book book = null;
        for(int i = 0; i < array.length(); i++){
            try {
                JSONObject obj = array.getJSONObject(i);
                String author = obj.getString("author");
                String title = obj.getString("title");
                String currencyCode = obj.getString("currencyCode");
                Integer id = obj.getInt("id");
                String isbn = obj.getString("isbn");
                Integer price = obj.getInt("price");
                book = new Book(author);
                book.setCurrencyCode(currencyCode);
                book.setId(id);
                book.setIsbn(isbn);
                book.setTitle(title);
                book.setPrice(price);
                bookList.add(book);
                Log.d("JSON", author + title + price + isbn + id + currencyCode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        /**
         * update the user singleton so they can view
         * the books list off-line if no internet
         */
        BooksSingleton.getSingleton().setBooks(bookList);
        Log.d("BOOK_SINGLE", BooksSingleton.getSingleton().getBooks().toString());
    }
}
