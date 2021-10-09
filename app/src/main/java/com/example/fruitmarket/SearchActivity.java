package com.example.fruitmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fruitmarket.adaptors.CategoryFilterAdaptor;
import com.example.fruitmarket.data.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    List<String> categories;
    CategoryFilterAdaptor adapter;
    RecyclerView rvButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        rvButtons = (RecyclerView) findViewById(R.id.category_filter_recycler_view);

        categories = DataProvider.generateFruitsList();
        adapter = new CategoryFilterAdaptor(categories);
        rvButtons.setAdapter(adapter);

        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvButtons.setLayoutManager(lm);
    }
}