package com.example.fruitmarket.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fruitmarket.R;
import com.example.fruitmarket.models.Fruit;

import java.util.List;

public class TopPicksAdapter extends RecyclerView.Adapter<TopPicksAdapter.ViewHolder> {

    public static final String TOP_PICKS_KEY = "topPicks";

    private List<Fruit> topPicks;
    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView topPickTextView;
        private ImageView topPickImageView;

        public ViewHolder(View view ){
            super(view);
            view.setOnClickListener(this); //setting a click listener for the top pick part
            //Setting the views
            topPickTextView = (TextView) view.findViewById(R.id.top_pick_text);
            topPickImageView = (ImageView) view.findViewById(R.id.top_pick_image);

        }

        @Override
        public void onClick(View v) {
            //When top item is clicked
            Activity activity = (Activity) context;
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(TOP_PICKS_KEY, topPicks.get(getAdapterPosition())); //passing the current PcPart into the intent
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_from_bottom, R.anim.slide_to_top); //setting the slide animation

        }
    }
    //passing in the data to be adapted/set to the view
    public TopPicksAdapter(List<Fruit> data){ topPicks = data; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext(); //setting the context
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.top_pick_item, viewGroup, false); //inflating the view with data
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TopPicksAdapter.ViewHolder viewHolder, int position) {
        viewHolder.topPickTextView.setText(topPicks.get(position).getCategory()); //setting the category name for the part
        int imageID = context.getResources().getIdentifier(topPicks.get(position).getImageIDs().get(0), "drawable", context.getPackageName()); //extracting the id of first image
        viewHolder.topPickImageView.setImageResource(imageID); //setting the image for the part
    }

    @Override
    public int getItemCount() { return topPicks.size(); }


}

