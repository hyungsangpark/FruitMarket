package com.example.fruitmarket.adaptors;

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

public class CategoryFilterAdaptor extends RecyclerView.Adapter<CategoryFilterAdaptor.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Chip itemCategoryFilterButton;
        private boolean isSelected;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            // Initialize the view objects.
            itemCategoryFilterButton = itemView.findViewById(R.id.item_category_filter_button);
            isSelected = false;
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

            // TODO: Included Selected Category as the filter.

            Toast.makeText(mContext, clickedCategory + " is filtered.", Toast.LENGTH_SHORT).show();
        }
    }

    public class MarginItemDecoration extends RecyclerView.ItemDecoration {

        private int horizontalMargin;
        private int verticalMargin;

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

    private List<String> mCategories;
    private Context mContext;

    public CategoryFilterAdaptor(List<String> categories) {
        mCategories = categories;
    }

    @NonNull
    @Override
    public CategoryFilterAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View categoryFilterView = inflater.inflate(R.layout.item_category_filter, parent, false);

        ViewHolder holder = new ViewHolder(categoryFilterView);
        return holder;
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
