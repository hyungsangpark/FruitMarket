package com.example.fruitmarket.adapters;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruitmarket.R;
import com.example.fruitmarket.activities.ListActivity;
import com.example.fruitmarket.models.Category;

import java.util.List;

/**
 * Adapter for dealing with the GUI and data for categories.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>  {

    public static final String CATEGORY_LIST_KEY = "category";

    private List<Category> categories;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView categoryTextView;
        private ImageView categoryImageView;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);

            //Setting views
            categoryTextView = (TextView) v.findViewById(R.id.category_text);
            categoryImageView = (ImageView) v.findViewById(R.id.category_image);
        }

        @Override
        public void onClick(View v) {
            //Set context and create intent to go to ListActivity
            Activity activty = (Activity) context;
            Intent intent = new Intent(context, ListActivity.class);

            //Passes in the current category object into the intent
            intent.putExtra(CATEGORY_LIST_KEY, categories.get(getAdapterPosition()).getCategoryName());

            //Starts the new activity with the intent and sets sliding transitions
            activty.startActivity(intent);
            activty.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    public CategoryAdapter(List<Category> categories){
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Links and inflates the views in category_item.xml with the data
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View categoryView = inflater.inflate(R.layout.category, parent, false);
        ViewHolder holder = new ViewHolder(categoryView);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        //Setting the text and image for the current category
        Category thisCategory = categories.get(position);
        holder.categoryTextView.setText(thisCategory.getCategoryDisplayName());
        Log.d(TAG, "onBindViewHolder: thisCategory - " + thisCategory);
        int imageID = thisCategory.getCategoryImageName();
        holder.categoryImageView.setImageResource(imageID);
    }

    @Override
    public int getItemCount() { return categories.size(); }
}
