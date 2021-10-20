package com.example.fruitmarket.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fruitmarket.R;
import com.example.fruitmarket.adapters.CategoryAdapter;
import com.example.fruitmarket.adapters.TopPicksAdapter;
import com.example.fruitmarket.data.DataProvider;
import com.example.fruitmarket.data.SearchDataProvider;
import com.example.fruitmarket.models.Category;
import com.example.fruitmarket.models.Fruit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView topPicksRecyclerView;
    ArrayList<Fruit> topPicks;
    LinearLayoutManager topPicksLayoutManager;
    TopPicksAdapter topPicksAdapter;

    RecyclerView categoriesRecyclerView;
    List<Category> categories;
    LinearLayoutManager categoriesLayoutManager;
    CategoryAdapter categoryAdapter;

    DataProvider dataProvider = new DataProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setTitle("  " + "FruitMarket");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.fruitmarket_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        topPicksRecyclerView = (RecyclerView) findViewById(R.id.top_picks);
        categoriesRecyclerView = (RecyclerView) findViewById(R.id.categories);

        topPicks = dataProvider.getMostPopular();
        categories = dataProvider.getFruitCategories();

        topPicksAdapter = new TopPicksAdapter(topPicks);
        categoryAdapter = new CategoryAdapter(categories);

        topPicksRecyclerView.setAdapter(topPicksAdapter);
        categoriesRecyclerView.setAdapter(categoryAdapter);

        topPicksLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        categoriesLayoutManager = new LinearLayoutManager(this);

        topPicksRecyclerView.setLayoutManager(topPicksLayoutManager);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //Check whether the user has submitted their search
            @Override
            public boolean onQueryTextSubmit(String query) {

                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();

                //Create intent for SearchActivity containing the search query
               /* Intent searchIntent = new Intent(getBaseContext(), SearchActivity.class);
                searchIntent.putExtra("searchQuery", query);
                startActivity(searchIntent);*/

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    //For when the user returns to the MainActivity
    @Override
    public void onResume(){
        super.onResume();
        //Refresh top picks
        this.topPicks = dataProvider.getMostPopular();
        topPicksAdapter = new TopPicksAdapter(topPicks);
        topPicksRecyclerView.setAdapter(topPicksAdapter);
    }
}