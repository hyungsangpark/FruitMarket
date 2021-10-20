package com.example.fruitmarket.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruitmarket.models.Fruit;
import com.example.fruitmarket.R;
import com.example.fruitmarket.activities.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    public static final String FRUIT_ITEM_KEY = "FRUIT_ITEM_KEY";

    private List<Fruit> fruitsList;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView itemImageView;
        private TextView itemNameTextView;
        private TextView itemProducerTextView;
        private TextView itemPriceTextView;
        private TextView itemPriceMetricTextView;

        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this); //Setting an onclick listener for every part
            //Setting the views from the xml
            itemImageView = (ImageView) v.findViewById(R.id.list_item_image);
            itemNameTextView = (TextView) v.findViewById(R.id.list_item_name);
            itemProducerTextView = (TextView) v.findViewById(R.id.list_item_producer);
            itemPriceTextView = (TextView) v.findViewById(R.id.list_item_price);
            itemPriceMetricTextView = (TextView) v.findViewById(R.id.list_item_priceMetric);
        }

        @Override
        public void onClick(View v) {
            //Setting the context
            Activity activity = (Activity) context;

            //Creating new intent to go to the details activity
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(FRUIT_ITEM_KEY, String.valueOf(fruitsList.get(getAdapterPosition())));

            //Start activity and sets the slide transitions
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    public ListAdapter(List<Fruit> fruitsList) {this.fruitsList = fruitsList;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItemView = inflater.inflate(R.layout.list_item_horizontal, parent, false);
        ViewHolder holder = new ViewHolder(listItemView);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        //Sets the name, price and image of the part into the recycler view
        Fruit thisFruit = fruitsList.get(position);
        holder.itemNameTextView.setText(thisFruit.getName());
        holder.itemProducerTextView.setText(thisFruit.getProducer());
        holder.itemPriceTextView.setText("Price: $"+ String.valueOf(thisFruit.getPrice()));
        holder.itemPriceMetricTextView.setText(thisFruit.getPriceMetric().toString());
        int imageID = context.getResources().getIdentifier(
                thisFruit.getImages().get(0), "drawable", context.getPackageName());
        holder.itemImageView.setImageResource(imageID);

    }

    @Override
    public int getItemCount() {
        return fruitsList.size();
    }
}

