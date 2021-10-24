package com.example.fruitmarket.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.example.fruitmarket.R;
import com.example.fruitmarket.adapters.CategoryAdapter;
import com.example.fruitmarket.adapters.TopPicksAdapter;
import com.example.fruitmarket.data.DataProvider;
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

/**
 * Shows the list of top picks and categories.
 */
public class MainActivity extends AppCompatActivity {

    RecyclerView topPicksRecyclerView;
    RecyclerView categoriesRecyclerView;
    List<Category> categories;
    LinearLayoutManager categoriesLayoutManager;
    CategoryAdapter categoryAdapter;

    ProgressBar progressBar;
    ScrollView mainScrollView;

    DataProvider dataProvider = DataProvider.getInstance();
    ImageButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("  " + "FruitMarket");
        setSupportActionBar(findViewById(R.id.main_menu_app_bar));

        progressBar = findViewById(R.id.fruits_load_progressbar);
        mainScrollView = findViewById(R.id.main_scroll_view);
        topPicksRecyclerView = findViewById(R.id.top_picks);
        categoriesRecyclerView = findViewById(R.id.categories);

        searchButton = findViewById(R.id.main_menu_search_icon);
        searchButton.setOnClickListener(view -> {
            Intent searchIntent = new Intent(getBaseContext(), SearchActivity.class);
            startActivity(searchIntent);
        });

        fetchFruits();
    }

    // Fetch the necessary data from the database.
    public void fetchFruits() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Fruit> fruitCategoryClasses = new HashMap<>();
        fruitCategoryClasses.put("apples", new Apple());
        fruitCategoryClasses.put("blueberries", new Blueberry());
        fruitCategoryClasses.put("feijoas", new Feijoa());
        fruitCategoryClasses.put("kiwifruits", new Kiwifruit());
        fruitCategoryClasses.put("oranges", new Orange());

        Map<String, List<Fruit>> fruitsMap = new HashMap<>();

        for (String collection : fruitCategoryClasses.keySet()) {
            fruitsMap.put(collection, new ArrayList<>());
            boolean[] everyCategoryFetched = {true};
            db.collection(collection).get().addOnCompleteListener(
                    (@NonNull Task<QuerySnapshot> task) -> {
                        if (task.isSuccessful()) {
                            for (Fruit fruit : task.getResult().toObjects(fruitCategoryClasses.get(collection).getClass())) {
                                Log.d("FruitFetched", collection + ": " + fruit);
                                fruitsMap.get(collection).add(fruit);
                            }

                            everyCategoryFetched[0] = true;
                            for (List<Fruit> category : fruitsMap.values()) {
                                if (category.isEmpty()) {
                                    everyCategoryFetched[0] = false;
                                    break;
                                }
                            }

                            if (everyCategoryFetched[0]) {
                                dataProvider.provideData(fruitsMap);

                                propagateTopPicksAdaptor();
                                propagateCategoriesAdaptor();

                                // hide the progressbar.
                                progressBar.setVisibility(View.GONE);
                                // Show main content.
                                mainScrollView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }
    }

    private void propagateTopPicksAdaptor() {
        List<Fruit> topPickFruits = dataProvider.getTopTenPicksOfFruits();
        Log.d(TAG, "propagateTopPicksAdapter: new_top_picks - " + topPickFruits);
        TopPicksAdapter topPicksAdapter = new TopPicksAdapter(topPickFruits);
        Log.d(TAG, "propagateTopPicksAdaptor: topPicksAdapter - " + topPicksAdapter);
        topPicksRecyclerView.setAdapter(topPicksAdapter);
        LinearLayoutManager topPicksLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        topPicksRecyclerView.setLayoutManager(topPicksLayoutManager);
    }

    private void propagateCategoriesAdaptor() {
        categories = dataProvider.getFruitCategories();
        categoryAdapter = new CategoryAdapter(categories);
        categoriesRecyclerView.setAdapter(categoryAdapter);
        categoriesLayoutManager = new LinearLayoutManager(this);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);
    }

    // For when the user returns to the MainActivity
    @Override
    public void onRestart(){
        super.onRestart();
        //Refresh top picks
        Log.d(TAG, "onRestart: Hi, returned to main");

        TopPicksAdapter topPicksAdapter = (TopPicksAdapter) topPicksRecyclerView.getAdapter();
        Log.d(TAG, "onRestart: topPicksAdapter - " + topPicksAdapter);

        List<Fruit> newTopPicks = dataProvider.getTopTenPicksOfFruits();
        Log.d(TAG, "onRestart: new_top_picks - " + newTopPicks);
        TopPicksAdapter updatedTopPicksAdapter = new TopPicksAdapter(newTopPicks);
        topPicksRecyclerView.setAdapter(updatedTopPicksAdapter);
    }
}