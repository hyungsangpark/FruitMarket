package com.example.fruitmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;

import com.example.fruitmarket.adapters.CategoryAdapter;
import com.example.fruitmarket.adapters.TopPicksAdapter;
import com.example.fruitmarket.data.DataProvider;
import com.example.fruitmarket.fruit.Category;
import com.example.fruitmarket.fruit.Fruit;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView topPicksRecyclerView;
    ArrayList<Fruit> topPicks;
    LinearLayoutManager topPicksLayoutManager;
    TopPicksAdapter topPicksAdapter;

    RecyclerView categoriesRecyclerView;
    ArrayList<Category> categories;
    LinearLayoutManager categoriesLayoutManager;
    CategoryAdapter categoryAdapter;

    DataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Sets title in the action bar with spacing in between the logo and text
        setTitle("  " + "FruitMarket");
        //Sets logo icon in the action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_small);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Instantiates the dataProvider
        dataProvider = DataProvider.getInstance(this);

        // Find and assign Recycler views in activity_main layout
        topPicksRecyclerView = (RecyclerView) findViewById(R.id.top_picks);
        categoriesRecyclerView = (RecyclerView) findViewById(R.id.categories);

        //Retrieving data from data provider
        topPicks = dataProvider.getMostPopular();
        categories = dataProvider.getCategoriesList();

        // Create adapter to pass in data
        topPicksAdapter = new TopPicksAdapter(topPicks);
        categoryAdapter = new CategoryAdapter(categories);

        // Attach the adapter to the recyclerview to populate items
        topPicksRecyclerView.setAdapter(topPicksAdapter);
        categoriesRecyclerView.setAdapter(categoryAdapter);

        //Creating layout manager
        topPicksLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        categoriesLayoutManager = new LinearLayoutManager(this);

        //Set layout manager to position the items
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
                Intent searchIntent = new Intent(getBaseContext(), SearchActivity.class);
                searchIntent.putExtra("searchQuery", query);
                startActivity(searchIntent);

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