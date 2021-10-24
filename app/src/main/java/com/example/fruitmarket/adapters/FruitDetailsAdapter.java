package com.example.fruitmarket.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruitmarket.R;

import java.util.List;

/**
 * Adapter for dealing with the GUI and data for fruit description in DetailsActivity.
 */
public class FruitDetailsAdapter extends RecyclerView.Adapter<FruitDetailsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView leftTextView; // e.g. Price:
        public TextView rightTextView; // e.g. $7.00 PER_KG

        public ViewHolder(View v) {
            super(v);
            leftTextView = v.findViewById(R.id.text_left);
            rightTextView = v.findViewById((R.id.text_right));
        }
    }

    // Declare the data collection object that holds the data to be populated in the RecyclerView.
    private List<String> mAttributeNames; // e.g. Price:
    private List<String> mAttributeValues; // e.g. $7.00 PER_KG
    private Context mContext;

    public FruitDetailsAdapter(List<String> attributeNames,
                               List<String> attributeValues) {
        mAttributeNames = attributeNames;
        mAttributeValues = attributeValues;
    }

    // Usually involves inflating a layout from XML and returning the holder.
    @NonNull
    @Override
    public FruitDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout.
        View fruitDescriptionView = inflater.inflate(R.layout.fruit_details,
                                                     parent, false);

        // Return a new holder instance.
        ViewHolder holder = new ViewHolder(fruitDescriptionView);
        return holder;
    }

    // This method populates the data to the view items.
    @Override
    public void onBindViewHolder(@NonNull FruitDetailsAdapter.ViewHolder holder, int position) {
        // Get the data object for the view in this position
        String thisAttributeName = mAttributeNames.get(position);
        String thisAttributeValue = mAttributeValues.get(position);

        holder.leftTextView.setText(thisAttributeName);
        holder.rightTextView.setText(thisAttributeValue);
    }

    @Override
    public int getItemCount() {
        return mAttributeNames.size();
    }
}
