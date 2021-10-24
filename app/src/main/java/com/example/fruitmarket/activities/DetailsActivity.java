package com.example.fruitmarket.activities;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.fruitmarket.R;
import com.example.fruitmarket.adapters.FruitDetailsAdapter;
import com.example.fruitmarket.adapters.ViewPagerAdapter;
import com.example.fruitmarket.data.DataProvider;
import com.example.fruitmarket.models.IProduct;
import com.google.android.material.tabs.TabLayout;

public class DetailsActivity extends AppCompatActivity {
    class ViewHolder {
        TextView fruitNameTextView, producerTextView, header;
        LinearLayout nameContainer;
        TabLayout tabs;
    }

    DetailsActivity.ViewHolder vh;

    // creating object of ViewPager
    ViewPager mViewPager;

    // images array
    int[] images = {R.drawable.gala_apple_rosie_1, R.drawable.gala_apple_rosie_2,
            R.drawable.gala_apple_rosie_3};

    // Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapter;

    IProduct fruit;
    FruitDetailsAdapter adapter;
    RecyclerView fruitDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Enable Tool bar to work.
        setSupportActionBar(findViewById(R.id.details_tool_bar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fruit = (IProduct) getIntent().getSerializableExtra("IProduct");
        fruit.incrementPopularity();
        DataProvider.getInstance().updatePopularityToFirestore(fruit);

        for (int i = 0; i < 3; i++) {
            String imageName = fruit.getImages().get(i).split("\\.")[0];
            images[i] = DetailsActivity.this.getResources().getIdentifier(
                    imageName, "drawable",
                    DetailsActivity.this.getPackageName());
        }

        vh = new DetailsActivity.ViewHolder();
        vh.header = findViewById(R.id.details_header);
        vh.fruitNameTextView = (TextView) findViewById(R.id.fruit_name_text_view);
        vh.producerTextView = (TextView) findViewById(R.id.producer_text_view);
        vh.nameContainer = (LinearLayout) findViewById(R.id.name_container);
        vh.tabs = (TabLayout) findViewById(R.id.tabs);

        vh.header.setText(fruit.getName());
        vh.fruitNameTextView.setText(fruit.getName());
        vh.producerTextView.setText(fruit.getProducer());
        setColour(fruit, vh.nameContainer, vh.tabs);

        // Initializing the ViewPager Object
        mViewPager = (ViewPager) findViewById(R.id.imagesSlider);

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

        // Create a LayoutManager
        LinearLayoutManager lm = new LinearLayoutManager(this);

        // Set layout manager to position the items
        fruitDetails.setLayoutManager(lm);
    }

    private void setColour(IProduct fruit, LinearLayout element, TabLayout layout) {
        switch (fruit.getCategory()) {
            case "Apple":
                element.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.apples_dark, null));
                layout.setSelectedTabIndicatorColor(ResourcesCompat.getColor(getResources(),
                        R.color.apples_dark, null));
                break;
            case "Blueberry":
                element.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.blueberries_dark, null));
                layout.setSelectedTabIndicatorColor(ResourcesCompat.getColor(getResources(),
                        R.color.blueberries_dark, null));
                break;
            case "Feijoa":
                element.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.feijoas_dark, null));
                layout.setSelectedTabIndicatorColor(ResourcesCompat.getColor(getResources(),
                        R.color.feijoas_dark, null));
                break;
            case "Kiwifruit":
                element.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.kiwifruits_dark, null));
                layout.setSelectedTabIndicatorColor(ResourcesCompat.getColor(getResources(),
                        R.color.kiwifruits_dark, null));
                break;
            case "Orange":
                element.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.oranges_dark, null));
                layout.setSelectedTabIndicatorColor(ResourcesCompat.getColor(getResources(),
                        R.color.oranges_dark, null));
                break;
            default:
                element.setBackgroundColor(ResourcesCompat.getColor(getResources(),
                        R.color.purple_500, null));
                layout.setSelectedTabIndicatorColor(ResourcesCompat.getColor(getResources(),
                        R.color.purple_500, null));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
