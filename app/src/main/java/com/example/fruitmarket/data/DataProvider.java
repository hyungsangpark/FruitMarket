package com.example.fruitmarket.data;

import androidx.annotation.NonNull;

import com.example.fruitmarket.models.Apple;
import com.example.fruitmarket.models.Blueberry;
import com.example.fruitmarket.models.Category;
import com.example.fruitmarket.models.Feijoa;
import com.example.fruitmarket.models.Fruit;
import com.example.fruitmarket.models.Kiwifruit;
import com.example.fruitmarket.models.Orange;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProvider {

    public DataProvider(){

    }

    public List<Fruit> getMostPopular() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Fruit> fruitCategoryClasses = new HashMap<>();
        fruitCategoryClasses.put("apples", new Apple());
        fruitCategoryClasses.put("blueberries", new Blueberry());
        fruitCategoryClasses.put("feijoas", new Feijoa());
        fruitCategoryClasses.put("kiwifruits", new Kiwifruit());
        fruitCategoryClasses.put("oranges", new Orange());

        Map<String, Fruit> fruitsMap = new HashMap<>();
        fruitsMap.put("", new Fruit() {
            @Override
            public List<String> getAttributeNames() {
                return null;
            }

            @Override
            public List<String> getAttributeValues() {
                return null;
            }
        });

        for (String collection : fruitsMap.keySet()) {
            db.collection(collection).get().addOnCompleteListener(
                    (@NonNull Task<QuerySnapshot> task) -> {
                if (task.isSuccessful()) {
                    for (Fruit fruit : task.getResult().toObjects(
                            fruitCategoryClasses.get(collection).getClass())) {
                        fruitsMap.put(fruit.getCategory(), fruit);
                    }
                }
            });
        }

        List<Fruit> allFruits = (List<Fruit>)fruitsMap.values();
        allFruits.sort(Comparator.comparing(Fruit::getPopularity).reversed());
        return allFruits.subList(0, 10); // Return top 10 popular fruits.
    }


    public List<Fruit> getFruitsGivenCategory(Category category) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<Long, Fruit> fruitsMap = new HashMap<>();
        fruitsMap.put(0L, new Fruit() {
            @Override
            public List<String> getAttributeNames() {
                return null;
            }

            @Override
            public List<String> getAttributeValues() {
                return null;
            }
        });

        Map<String, Fruit> fruitCategoryClasses = new HashMap<>();
        fruitCategoryClasses.put("apples", new Apple());
        fruitCategoryClasses.put("blueberries", new Blueberry());
        fruitCategoryClasses.put("feijoas", new Feijoa());
        fruitCategoryClasses.put("kiwifruits", new Kiwifruit());
        fruitCategoryClasses.put("oranges", new Orange());

        db.collection(category.getCategoryName()).get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {
            if (task.isSuccessful()) {
                for (Fruit fruit : task.getResult().toObjects(fruitCategoryClasses.get(category.getCategoryName()).getClass())) {
                    fruitsMap.put(fruit.getId(), fruit);
                }
            }
        });
        fruitsMap.remove(0L);

        List<Fruit> fruitsList = new ArrayList<Fruit>(fruitsMap.values());
        return fruitsList;
    }

    public List<Fruit> getMatchingSearchTerm(String searchTerm, ArrayList<String> filterCategories) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Fruit> fruitCategoryClasses = new HashMap<>();
        fruitCategoryClasses.put("apples", new Apple());
        fruitCategoryClasses.put("blueberries", new Blueberry());
        fruitCategoryClasses.put("feijoas", new Feijoa());
        fruitCategoryClasses.put("kiwifruits", new Kiwifruit());
        fruitCategoryClasses.put("oranges", new Orange());

        Map<String, Fruit> fruitsMap = new HashMap<>();
        fruitsMap.put("", new Fruit() {
            @Override
            public List<String> getAttributeNames() {
                return null;
            }

            @Override
            public List<String> getAttributeValues() {
                return null;
            }
        });

        if (filterCategories.isEmpty()){
            for (String collection : fruitsMap.keySet()) {
                db.collection(collection).get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {
                    if (task.isSuccessful()) {
                        for (Fruit fruit : task.getResult().toObjects(fruitCategoryClasses.get(collection).getClass())) {
                            if ((fruit.getName().equals(searchTerm)) || (fruit.getVariety().equals(searchTerm)) || (fruit.getProducer().equals(searchTerm))){
                                fruitsMap.put(fruit.getCategory(), fruit);
                            }
                        }
                    }
                });
            }
            return new ArrayList<Fruit>(fruitsMap.values());
        }

        for (String filter : filterCategories){
            db.collection(filter).get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {
                if (task.isSuccessful()) {
                    for (Fruit fruit : task.getResult().toObjects(fruitCategoryClasses.get(filter).getClass())) {
                        if ((fruit.getName().equals(searchTerm)) || (fruit.getVariety().equals(searchTerm)) || (fruit.getProducer().equals(searchTerm))){
                            fruitsMap.put(fruit.getCategory(), fruit);
                        }
                    }
                }
            });
        }

        fruitsMap.remove("");

        return new ArrayList<Fruit>(fruitsMap.values());
    }

    public List<Category> getFruitCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Apples"));
        categoryList.add(new Category("Blueberries"));
        categoryList.add(new Category("Feijoas"));
        categoryList.add(new Category("Kiwifruits"));
        categoryList.add(new Category("Oranges"));
        return categoryList;
    }
}