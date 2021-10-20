package com.example.fruitmarket.data;

import com.example.fruitmarket.models.Apple;
import com.example.fruitmarket.models.Blueberry;
import com.example.fruitmarket.models.Feijoa;
import com.example.fruitmarket.models.Fruit;
import com.example.fruitmarket.models.Kiwifruit;
import com.example.fruitmarket.models.Orange;
import java.util.List;

public class SearchDataProvider {

    private static final String[] collections = {"apples", "blueberries", "feijoas", "kiwifruits", "oranges"};
    private static final String[] fruitCategories = {"Apple", "Blueberry", "Feijoa", "Kiwifruit", "Orange"};
    private static final Fruit[] fruitCategoryInstances = {new Apple(), new Blueberry(), new Feijoa(), new Kiwifruit(), new Orange()};

    public static String[] getCollections() {
        return collections;
    }

    public static Fruit[] getFruitCategoryInstances() {
        return fruitCategoryInstances;
    }

    public static String[] getFruitCategories() {
        return fruitCategories;
    }

    public static List<String> retrieveSearchKeywords() {
        return null;
    }

}
