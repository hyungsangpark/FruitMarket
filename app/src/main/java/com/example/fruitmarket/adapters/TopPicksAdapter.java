package com.example.fruitmarket.adapters;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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

import com.example.fruitmarket.R;
import com.example.fruitmarket.activities.DetailsActivity;
import com.example.fruitmarket.models.Fruit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TopPicksAdapter extends RecyclerView.Adapter<TopPicksAdapter.ViewHolder> {

    public static final String TOP_PICKS_KEY = "TOP_PICKS_KEY";

    private List<Fruit> topPicks;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView topPickNameTextView;
        private TextView topPickProducerTextView;
        private ImageView topPickImageView;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this); //setting a click listener for the top pick part
            //Setting the views
            topPickNameTextView = (TextView) view.findViewById(R.id.top_pick_item_name);
            topPickProducerTextView = (TextView) view.findViewById(R.id.top_pick_item_producer);
            topPickImageView = (ImageView) view.findViewById(R.id.top_pick_image);
        }

        @Override
        public void onClick(View v) {
            //When top item is clicked
            Activity activity = (Activity) context;
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("IProduct", (Serializable)topPicks.get(getAdapterPosition()));
            activity.startActivity(intent);
        }
    }

    //passing in the data to be adapted/set to the view
    public TopPicksAdapter(List<Fruit> data) {
        topPicks = data;
        Log.d(TAG, "TopPicksAdapter: topPicks (" + topPicks.size() + ") - " + topPicks);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext(); //setting the context
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.top_pick_item,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopPicksAdapter.ViewHolder vHolder, int position) {
        Fruit thisFruit = topPicks.get(position);
        vHolder.topPickNameTextView.setText(thisFruit.getName());
        vHolder.topPickProducerTextView.setText(thisFruit.getProducer());
        if (thisFruit.getImages() != null) {
            String imageName =  thisFruit.getImages().get(0).split("\\.")[0];
            int imageID = context.getResources().getIdentifier(
                    imageName, "drawable", context.getPackageName());
            Bitmap thumbnail = decodeSampledBitmapFromResource(context.getResources(),imageID, 100, 100);
            vHolder.topPickImageView.setImageBitmap(thumbnail);
//            vHolder.topPickImageView.setImageResource(imageID);
            Log.d(TAG, "onBindViewHolder: thumbnail - " + imageName + " with ID: " + imageID);
        }
    }

    @Override
    public int getItemCount() {
        int numTopPicks;
        if (topPicks == null) {
            numTopPicks = 0;
        } else {
            numTopPicks = topPicks.size();
        }
        return numTopPicks;
    }

    public void addTopPicksData(List<Fruit> mostPopular) {
        topPicks = mostPopular;
        notifyDataSetChanged();
    }

    // copied from stack overflow
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
}