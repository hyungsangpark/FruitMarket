package com.example.fruitmarket.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruitmarket.fruit.Fruit;

import java.util.ArrayList;

public class TopPicksAdapter extends RecyclerView.Adapter {
    public TopPicksAdapter(ArrayList<Fruit> topPicks) {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}