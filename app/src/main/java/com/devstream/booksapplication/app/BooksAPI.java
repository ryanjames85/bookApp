package com.devstream.booksapplication.app;

import models.Book;
import retrofit.Callback;
import retrofit.http.GET;

import java.util.List;

/**
 * Created by johndoe on 18/08/15.
 */
public interface BooksAPI {
    @GET("/books")

    void getBooksList(Callback<List<Book>> response);
    //response is the response from the server which is now in the POJO
}
