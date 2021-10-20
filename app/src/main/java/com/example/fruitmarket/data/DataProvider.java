package com.example.fruitmarket.data;

import com.example.fruitmarket.models.Category;
import com.example.fruitmarket.models.Fruit;

import java.util.ArrayList;

public class DataProvider {

    public DataProvider(){

    }

    public ArrayList<Fruit> getMostPopular() {
        ArrayList<Fruit> sortedByPopularity = new ArrayList<Fruit>();
        return sortedByPopularity;
    }

    public ArrayList<Category> getCategoriesList() {
        ArrayList<Category> categories = new ArrayList<Category>();
        return categories;
    }
}