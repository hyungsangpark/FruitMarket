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
        EditText priceEditText, usernameEditText;
        TextView totalOrderTextView, quantityTextView;
        CardView cardViewResults;
        Button confirmButton;
    }

    ViewHolder vh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

//        vh = new ViewHolder();
//        vh.priceEditText = (EditText) findViewById(R.id.price_edit_text);
//        vh.totalOrderTextView = (TextView) findViewById(R.id.total_order_text_view);
//        vh.quantityTextView = findViewById(R.id.quantity_text_view);
//        vh.cardViewResults = (CardView) findViewById(R.id.card_view_result_message);
//        vh.usernameEditText = (EditText) findViewById(R.id.edit_text_username);
//        vh.confirmButton= (Button) findViewById(R.id.confirm_button);
    }
}
