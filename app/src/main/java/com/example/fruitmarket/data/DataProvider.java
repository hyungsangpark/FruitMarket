package com.example.fruitmarket.data;

import android.util.Log;

import com.example.fruitmarket.models.Category;
import com.example.fruitmarket.models.Fruit;
import com.example.fruitmarket.models.IProduct;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataProvider {

    private final int NUM_CATEGORIES = 5;
    private final String[] collections = {"apples", "blueberries", "feijoas", "kiwifruits", "oranges"};
    private final String[] fruitCategories = {"Apple", "Blueberry", "Feijoa", "Kiwifruit", "Orange"};

    private Map<String, List<Fruit>> fruitsMap;

    private static DataProvider instance;

    public static DataProvider getInstance() {
        if (instance == null) {
            instance = new DataProvider();
        }
        return instance;
    }

    private DataProvider() {
    }

    public void provideData(Map<String, List<Fruit>> fruitsMap) {
        this.fruitsMap = fruitsMap;
    }

    public List<Fruit> getFruitsOfCategory(String category) {
        return fruitsMap.get(category);
    }

    public List<Fruit> getFruitsFromSearchTerm(String searchTerm, List<String> filters) {
        List<Fruit> fruitsList = new ArrayList<>();
        //with no filter
        if (filters.isEmpty()) {
            return findUsingSearchTerm(searchTerm);
        } else {
            for (String filter : filters) {
                List<Fruit> fruitsOfFilterCategory = fruitsMap.get(filter);
                for (Fruit fruit : fruitsOfFilterCategory) {
                    if ((fruit.getName().equals(searchTerm)) || (fruit.getVariety().equals(searchTerm)) || (fruit.getProducer().equals(searchTerm))) {
                        fruitsList.add(fruit);
                    }
                }
            }
        }
        return fruitsList;
    }

    private List<Fruit> findUsingSearchTerm(String searchTerm) {
        List<Fruit> fruitsList = new ArrayList<>();
        for (List<Fruit> fullFruitsList : fruitsMap.values()) {
            for (Fruit fruit : fullFruitsList) {
                if ((fruit.getName().equals(searchTerm)) || (fruit.getVariety().equals(searchTerm)) || (fruit.getProducer().equals(searchTerm))) {
                    fruitsList.add(fruit);
                }
            }
        }
        return fruitsList;
    }

    public List<Category> getFruitCategories() {
        return Arrays.stream(collections).map(Category::new).collect(Collectors.toList());
    }

    private String retrieveCollectionName(String categoryName) {
        for (int i = 0; i < NUM_CATEGORIES; i++) {
            if (fruitCategories[i].equals(categoryName)) {
                return collections[i];
            }
        }
        return "";
    }

    public void updatePopularityToFirestore(IProduct fruit) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Convert getCategory() to collection name.
        String collectionName = retrieveCollectionName(fruit.getCategory());
        db.collection(collectionName)
                .document(collectionName + fruit.getId())
                .update("popularity", fruit.getPopularity())
                .addOnSuccessListener(unused -> Log.d(collectionName + " Collection Update",
                        "Popularity updated to " + fruit.getPopularity() + "."))
                .addOnFailureListener(e -> Log.w(fruit.getCategory() + " Collection Update",
                        "Popularity could not be updated to " + fruit.getPopularity() + "."));
    }
}