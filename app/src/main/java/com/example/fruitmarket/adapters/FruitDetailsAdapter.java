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

import com.example.fruitmarket.models.Fruit;

import java.lang.reflect.Field;
import java.util.List;

public class FruitDetailsAdapter extends RecyclerView.Adapter<FruitDetailsAdapter.ViewHolder> {

//    // To make your view item clickable ensure that the view holder class implements View.OnClickListener and it has the onClick(View v) method.
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        // Declare objects of all the views to be manipulated in item_contact.xml
//        public TextView nameTextView;
//        public RadioButton radioButtonOnlineStatus;
//
//        public ViewHolder(View v) {
//            super(v);
//            v.setOnClickListener(this);
//            // Initialize the view objects
//            nameTextView = v.findViewById(R.id.contact_name);
//            radioButtonOnlineStatus = v.findViewById((R.id.radio_button_online_status));
//        }
//
//        @Override
//        public void onClick(View v) {
//            // What to do when the view item is clicked
//            Contact clickedContact = mContacts.get(getAdapterPosition());
//            Toast.makeText(mContext, clickedContact.getName() + " is clicked in position " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
//        }
//    }

    // Declare the data collection object that holds the data to be populated in the RecyclerView
    private Fruit mFruit;
    private Context mContext;

    // Pass in the contact array object into the constructor
    public FruitDetailsAdapter(IProduct fruit) {
        // The contacts object is sent via the activity that creates this adaptor
        mFruit = (Fruit)fruit;
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
        for (Field field : mFruit.getClass().getDeclaredFields()) {
            field.setAccessible(true); // You might want to set modifier to public first.
            Object value = field.get(someObject);
            if (value != null) {
                System.out.println(field.getName() + "=" + value);
            }
        }

        holder.nameTextView.setText(thisContact.getName());
        holder.radioButtonOnlineStatus.setChecked(thisContact.isOnline());
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }


}
