package com.example.adefault.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.adefault.R;
import com.example.adefault.data.ReviewData;
import com.example.adefault.util.TaskServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SearchPopularAdapter extends RecyclerView.Adapter<SearchPopularAdapter.ViewHolder> {

    private ArrayList<ReviewData> itemList;
    private Context context;
    private View.OnClickListener onClickItem;

    public SearchPopularAdapter(Context context, ArrayList<ReviewData> itemList, View.OnClickListener onClickItem) {
        this.context = context;
        this.itemList = itemList;
        this.onClickItem = onClickItem;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.popular_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReviewData item = itemList.get(position);
        if(item.getImage()!="none") {
            new DrawUrlImageTask(holder.imageView).execute(TaskServer.ip+item.getImage());

        }else{
            holder.imageView.setImageAlpha(R.drawable.movieposter1);
            }
        holder.name_textView.setText(item.getPlaceName());
        Log.d("tlqkf",Integer.toString(item.getRating()));
        holder.ratingBar.setRating(item.getRating());
        holder.context_textView.setText(item.getReviewText());
//        holder.textview.setText(item);
//        holder.textview.setTag(item);
//        holder.textview.setOnClickListener(onClickItem);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public RatingBar ratingBar;
        public TextView name_textView;
        public TextView context_textView;
        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.place_image);
            ratingBar = itemView.findViewById(R.id.ratingbar);
            name_textView = itemView.findViewById(R.id.place_name);
            context_textView = itemView.findViewById(R.id.review_text);
        }
    }
    private class DrawUrlImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView ivSample;

        public DrawUrlImageTask(ImageView ivSample) {
            this.ivSample = ivSample;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            InputStream in = null;

            try {
                in = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    in.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return bitmap;
        }

        protected void onPostExecute(Bitmap bitmap) {
            ivSample.setImageBitmap(bitmap);
        }
    }
}