package com.example.fruitmarket.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fruitmarket.R;
import com.example.fruitmarket.adapters.CategoryAdapter;
import com.example.fruitmarket.adapters.ListAdapter;
import com.example.fruitmarket.models.Category;
import com.example.fruitmarket.models.Fruit;
import com.example.fruitmarket.data.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView listItemsRecyclerView;
    List<Fruit> fruitsList = new ArrayList<>();
    LinearLayoutManager listItemsLayoutManager;
    ListAdapter listAdapter;

    DataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_horizontal);
        dataProvider = new DataProvider();

        //Gets the intent
        Intent thisIntent = getIntent();
        //Passes in the category selected from MainActivity
        String categoryDataPassedIn = thisIntent.getStringExtra(CategoryAdapter.CATEGORY_LIST_KEY);
        String searchTermDataPassedIn = thisIntent.getStringExtra(SearchActivity.SEARCH_TERM_KEY);
        ArrayList<String> filterCategories = thisIntent.getStringArrayListExtra(SearchActivity.FILTER_CATEGORIES_KEY);
        if (searchTermDataPassedIn == null){
            //Setting actionbar logo and name to selected category
            setTitle("  " + categoryDataPassedIn);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.fruitmarket_logo);
            getSupportActionBar().setDisplayUseLogoEnabled(true);

            //Retrieving list of fruit of the selected category from dataProvider
            fruitsList = dataProvider.getFruitsGivenCategory(new Category(categoryDataPassedIn));
        } else {
            fruitsList = dataProvider.getMatchingSearchTerm(searchTermDataPassedIn, filterCategories);
        }

        //Setting the recycler view
        listItemsRecyclerView = (RecyclerView) findViewById(R.id.fruits_list);

        //Creating and setting the adapter to the recycler view
        listAdapter = new ListAdapter(fruitsList);
        listItemsRecyclerView.setAdapter(listAdapter);

        //Creating and setting a layout manager for the recycler view
        listItemsLayoutManager = new LinearLayoutManager(this);
        listItemsRecyclerView.setLayoutManager(listItemsLayoutManager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Set the slide transition when the back button is pressed
        overridePendingTransition(R.anim.slide_to_left, R.anim.slide_from_right);

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
}