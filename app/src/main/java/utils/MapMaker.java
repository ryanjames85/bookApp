package utils;

import models.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johndoe on 19/08/15.
 */
public class MapMaker {

    public static <T> Map createMap(List<T> listItems) {
        Map<Integer, Book>map = new HashMap<Integer, Book>();
        for(T item : listItems){
            Book b = (Book)item;
            map.put(b.getId(), b);
        }
        return map;
    }
}
