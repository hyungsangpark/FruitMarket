package com.example.fruitmarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruitmarket.R;
import com.example.fruitmarket.adapters.CategoryAdapter;
import com.example.fruitmarket.adapters.ListAdapter;
import com.example.fruitmarket.data.DataProvider;
import com.example.fruitmarket.models.Fruit;

import java.util.ArrayList;
import java.util.List;

/**
 * Shows the list of fruits in a chosen category.
 */
public class ListActivity extends AppCompatActivity {

    RecyclerView listItemsRecyclerView;
    List<Fruit> fruitsList = new ArrayList<>();
    LinearLayoutManager listItemsLayoutManager;
    ListAdapter listAdapter;

    DataProvider dataProvider = DataProvider.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_horizontal);
        setSupportActionBar(findViewById(R.id.list_menu_app_bar));
        TextView main_menu_title = (TextView)findViewById(R.id.main_menu_title);

        //Gets the intent
        Intent thisIntent = getIntent();

        //Passes in the category selected from MainActivity
        String categoryDataPassedIn = thisIntent.getStringExtra(CategoryAdapter.CATEGORY_LIST_KEY);
        String searchTermDataPassedIn = thisIntent.getStringExtra(SearchActivity.SEARCH_TERM_KEY);
        ArrayList<String> filterCategories = thisIntent.getStringArrayListExtra(SearchActivity.FILTER_CATEGORIES_KEY);

        if (searchTermDataPassedIn == null){
            //Retrieving list of fruit of the selected category from dataProvider
            fruitsList = dataProvider.getFruitsOfCategory(categoryDataPassedIn);
            main_menu_title.setText(categoryDataPassedIn);
        } else {
            fruitsList = dataProvider.getFruitsFromSearchTerm(searchTermDataPassedIn, filterCategories);
            main_menu_title.setText(searchTermDataPassedIn);
        }

        //Setting the recycler view to default horizontal cards
        listItemsRecyclerView = (RecyclerView) findViewById(R.id.fruits_list);

        //Creating and setting the adapter to the recycler view
        listAdapter = new ListAdapter(fruitsList);
        listItemsRecyclerView.setAdapter(listAdapter);

        //Creating and setting a layout manager for the recycler view
        listItemsLayoutManager = new LinearLayoutManager(this);
        listItemsRecyclerView.setLayoutManager(listItemsLayoutManager);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}