package com.example.fruitmarket.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruitmarket.R;
import com.google.android.material.chip.Chip;

import java.util.List;
import java.util.function.Consumer;

/**
 * Adapter for dealing with the GUI and data for categories.
 */
public class CategoryFilterAdapter extends RecyclerView.Adapter<CategoryFilterAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Chip itemCategoryFilterButton;
        public Consumer<String> filterButtonOnClick;
        private boolean isSelected;

        public ViewHolder(@NonNull View itemView, Consumer<String> filterButtonOnClick) {
            super(itemView);
            itemView.setOnClickListener(this);
            // Initialize the view objects.
            this.itemCategoryFilterButton = itemView.findViewById(R.id.item_category_filter_button);
            this.filterButtonOnClick = filterButtonOnClick;
            this.isSelected = false;
        }

        @Override
        public void onClick(View view) {
            String clickedCategory = mCategories.get(getAdapterPosition());

            if (!isSelected) {
                itemCategoryFilterButton.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.kiwi_green_light)));
                itemCategoryFilterButton.setChipStrokeWidth(0);
                isSelected = true;
            } else {
                itemCategoryFilterButton.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.white)));
                itemCategoryFilterButton.setChipStrokeWidthResource(R.dimen.category_filter_button_stroke_width);
                isSelected = false;
            }

            // Run the on click event.
            filterButtonOnClick.accept(clickedCategory);
        }
    }

    public static class MarginItemDecoration extends RecyclerView.ItemDecoration {

        private final int horizontalMargin;
        private final int verticalMargin;

        public MarginItemDecoration(int horizontalMargin, int verticalMargin) {
            this.horizontalMargin = horizontalMargin;
            this.verticalMargin = verticalMargin;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) == 0) outRect.left = horizontalMargin;
            outRect.right = horizontalMargin;
            outRect.top = verticalMargin;
            outRect.bottom = verticalMargin;
        }
    }

    private final List<String> mCategories;
    private final Consumer<String> filterButtonOnClick;
    private Context mContext;

    public CategoryFilterAdapter(List<String> categories, Consumer<String> filterButtonOnClick) {
        this.mCategories = categories;
        this.filterButtonOnClick = filterButtonOnClick;
    }

    @NonNull
    @Override
    public CategoryFilterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View categoryFilterView = inflater.inflate(R.layout.item_category_filter, parent, false);

        return new ViewHolder(categoryFilterView, filterButtonOnClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String category = mCategories.get(position);

        holder.itemCategoryFilterButton.setText(category);
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }
}
