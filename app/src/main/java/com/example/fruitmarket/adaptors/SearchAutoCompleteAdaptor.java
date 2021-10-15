package com.example.fruitmarket.adaptors;

import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fruitmarket.R;
import com.example.fruitmarket.fruit.Fruit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchAutoCompleteAdaptor extends ArrayAdapter implements Filterable {

    private static final int MAX_NUM_SUGGESTIONS = 3;
    private static final int MAX_NUM_HISTORY_SUGGESTIONS = 3;

    // mLayoutId refers to the ListView item xml
    private final int mLayoutID;
    private List<String> mSearchItems;
    private List<String> mSearchHistory;
    private Context mContext;

    public SearchAutoCompleteAdaptor(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        mLayoutID = resource;
        mSearchItems = objects;
        mSearchHistory = new ArrayList<>();
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentSearchSuggestionItem = convertView;

        if (currentSearchSuggestionItem == null) {
            currentSearchSuggestionItem = LayoutInflater
                    .from(getContext())
                    .inflate(mLayoutID, parent, false);
        }

        String currentSearchSuggestion = mSearchItems.get(position);

        // Set image visibility based on whether it is present in the search history.
        ImageView historyIcon = currentSearchSuggestionItem
                .findViewById(R.id.item_search_suggestion_history_image_view);

        historyIcon.setVisibility(View.VISIBLE);

        TextView searchSuggestionTextView = currentSearchSuggestionItem
                .findViewById(R.id.item_search_suggestion_text_view);

//        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(currentSearchSuggestion);
//        spannableStringBuilder.setSpan(
//                new StyleSpan(Typeface.BOLD),
//                MATCHING_TEXT_INT_START,
//                MATCHING_TEXT_INT_END,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        );
//        searchSuggestionTextView.setText(spannableStringBuilder);

        searchSuggestionTextView.setText(currentSearchSuggestion);

        return currentSearchSuggestionItem;
    }

    @Override
    public int getCount() {
//        return Math.min(mSearchItems.size(), MAX_NUM_SUGGESTIONS);
        return mSearchItems.size();
    }


}
