package com.example.fruitmarket.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.fruitmarket.adapters.FruitDetailsAdapter;
import com.example.fruitmarket.adapters.ViewPagerAdapter;
import com.example.fruitmarket.data.DataProvider;
import com.example.fruitmarket.models.IProduct;
import com.example.fruitmarket.R;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    class ViewHolder {
        TextView fruitNameTextView, producerTextView, descriptionTextView, priceTextView,
                availabilityTextView;
    }

    DetailsActivity.ViewHolder vh;

    // creating object of ViewPager
    ViewPager mViewPager;

    // images array
    int[] images = {R.drawable.gala_apple_rosie_1, R.drawable.gala_apple_rosie_2, R.drawable.gala_apple_rosie_3};

    // Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapter;

    IProduct fruit;
    FruitDetailsAdapter adapter;
    RecyclerView fruitDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        fruit = (IProduct)getIntent().getSerializableExtra("IProduct");
        fruit.incrementPopularity();
        DataProvider.updatePopularityToFirestore(fruit);

        for (int i = 0; i < 3; i++) {
            images[i] = DetailsActivity.this.getResources().getIdentifier(
                    fruit.getImages().get(i), "drawable",
                    DetailsActivity.this.getPackageName());
        }

        vh = new DetailsActivity.ViewHolder();
        vh.fruitNameTextView = (TextView) findViewById(R.id.fruit_name_text_view);
        vh.producerTextView = (TextView) findViewById(R.id.producer_text_view);
        vh.descriptionTextView = (TextView) findViewById(R.id.description_text_view);

        vh.fruitNameTextView.setText(fruit.getName());
        vh.producerTextView.setText(fruit.getProducer());

        // Initializing the ViewPager Object
        mViewPager = (ViewPager)findViewById(R.id.imagesSlider);

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapter(DetailsActivity.this, images);

        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);

        // Lookup the recyclerview in activity layout
        fruitDetails = (RecyclerView) findViewById(R.id.fruitDetails);

        // Create adapter passing in the sample user data
        adapter = new FruitDetailsAdapter(fruit.getAttributeNames(), fruit.getAttributeValues());
        // Attach the adapter to the recyclerview to populate items
        fruitDetails.setAdapter(adapter);

//        // Create a LayoutManager
//        LinearLayoutManager lm = new LinearLayoutManager(this);
//
//
//        // Fore a Horizontal RecyclerView use
//        // LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//
//        // Set layout manager to position the items
//        rvContacts.setLayoutManager(lm);
    }
}
