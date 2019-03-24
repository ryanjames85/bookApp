package utils;

import constants.Constant;
import models.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Barry Dempsey on 18/08/15.
 */
public class BooksSingleton {
    private static BooksSingleton singleton;
    private List<Book> books = new ArrayList<Book>();
    private List<Book> booksOrder = new ArrayList<>();
    private Map<Integer, Book> BOOK_MAP = new HashMap<Integer, Book>();
    private String USER_CURRENCY_PREFERENCE = Constant.EURO; // default is EURO
    private boolean isDownloaded = false;
    private boolean internetConnection = false;
    private boolean twoPaneDevice = false;

    private BooksSingleton() {}

    public static synchronized BooksSingleton getSingleton() {
        if(singleton == null){
            singleton = new BooksSingleton();
        }
        return singleton;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }


    public Map<Integer, Book> getBOOK_MAP() {
        return BOOK_MAP;
    }

    public void setBOOK_MAP(Map<Integer, Book> BOOK_MAP) {
        this.BOOK_MAP = BOOK_MAP;
    }


    public String getUSER_CURRENCY_PREFERENCE() {
        return USER_CURRENCY_PREFERENCE;
    }

    public void setUSER_CURRENCY_PREFERENCE(String USER_CURRENCY_PREFERENCE) {
        this.USER_CURRENCY_PREFERENCE = USER_CURRENCY_PREFERENCE;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setIsDownloaded(boolean isDownloaded) {
        this.isDownloaded = isDownloaded;
    }


    public boolean isInternetConnection() {
        return internetConnection;
    }

    public void setInternetConnection(boolean internetConnection) {
        this.internetConnection = internetConnection;
    }


    public List<Book> getBooksOrder() {
        return booksOrder;
    }

    public void setBooksOrder(List<Book> booksOrder) {
        this.booksOrder = booksOrder;
    }

    public boolean addToOrdersList(Book book) {
        if(!booksOrder.contains(book)) {
            booksOrder.add(book);
            return true;
        }
        return false;
    }

    public List<Book>showAllOrders(){
        return booksOrder;
    }


    public boolean isTwoPaneDevice() {
        return twoPaneDevice;
    }

    public void setTwoPaneDevice(boolean twoPaneDevice) {
        this.twoPaneDevice = twoPaneDevice;
    }

}
