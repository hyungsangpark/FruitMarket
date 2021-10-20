package com.example.fruitmarket;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fruitmarket.adapters.CategoryFilterAdapter;
import com.example.fruitmarket.adapters.SearchAutoCompleteAdapter;
import com.example.fruitmarket.fruit.Apple;
import com.example.fruitmarket.fruit.Blueberry;
import com.example.fruitmarket.fruit.Feijoa;
import com.example.fruitmarket.fruit.Fruit;
import com.example.fruitmarket.fruit.Kiwifruit;
import com.example.fruitmarket.fruit.Orange;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    private ImageButton searchByVoiceIcon;
    private ActivityResultLauncher<Intent> mStartVoiceSearch;
    private List<String> categories;
    private RecyclerView rvButtons;
    private CategoryFilterAdapter categoryFilterButtonsAdapter;
    private ListView searchSuggestions;
    private SearchAutoCompleteAdapter searchAutoCompleteAdaptor;

    // Perhaps a class like this can be used for each category filter item.
    public static class FilterCategory {
        private final String name;
        private final int color;

        public FilterCategory(String name, int color) {
            this.name = name;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public int getColor() {
            return color;
        }
    }


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
        mStartVoiceSearch = registerForActivityResult(new StartActivityForResult(),
                (ActivityResult result) -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        ArrayList<String> resultString = result.getData().getStringArrayListExtra(
                                RecognizerIntent.EXTRA_RESULTS);
                        searchEditText.setText(Objects.requireNonNull(resultString).get(0));
                    }
                });
        searchByVoiceIcon.setOnClickListener((View view) -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.ENGLISH.toString());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak keyword to search...");

            try {
                mStartVoiceSearch.launch(intent);
            } catch (ActivityNotFoundException e) {
                Toast
                        .makeText(SearchActivity.this, "" + e.getMessage(),
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });

        List<String> filteredCategories = new ArrayList<>();
        Map<String, List<String>> fruitsData = new HashMap<>();

        // Category filter array
        rvButtons = findViewById(R.id.category_filter_recycler_view);
        // TODO: Modify this array to import from a legitimate source of these categories.
//        categories = Arrays.asList("Kiwifruit", "Apple", "Orange", "Blueberry", "Feijoa");
        categories = Arrays.asList("apples", "blueberries", "feijoas", "kiwifruits", "oranges");
        categoryFilterButtonsAdapter = new CategoryFilterAdapter(categories, (selectedCategory) -> {
            if (filteredCategories.contains(selectedCategory))

            filteredCategories.add(selectedCategory);
            // TODO: Included Selected Category as the filter.
            Toast.makeText(getBaseContext(), selectedCategory + " is filtered.", Toast.LENGTH_SHORT).show();
        });
        rvButtons.setAdapter(categoryFilterButtonsAdapter);
        rvButtons.addItemDecoration(new CategoryFilterAdapter.MarginItemDecoration(
                (int) getResources().getDimension(R.dimen.category_filter_button_horizontal_margin),
                (int) getResources().getDimension(R.dimen.category_filter_button_vertical_margin)));

        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvButtons.setLayoutManager(lm);

        // Auto Suggestion
        searchSuggestions = findViewById(R.id.search_suggestions_list_view);
//        List<Fruit> fruitsData = fetchFruitsData();
//        Log.d("searchKeywords", "onCreate: " + fruitsData);

