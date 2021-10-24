package com.example.fruitmarket.adapters;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruitmarket.models.Fruit;
import com.example.fruitmarket.R;
import com.example.fruitmarket.activities.DetailsActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for dealing with the GUI and data in DetailsActivity.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    public static final String FRUIT_ITEM_KEY = "FRUIT_ITEM_KEY";

    private List<Fruit> fruitsList;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView itemImageView;
        private TextView itemNameTextView;
        private TextView itemProducerTextView;
        private TextView itemPriceTextView;

        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this); //Setting an onclick listener for every part
            //Setting the views from the xml
            itemImageView = (ImageView) v.findViewById(R.id.list_item_image);
            itemNameTextView = (TextView) v.findViewById(R.id.list_item_name);
            itemProducerTextView = (TextView) v.findViewById(R.id.list_item_producer);
            itemPriceTextView = (TextView) v.findViewById(R.id.list_item_price);
        }

        @Override
        public void onClick(View v) {
            //Setting the context
            Activity activity = (Activity) context;

            //Creating new intent to go to the details activity
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("IProduct", (Serializable)fruitsList.get(getAdapterPosition()));

            //Start activity and sets the slide transitions
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    public ListAdapter(List<Fruit> fruitsList) {this.fruitsList = fruitsList;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItemView = inflater.inflate(R.layout.list_item_horizontal, parent, false);
        ViewHolder holder = new ViewHolder(listItemView);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {

        //Sets the name, price and image of the part into the recycler view
        Fruit thisFruit = fruitsList.get(position);
        holder.itemNameTextView.setText(thisFruit.getName());
        holder.itemProducerTextView.setText(thisFruit.getProducer());
        holder.itemPriceTextView.setText("$" + thisFruit.getPrice() + " " + thisFruit.getPriceMetric().toString());
        if (thisFruit.getImages() != null) {
            String imageName =  thisFruit.getImages().get(0).split("\\.")[0];
            int imageID = context.getResources().getIdentifier(
                    imageName, "drawable", context.getPackageName());
            Bitmap thumbnail = decodeSampledBitmapFromResource(context.getResources(),imageID, 100, 100);
            holder.itemImageView.setImageBitmap(thumbnail);
            Log.d(TAG, "onBindViewHolder: thumbnail - " + imageName + " with ID: " + imageID);
        }
    }

    /**
     * Decode the bitmap images so that images that are very large can be compressed.
     * @param res - resource to be compressed
     * @param resId - the id of the resource
     * @param reqWidth - witdth of the resource
     * @param reqHeight -height of the resource
     * @return - decoded bitmap
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * Calculate the size of the image based on the ratios.
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    @Override
    public int getItemCount() {
        return fruitsList.size();
    }
}