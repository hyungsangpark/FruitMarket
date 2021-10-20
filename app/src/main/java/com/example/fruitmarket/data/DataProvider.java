package com.example.fruitmarket.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fruitmarket.models.Apple;
import com.example.fruitmarket.models.Blueberry;
import com.example.fruitmarket.models.Category;
import com.example.fruitmarket.models.Feijoa;
import com.example.fruitmarket.models.Fruit;
import com.example.fruitmarket.models.IProduct;
import com.example.fruitmarket.models.Kiwifruit;
import com.example.fruitmarket.models.Orange;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProvider {

    public DataProvider() {

    }

    public List<Fruit> getMostPopular() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Fruit> fruitCategoryClasses = new HashMap<>();
        fruitCategoryClasses.put("apples", new Apple());
        fruitCategoryClasses.put("blueberries", new Blueberry());
        fruitCategoryClasses.put("feijoas", new Feijoa());
        fruitCategoryClasses.put("kiwifruits", new Kiwifruit());
        fruitCategoryClasses.put("oranges", new Orange());

        Map<String, List<Fruit>> fruitsMap = new HashMap<>();

        for (Category collection : getFruitCategories()) {
            fruitsMap.put(collection.getCategoryName(), new ArrayList<>());
            db.collection(collection.getCategoryName()).get().addOnCompleteListener(
                    (@NonNull Task<QuerySnapshot> task) -> {
                        if (task.isSuccessful()) {
                            for (Fruit fruit : task.getResult().toObjects(fruitCategoryClasses.get(collection.getCategoryName()).getClass())) {
                                Log.d("FruitFetched", collection.getCategoryName() + ": " + fruit);
                                fruitsMap.get(collection.getCategoryName()).add(fruit);
                            }
                        }
                    });
        }

        List<Fruit> allFruits = new ArrayList<>();
        for (List<Fruit> category : fruitsMap.values()) {
            allFruits.addAll(category);
        }
        allFruits.sort(Comparator.comparing(Fruit::getPopularity).reversed());
        Log.d("getMostPopular", "allFruits (" + allFruits.size() + "): " + allFruits);
//        Possibly use the line below as an alternative? but that's not the root of the problem.
//        return allFruits.subList(0, Math.min(allFruits.size(), 10)); // Return top 10 popular fruits.
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

        return new ArrayList<Fruit>(fruitsMap.values());
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

        if (filterCategories.isEmpty()) {
            for (Category collection : getFruitCategories()) {
                db.collection(collection.getCategoryName()).get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {
                    if (task.isSuccessful()) {
                        for (Fruit fruit : task.getResult().toObjects(fruitCategoryClasses.get(collection.getCategoryName()).getClass())) {
                            if ((fruit.getName().equals(searchTerm)) || (fruit.getVariety().equals(searchTerm)) || (fruit.getProducer().equals(searchTerm))) {
                                fruitsMap.put(fruit.getCategory(), fruit);
                            }
                        }
                    }
                });
            }
            return new ArrayList<Fruit>(fruitsMap.values());
        }

        for (String filter : filterCategories) {
            db.collection(filter).get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {
                if (task.isSuccessful()) {
                    for (Fruit fruit : task.getResult().toObjects(fruitCategoryClasses.get(filter).getClass())) {
                        if ((fruit.getName().equals(searchTerm)) || (fruit.getVariety().equals(searchTerm)) || (fruit.getProducer().equals(searchTerm))) {
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
        categoryList.add(new Category("apples"));
        categoryList.add(new Category("blueberries"));
        categoryList.add(new Category("feijoas"));
        categoryList.add(new Category("kiwifruits"));
        categoryList.add(new Category("oranges"));
        return categoryList;
    }

    public static void updatePopularityToFirestore(IProduct fruit) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(fruit.getCategory())
                .document(fruit.getCategory() + fruit.getId())
                .set(fruit)
                .addOnSuccessListener(unused
                        -> Log.d(fruit.getCategory() + " Collection Update",
                        "Popularity updated to " + fruit.getPopularity() + "."))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(fruit.getCategory() + " Collection Update",
                                "Popularity could not be updated to " + fruit.getPopularity()
                                        + ".");
                    }
                });
    }
}