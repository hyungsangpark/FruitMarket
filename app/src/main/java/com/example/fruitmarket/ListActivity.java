package com.example.fruitmarket;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.fruitmarket.fruit.Fruit;
import com.example.fruitmarket.fruit.DataProvider;
import com.example.fruitmarket.fruit.Fruit;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    RecyclerView listItemsRecyclerView;
    ArrayList<Fruit> FruitList;
    LinearLayoutManager listItemsLayoutManager;
    ListAdapter listAdapter;

    DataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        dataProvider = DataProvider.getInstance(this);

        //Gets the intent
        Intent thisIntent = getIntent();
        //Passes in the category selected from MainActivity
        String currentCategory = thisIntent.getStringExtra(CategoryAdapter.CATEGORY_LIST_KEY);

        //Setting actionbar logo and name to selected category
        setTitle("  " + currentCategory);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.logo_small);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        //Retrieving list of fruit of the selected category from dataProvider
        FruitList = dataProvider.getCategoryOfData(currentCategory);

        //Setting the recycler view
        listItemsRecyclerView = (RecyclerView) findViewById(R.id.fruits_list);

        //Creating and setting the adapter to the recycler view
        listAdapter = new ListAdapter(FruitList);
        listItemsRecyclerView.setAdapter(listAdapter);

        //Creating and setting a layout manager for the recycler view
        listItemsLayoutManager = new LinearLayoutManager(this);
        listItemsRecyclerView.setLayoutManager(listItemsLayoutManager);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Set the slide transition when the back button is pressed
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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