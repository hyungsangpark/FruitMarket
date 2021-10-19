package com.example.fruitmarket;

import com.example.fruitmarket.fruit.Apple;
import com.example.fruitmarket.fruit.Blueberry;
import com.example.fruitmarket.fruit.Feijoa;
import com.example.fruitmarket.fruit.Fruit;
import com.example.fruitmarket.fruit.Kiwifruit;
import com.example.fruitmarket.fruit.Orange;
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
