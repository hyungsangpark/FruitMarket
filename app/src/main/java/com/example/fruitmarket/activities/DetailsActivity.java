package com.example.fruitmarket.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.fruitmarket.R;
import com.example.fruitmarket.adapters.FruitDetailsAdapter;
import com.example.fruitmarket.adapters.ViewPagerAdapter;
import com.example.fruitmarket.data.DataProvider;
import com.example.fruitmarket.models.IProduct;
import com.google.android.material.tabs.TabLayout;

/**
 * Shows the photos and details of the selected fruit.
 */
public class DetailsActivity extends AppCompatActivity {
    class ViewHolder {
        LinearLayout titleContainerLinearLayout; // Container that contains the name and producer
                                                 // of the fruit.
        TextView header;
        TextView fruitNameTextView, fruitProducerTextView;
        TabLayout tabLayout;
        RecyclerView fruitDescriptionRecyclerView;
        TextView fruitAboutTextView;
    }
    DetailsActivity.ViewHolder vh;

    ViewPager imagesViewPager; // ViewPager for fruitDescription.
    ViewPagerAdapter imagesViewPagerAdapter;
    FruitDetailsAdapter fruitDescriptionAdapter;

    // Images array for fruit images. Initialise it with default images.
    int[] images = {R.drawable.gala_apple_rosie_1, R.drawable.gala_apple_rosie_2,
                    R.drawable.gala_apple_rosie_3};

    IProduct fruit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Enable Tool bar to work.
        setSupportActionBar(findViewById(R.id.details_tool_bar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get the selected fruit.
        fruit = (IProduct) getIntent().getSerializableExtra("IProduct");

        // Increase popularity per click.
        DataProvider.getInstance().updatePopularity(fruit);

        // Get the images for the selected fruit.
        for (int i = 0; i < 3; i++) {
            String imageName = fruit.getImages().get(i).split("\\.")[0];
            images[i] = DetailsActivity.this.getResources().getIdentifier(
                    imageName, "drawable",
                    DetailsActivity.this.getPackageName());
        }

        // Initialise the vh components.
        vh = new DetailsActivity.ViewHolder();
        vh.header = findViewById(R.id.details_header);
        vh.titleContainerLinearLayout = (LinearLayout) findViewById(R.id.name_container);
        vh.fruitNameTextView = (TextView) findViewById(R.id.fruit_name_text_view);
        vh.fruitProducerTextView = (TextView) findViewById(R.id.producer_text_view);
        vh.tabLayout = (TabLayout) findViewById(R.id.tabs);
        vh.fruitDescriptionRecyclerView = (RecyclerView) findViewById(R.id.fruitDetails);
        vh.fruitAboutTextView = (TextView) findViewById(R.id.fruit_about);

        // Setting up the image slider.
        imagesViewPager = (ViewPager) findViewById(R.id.imagesSlider);
        imagesViewPagerAdapter = new ViewPagerAdapter(DetailsActivity.this, images);
        imagesViewPager.setAdapter(imagesViewPagerAdapter);

        fruitDescriptionAdapter = new FruitDetailsAdapter(fruit.getAttributeNames(),
                                                          fruit.getAttributeValues());
        vh.fruitDescriptionRecyclerView.setAdapter(fruitDescriptionAdapter); // Attach the adapter
                                                                             // to the recyclerview
                                                                             // to populate items.
        LinearLayoutManager lm = new LinearLayoutManager(this);
        vh.fruitDescriptionRecyclerView.setLayoutManager(lm);

        // Update the vh components with the details of the selected fruit.
        vh.header.setText(fruit.getName());
        fruitDescriptionAdapter.setColour(fruit, vh.titleContainerLinearLayout,
                                          vh.tabLayout, this.getApplicationContext());
        vh.fruitNameTextView.setText(fruit.getName());
        vh.fruitProducerTextView.setText(fruit.getProducer());
        vh.fruitAboutTextView.setText(fruit.getDescription());

        // Listener for which tab (description or about) is selected.
        vh.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        vh.fruitAboutTextView.setVisibility(View.GONE);
                        vh.fruitDescriptionRecyclerView.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        vh.fruitAboutTextView.setVisibility(View.VISIBLE);
                        vh.fruitDescriptionRecyclerView.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
