package com.example.fruitmarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    class ViewHolder {
        TextView fruitNameTextView, producerTextView, descriptionTextView, priceTextView,
                availabilityTextView;
    }

    MainActivity.ViewHolder vh;

    // creating object of ViewPager
    ViewPager mViewPager;

    // images array
    int[] images = {R.drawable.a1, R.drawable.a2, R.drawable.a3};

    // Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        vh = new MainActivity.ViewHolder();
        vh.fruitNameTextView = (TextView) findViewById(R.id.fruit_name_text_view);
        vh.producerTextView = (TextView) findViewById(R.id.producer_text_view);
        vh.descriptionTextView = (TextView) findViewById(R.id.description_text_view);
        vh.priceTextView = (TextView) findViewById(R.id.price_text_view);
        vh.availabilityTextView = (TextView) findViewById(R.id.availability_text_view);

        vh.fruitNameTextView.setText("Implement the rest when DataProvider is finished");

        // Initializing the ViewPager Object
        mViewPager = (ViewPager)findViewById(R.id.viewPagerMain);

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapter(MainActivity.this, images);

        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);
    }
}