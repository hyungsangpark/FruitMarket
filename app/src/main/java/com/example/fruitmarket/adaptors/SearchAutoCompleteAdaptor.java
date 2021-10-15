package com.example.fruitmarket.adaptors;

import android.content.Context;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchAutoCompleteAdaptor extends ArrayAdapter implements Filterable {

    private static final int MAX_NUM_SUGGESTIONS = 3;
    private static final int MAX_NUM_HISTORY_SUGGESTIONS = 3;

    // mLayoutId refers to the ListView item xml
    private final int mLayoutID;
    private List<String> mSearchItems;
    private List<String> mSearchItemsSuggested;
    private List<String> mSearchHistory;
    private Context mContext;

    public SearchAutoCompleteAdaptor(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mLayoutID = resource;
        mSearchItemsSuggested = objects;
        mSearchItems = new ArrayList<>(objects);
//        mSearchItemsSuggested.removeAll(mSearchItems);
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

        historyIcon.setVisibility(mSearchHistory.contains(currentSearchSuggestion) ? View.VISIBLE : View.INVISIBLE);

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
        return mSearchItemsSuggested.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence == null || charSequence.length() == 0) {
                    filterResults.values = mSearchItems;
                    filterResults.count = mSearchItems.size();
                } else {
                    List<String> result = new ArrayList<>();
                    for (String searchKeyword : mSearchItems) {
                        if (searchKeyword.toLowerCase().contains(charSequence.toString().toLowerCase())) result.add(searchKeyword);
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
                if (filterResults != null && filterResults.count > 0) {
                    mSearchItemsSuggested = (List<String>) filterResults.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }


}
