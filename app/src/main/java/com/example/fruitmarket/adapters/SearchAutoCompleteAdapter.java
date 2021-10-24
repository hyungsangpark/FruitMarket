package com.example.fruitmarket.adapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fruitmarket.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Adapter for dealing with the GUI and data in SearchActivity that are related to auto complete.
 */
public class SearchAutoCompleteAdapter extends ArrayAdapter implements Filterable {

    private static final int MAX_NUM_SUGGESTIONS = 10;
    public static final String NO_RESULT_DESCRIPTION = "No results with: ";

    // mLayoutId refers to the ListView item xml
    private final int mLayoutID;
    private String searchKeyword;
    private List<String> mSearchItems;
    private List<String> mSearchItemsSuggested;
    private List<String> mSearchHistory;
    private Context mContext;

    public SearchAutoCompleteAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mLayoutID = resource;
        searchKeyword = "";
        mSearchItems = new ArrayList<>(objects);
        mSearchItemsSuggested = new ArrayList<>();
        mSearchHistory = new ArrayList<>();
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
            if (!currentSearchSuggestion.startsWith(NO_RESULT_DESCRIPTION)) {
                int matchingTextEndIndex = matchingTextStartIndex + searchKeyword.length();

                String text = currentSearchSuggestion.substring(0, matchingTextStartIndex) +
                        "<font face='sans-serif-black'>" +
                        currentSearchSuggestion.substring(matchingTextStartIndex, matchingTextEndIndex) +
                        "</font>" +
                        currentSearchSuggestion.substring(matchingTextEndIndex);
                searchSuggestionTextView.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
            } else {
                searchSuggestionTextView.setText(currentSearchSuggestion);
            }
        }

        return currentSearchSuggestionItem;
    }

    @Override
    public int getCount() {
        int numSuggestedItems;
        if (mSearchItemsSuggested == null) {
            numSuggestedItems = 0;
        } else {
            numSuggestedItems = mSearchItemsSuggested.size();
        }
        return Math.min(numSuggestedItems, MAX_NUM_SUGGESTIONS);
    }

    public void updateSearchItems(List<String> newItems) {
        mSearchItems = newItems;
        getFilter().filter(searchKeyword);
    }

    public void addSearchItems(Collection<String> newItems) {
        mSearchItems.addAll(newItems);
        getFilter().filter(searchKeyword);
    }

    public void addKeywordSearched(String searchedKeyword) {
        mSearchHistory.add(searchedKeyword);
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
                    String previousItem = "";
                    for (String searchItem : mSearchItems) {
                        try {
                            if (searchItem.toLowerCase().contains(searchKeyword)) result.add(searchItem);
                        } catch (NullPointerException e) {
                            Log.e("NullPointerException", "Previous item: " + previousItem + ". Maybe check firebase to see whether it's missing this field?");
                        }
                        previousItem = searchItem;
                    }

                    if (result.isEmpty()) {
                        result.add(NO_RESULT_DESCRIPTION + charSequence.toString());
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
