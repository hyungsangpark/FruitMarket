package com.example.fruitmarket;

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

public class DetailsActivity extends AppCompatActivity {
    class ViewHolder {
        TextView fruitNameTextView, producerTextView, descriptionTextView, priceTextView,
                 availabilityTextView;
    }

    ViewHolder vh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        vh = new ViewHolder();
        vh.fruitNameTextView = (TextView) findViewById(R.id.fruit_name_text_view);
        vh.producerTextView = (TextView) findViewById(R.id.producer_text_view);
        vh.descriptionTextView = (TextView) findViewById(R.id.description_text_view);
        vh.priceTextView = (TextView) findViewById(R.id.price_text_view);
        vh.availabilityTextView = (TextView) findViewById(R.id.availability_text_view);

        vh.fruitNameTextView.setText("Implement the rest when DataProvider is finished");
    }
}
