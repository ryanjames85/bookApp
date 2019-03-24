package models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by johndoe on 18/08/15.
 * Model class Book stores details
 * for each book
 */


public class Book implements Serializable {

    @Expose
    private Integer id;
    @Expose
    private String title;
    @Expose
    private String isbn;
    @Expose
    private Integer price;
    @Expose
    private String currencyCode;
    @Expose
    private String author;

    public Book(String author) {
        this.author = author;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     *
     * @param isbn
     * The isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     *
     * @return
     * The price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     *
     * @param currencyCode
     * The currencyCode
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     *
     * @return
     * The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     *
     * @param author
     * The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return " \n\n" + title + " \nby " + author;
    }

    public String titleAuthorToString() {
        return "\n" + title + " \n" + author;
    }

}
