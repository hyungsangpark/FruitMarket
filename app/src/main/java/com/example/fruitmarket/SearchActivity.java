package com.example.fruitmarket;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fruitmarket.adaptors.CategoryFilterAdaptor;
import com.example.fruitmarket.adaptors.SearchAutoCompleteAdaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    private ImageButton searchByVoiceIcon;
    private List<String> categories;
    private CategoryFilterAdaptor categoryFilterButtonsAdapter;
    private RecyclerView rvButtons;
    private ListView searchSuggestions;
    private SearchAutoCompleteAdaptor searchSuggestionsAdaptor;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Enable Tool bar to work.
        setSupportActionBar(findViewById(R.id.search_tool_bar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Configure Speech Recognition components.
        searchEditText = findViewById(R.id.search_edit_text);
        searchByVoiceIcon = findViewById(R.id.search_by_voice_icon);
        searchByVoiceIcon.setOnClickListener((View view) -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.ENGLISH.toString());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
            } catch (Exception e) {
                Toast
                        .makeText(SearchActivity.this, "" + e.getMessage(),
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });

        rvButtons = findViewById(R.id.category_filter_recycler_view);
        // TODO: Modify this array to import from a legitimate source of these categories.
        categories = Arrays.asList("Kiwifruit", "Apple", "Orange", "Blueberry", "Feijoa");
        categoryFilterButtonsAdapter = new CategoryFilterAdaptor(categories);
        rvButtons.setAdapter(categoryFilterButtonsAdapter);
        rvButtons.addItemDecoration(categoryFilterButtonsAdapter.new MarginItemDecoration(
                (int) getResources().getDimension(R.dimen.category_filter_button_horizontal_margin),
                (int) getResources().getDimension(R.dimen.category_filter_button_vertical_margin)));
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvButtons.setLayoutManager(lm);

        searchSuggestions = findViewById(R.id.search_suggestions_list_view);
//        searchSuggestionsAdaptor = new SearchAutoCompleteAdaptor();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                searchEditText.setText(Objects.requireNonNull(result).get(0));
            }
        }
    }
}