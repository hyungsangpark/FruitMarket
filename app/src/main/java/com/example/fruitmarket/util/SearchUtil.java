package com.example.fruitmarket.util;

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
