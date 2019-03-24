package utils;

import constants.Constant;
import models.Book;

/**
 * Created by Barry Dempsey on 21/08/15.
 * Util class to convert to the user's
 * chosen currency
 */
public class CurrencyConverter {

    public static double convert(Book book, final String CURRENCY_REQUIRED) {
        double price = 0.0;
        switch (CURRENCY_REQUIRED){
            case Constant.EURO :
                price = (double)book.getPrice()/100;
                break;
            case Constant.GBP :
                price = (((double)book.getPrice()) * 1.3998)/100;
                break;
            case Constant.USD :
                price = (((double)book.getPrice()) * 0.8923)/100;
        }
        price = DecimalPlacer.round(price, 2);
        return price;
    }
}
