package com.example.fruitmarket.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.recyclerviewdemo.R;
//import com.example.recyclerviewdemo.models.Contact;

import com.example.fruitmarket.R;
import com.example.fruitmarket.models.Fruit;
import com.example.fruitmarket.models.IProduct;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FruitDetailsAdapter extends RecyclerView.Adapter<FruitDetailsAdapter.ViewHolder> {

    // To make your view item clickable ensure that the view holder class implements View.OnClickListener and it has the onClick(View v) method.
    public class ViewHolder extends RecyclerView.ViewHolder {

        // Declare objects of all the views to be manipulated in item_contact.xml
        public TextView leftTextView;
        public TextView rightTextView;

        public ViewHolder(View v) {
            super(v);
            // Initialize the view objects
            leftTextView = v.findViewById(R.id.text_left);
            rightTextView = v.findViewById((R.id.text_right));
        }

//        @Override
//        public void onClick(View v) {
//            // What to do when the view item is clicked
//            Contact clickedContact = mContacts.get(getAdapterPosition());
//            Toast.makeText(mContext, clickedContact.getName() + " is clicked in position " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
//        }
    }

    // Declare the data collection object that holds the data to be populated in the RecyclerView
    private List<String> mAttributeNames;
    private List<String> mAttributeValues;
    private Context mContext;

    // Pass in the contact array object into the constructor
    public FruitDetailsAdapter(List<String> attributeNamesames,
                               List<String> attributeValues) {
        // The contacts object is sent via the activity that creates this adaptor
        mAttributeNames = attributeNamesames;
        mAttributeValues = attributeValues;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public FruitDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.fruit_details, parent, false);

        // Return a new holder instance
        ViewHolder holder = new ViewHolder(contactView);
        return holder;
    }

    // This method populates the data from mContacts to the view items
    @Override
    public void onBindViewHolder(@NonNull FruitDetailsAdapter.ViewHolder holder, int position) {
        // Get the data object for the item view in this position
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
