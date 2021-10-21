package com.example.fruitmarket.activities;

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

public class MainActivity extends AppCompatActivity {

    RecyclerView topPicksRecyclerView;

    RecyclerView categoriesRecyclerView;
    List<Category> categories;
    LinearLayoutManager categoriesLayoutManager;
    CategoryAdapter categoryAdapter;

    ProgressBar progressBar;
    ScrollView mainScrollView;

    ImageButton searchButton;

    DataProvider dataProvider = new DataProvider();

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

    private Map<String, List<Fruit>> fruitsMap;

    public void fetchFruits() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // TODO: Refactor it out later.
        Map<String, Fruit> fruitCategoryClasses = new HashMap<>();
        fruitCategoryClasses.put("apples", new Apple());
        fruitCategoryClasses.put("blueberries", new Blueberry());
        fruitCategoryClasses.put("feijoas", new Feijoa());
        fruitCategoryClasses.put("kiwifruits", new Kiwifruit());
        fruitCategoryClasses.put("oranges", new Orange());

        fruitsMap = new HashMap<>();

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
                                List<Fruit> topPickFruits = getTopTenFruits();
                                propagateTopPicksAdaptor(topPickFruits);
                                propagateCategoriesAdaptor();

                                dataProvider.provideData(fruitsMap);

                                // hide the progressbar.
                                progressBar.setVisibility(View.GONE);
                                // Show main content.
                                mainScrollView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }
    }

    private List<Fruit> getTopTenFruits() {
        List<Fruit> allFruits = new ArrayList<>();
        for (List<Fruit> category : fruitsMap.values()) {
            allFruits.addAll(category);
        }
        allFruits.sort(Comparator.comparing(Fruit::getPopularity).reversed());
        return allFruits.subList(0, 10);
    }

    private void propagateTopPicksAdaptor(List<Fruit> topPickFruits) {
        TopPicksAdapter topPicksAdapter = new TopPicksAdapter(topPickFruits);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        final MenuItem searchItem = menu.findItem(R.id.action_search);
//        final SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            //Check whether the user has submitted their search
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                // Reset SearchView
//                searchView.clearFocus();
//                searchView.setQuery("", false);
//                searchView.setIconified(true);
//                searchItem.collapseActionView();
//
//                //Create intent for SearchActivity containing the search query
//               /* Intent searchIntent = new Intent(getBaseContext(), SearchActivity.class);
//                searchIntent.putExtra("searchQuery", query);
//                startActivity(searchIntent);*/
//
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
//        return true;
//    }

    //For when the user returns to the MainActivity
//    @Override
//    public void onResume(){
//        super.onResume();
//        //Refresh top picks
//        topPicksAdapter = new TopPicksAdapter(topPicks);
//        dataProvider.getMostPopular(topPicksAdapter);
//        topPicksRecyclerView.setAdapter(topPicksAdapter);
//    }
}