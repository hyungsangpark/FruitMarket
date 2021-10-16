package com.example.fruitmarket.adaptors;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fruitmarket.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchAutoCompleteAdaptor extends ArrayAdapter implements Filterable {

    private static final int MAX_NUM_SUGGESTIONS = 10;
    private static final int MAX_NUM_HISTORY_SUGGESTIONS = 3;

    // mLayoutId refers to the ListView item xml
    private final int mLayoutID;
    private String searchKeyword;
    private List<String> mSearchItems;
    private List<String> mSearchItemsSuggested;
    private List<String> mSearchHistory;
    private Context mContext;

    public SearchAutoCompleteAdaptor(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mLayoutID = resource;
        searchKeyword = "";
        mSearchItemsSuggested = objects;
        mSearchItems = new ArrayList<>(objects);
//        mSearchItemsSuggested.removeAll(mSearchItems);
        mSearchHistory = new ArrayList<>();
        // TODO: Two lines below are for debug purposes.
        mSearchHistory.add("Ho Seok's Orchard");
        mSearchHistory.add("Orangy Orange");
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

        String currentSearchSuggestion = mSearchItemsSuggested.get(position);

        // Set image visibility based on whether it is present in the search history.
        ImageView historyIcon = currentSearchSuggestionItem
                .findViewById(R.id.item_search_suggestion_history_image_view);

        // Only show history icon on keywords that have already been searched.
        historyIcon.setVisibility(mSearchHistory.contains(currentSearchSuggestion) ? View.VISIBLE : View.INVISIBLE);

        TextView searchSuggestionTextView = currentSearchSuggestionItem
                .findViewById(R.id.item_search_suggestion_text_view);
        int matchingTextStartIndex = currentSearchSuggestion.toLowerCase().indexOf(searchKeyword);

        if (matchingTextStartIndex == -1) {
            searchSuggestionTextView.setText(currentSearchSuggestion);
        } else {
            int matchingTextEndIndex = matchingTextStartIndex + searchKeyword.length();

            String text = currentSearchSuggestion.substring(0, matchingTextStartIndex) +
                    "<font face='sans-serif-black'>" +
                    currentSearchSuggestion.substring(matchingTextStartIndex, matchingTextEndIndex) +
                    "</font>" +
                    currentSearchSuggestion.substring(matchingTextEndIndex);
            searchSuggestionTextView.setText(Html.fromHtml(text));
        }

        return currentSearchSuggestionItem;
    }

    @Override
    public int getCount() {
        return Math.min(mSearchItemsSuggested.size(), MAX_NUM_SUGGESTIONS);
//        return mSearchItemsSuggested.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence == null || charSequence.length() == 0) {
                    searchKeyword = "";
                    filterResults.values = mSearchItems;
//                    filterResults.count = mSearchItems.size();
                    filterResults.count = Math.min(mSearchItems.size(), MAX_NUM_SUGGESTIONS);
                } else {
                    searchKeyword = charSequence.toString().toLowerCase();
                    List<String> result = new ArrayList<>();
                    for (String searchItem : mSearchItems) {
                        if (searchItem.toLowerCase().contains(searchKeyword)) result.add(searchItem);
                    }

                    // Assign the data to the FilterResults
                    filterResults.values = result;
                    Log.d("filterResults", result.toString());
                    filterResults.count = result.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mSearchItemsSuggested = (List<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}
