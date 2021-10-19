package com.example.fruitmarket.util;

import com.example.fruitmarket.models.Fruit;

import java.util.ArrayList;
import java.util.List;

public class SearchUtil {
    public SearchUtil() {}

    public List<Fruit> search(String searchArg, List<Fruit> fruits) {
        List<Fruit> searchResult = new ArrayList<>();

        for (Fruit fruit : fruits) {
            if (fruit.getName().matches("(.*)" + searchArg + "(.*)")) {
                searchResult.add(fruit);
            }
        }

        return searchResult;
    }
}
