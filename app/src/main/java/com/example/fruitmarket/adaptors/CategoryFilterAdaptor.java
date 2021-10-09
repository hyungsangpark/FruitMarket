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
import com.google.android.material.chip.ChipDrawable;

import java.util.List;

public class CategoryFilterAdaptor extends RecyclerView.Adapter<CategoryFilterAdaptor.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Chip itemCategoryFilterButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            // Initialize the view objects.
            itemCategoryFilterButton = itemView.findViewById(R.id.item_category_filter_button);
        }

        @Override
        public void onClick(View view) {
            String clickedCategory = mCategories.get(getAdapterPosition());

            itemCategoryFilterButton.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.kiwi_green_light)));
            itemCategoryFilterButton.setChipStrokeWidth(0);

            // TODO: Included Selected Category as the filter.

            Toast.makeText(mContext, clickedCategory + " is filtered.", Toast.LENGTH_SHORT).show();
        }
    }

    public class MarginItemDecoration extends RecyclerView.ItemDecoration {

        private int verticalMargin;
        private int horizontalMargin;

        public MarginItemDecoration(int verticalMargin, int horizontalMargin) {
            this.verticalMargin = verticalMargin;
            this.horizontalMargin = horizontalMargin;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.top = verticalMargin;
            outRect.bottom = verticalMargin;
            outRect.left = parent.getChildAdapterPosition(view) == 0 ? horizontalMargin * 2 : horizontalMargin;
            outRect.right = parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() ? horizontalMargin * 2 : horizontalMargin;
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