//        List<String> searchKeywords = new ArrayList<>();
//        for (List<String> fruitCollection : fruitsData.values()) {
//            // TODO: Confirm what to include in the search keywords.
////            searchKeywords.add(fruit.getName());
////            searchKeywords.add(fruit.getProducer());
////            searchKeywords.add(fruit.getVariety());
//            searchKeywords.addAll(fruitCollection);
//        }

        searchAutoCompleteAdaptor = new SearchAutoCompleteAdapter(this, R.layout.item_search_suggestion, new ArrayList<>());
        searchSuggestions.setAdapter(searchAutoCompleteAdaptor);
        searchSuggestions.setTextFilterEnabled(true);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchAutoCompleteAdaptor.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        fetchFruitsData(fruitsData);
    }

    private void fetchFruitsData(Map<String, List<String>> fruitsMap) {
//        List<Fruit> fruitsList = new LinkedList<>();
        // TODO: Extract these hard-coded values into DataProviders.
        String[] collections = {"apples", "blueberries", "feijoas", "kiwifruits", "oranges"};
//        Fruit[] fruitCategoryInstances = {new Apple(), new Blueberry(), new Feijoa(), new Kiwifruit(), new Orange()};

//        Map<String, List<String>> fruitsMap = new HashMap<>();
        for (String collection : collections) {
            fruitsMap.put(collection, new ArrayList<>());
        }
//        fruitsMap.keySet().addAll(Arrays.asList(collections));

        Map<String, Fruit> fruitCategoryClasses = new HashMap<>();
        fruitCategoryClasses.put("apples", new Apple());
        fruitCategoryClasses.put("blueberries", new Blueberry());
        fruitCategoryClasses.put("feijoas", new Feijoa());
        fruitCategoryClasses.put("kiwifruits", new Kiwifruit());
        fruitCategoryClasses.put("oranges", new Orange());

        // Getting apples collection from Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        for (String collection : fruitsMap.keySet()) {
            db.collection(collection).get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {
                if (task.isSuccessful()) {
                    for (Fruit fruit : task.getResult().toObjects(fruitCategoryClasses.get(collection).getClass())) {
                        fruitsMap.get(collection).add(fruit.getName());
                        fruitsMap.get(collection).add(fruit.getProducer());
                        fruitsMap.get(collection).add(fruit.getVariety());

//                        fruitsList.add(fruit);
                        Log.i("Parsing " + collection, fruit.getName() + " loaded.");
                    }
                    if (fruitsMap.get(collection).size() > 0) {
                        Log.i("Getting fruits", "Success");
                        // Once the task is successful and data is fetched, propagate the adaptor.
//                        return fruitsList;
//                        searchAutoCompleteAdaptor.updateSearchItems(fruitsMap.get(collection));
                        searchAutoCompleteAdaptor.addSearchItems(fruitsMap.get(collection));
//                        propagateAdaptor(fruitsMap.get(collection));
                    } else {
                        Toast.makeText(getBaseContext(),
                                "Collection was empty!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(getBaseContext(),
                            "Loading apples collection failed from Firestore!",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });
        }
//        return fruitsMap;

//        for (int i = 0; i < fruitCategoryInstances.length; i++) {
//            int finalI = i;
//            db.collection(collections[i]).get().addOnCompleteListener((@NonNull Task<QuerySnapshot> task) -> {
//                if (task.isSuccessful()) {
//                    for (Fruit apple : task.getResult().toObjects(fruitCategoryInstances[finalI].getClass())) {
//                        fruitsList.add(apple);
//                        Log.i("Parsing " + collections[finalI], apple.getName() + " loaded.");
//                    }
//                    if (fruitsList.size() > 0) {
//                        Log.i("Getting fruits", "Success");
//                        // Once the task is successful and data is fetched, propagate the adaptor.
////                        return fruitsList;
////                        propagateAdaptor(fruitsList);
//                    } else {
//                        Toast.makeText(getBaseContext(),
//                                "Collection was empty!",
//                                Toast.LENGTH_LONG)
//                                .show();
//                    }
//                } else {
//                    Toast.makeText(getBaseContext(),
//                            "Loading apples collection failed from Firestore!",
//                            Toast.LENGTH_LONG)
//                            .show();
//                }
//            });
//        }
//        return fruitsList;
    }

//    private void propagateAdaptor(List<String> searchKeywords) {
////        List<String> searchKeywords = new ArrayList<>();
////        for (Fruit fruit : data) {
////            // TODO: Confirm what to include in the search keywords.
////            searchKeywords.add(fruit.getName());
////            searchKeywords.add(fruit.getProducer());
////            searchKeywords.add(fruit.getVariety());
////        }
////        SearchAutoCompleteAdaptor searchAutoCompleteAdaptor = new SearchAutoCompleteAdaptor(this, R.layout.item_search_suggestion, searchKeywords);
////        searchSuggestions.setAdapter(searchAutoCompleteAdaptor);
////        searchSuggestions.setTextFilterEnabled(true);
////        searchEditText.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////            }
////
////            @Override
////            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////                searchAutoCompleteAdaptor.getFilter().filter(charSequence.toString());
////            }
////
////            @Override
////            public void afterTextChanged(Editable editable) {
////            }
////        });
//        searchAutoCompleteAdaptor.updateSearchItems(searchKeywords);
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}