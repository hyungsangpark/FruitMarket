package com.example.fruitmarket.adapters;

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

import com.example.fruitmarket.R;
import com.example.fruitmarket.activities.DetailsActivity;
import com.example.fruitmarket.models.Fruit;

import java.util.ArrayList;
import java.util.List;

public class TopPicksAdapter extends RecyclerView.Adapter<TopPicksAdapter.ViewHolder> {

    public static final String TOP_PICKS_KEY = "TOP_PICKS_KEY";

    private List<Fruit> topPicks;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView topPickNameTextView;
        private TextView topPickProducerTextView;
        private ImageView topPickImageView;

        public ViewHolder(View view){
            super(view);
            view.setOnClickListener(this); //setting a click listener for the top pick part
            //Setting the views
            topPickNameTextView = (TextView) view.findViewById(R.id.top_pick_item_name);
            topPickProducerTextView = (TextView) view.findViewById(R.id.top_pick_item_producer);
            topPickImageView = (ImageView) view.findViewById(R.id.top_pick_image);
        }

        @Override
        public void onClick(View v) {
            //When top item is clicked
            Activity activity = (Activity) context;
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(TOP_PICKS_KEY, String.valueOf(topPicks.get(getAdapterPosition())));
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top);
        }
    }
    //passing in the data to be adapted/set to the view
    public TopPicksAdapter(List<Fruit> data){
        topPicks = data;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext(); //setting the context
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.top_pick_item,
                                        viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopPicksAdapter.ViewHolder vHolder, int position) {
        Fruit thisFruit = topPicks.get(position);
        vHolder.topPickNameTextView.setText(thisFruit.getName());
        vHolder.topPickProducerTextView.setText(thisFruit.getProducer());
        if (thisFruit.getImages() != null){
            int imageID =context.getResources().getIdentifier(
                    thisFruit.getImages().get(0), "drawable", context.getPackageName());
            vHolder.topPickImageView.setImageResource(imageID);
        }
    }

    @Override
    public int getItemCount() {
        int numTopPicks;
        if (topPicks == null) {
            numTopPicks = 0;
        } else {
            numTopPicks = topPicks.size();
        }
        return numTopPicks;
    }

    public void addTopPicksData(List<Fruit> mostPopular){
        topPicks = mostPopular;
        notifyDataSetChanged();
    }
}