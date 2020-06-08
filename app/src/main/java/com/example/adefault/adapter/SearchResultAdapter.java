package com.example.adefault.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.adefault.R;
import com.example.adefault.data.ResultData;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private ArrayList<ResultData> itemList;
    private Context context;
    private View.OnClickListener onClickItem;

    public SearchResultAdapter(Context context, ArrayList<ResultData> itemList, View.OnClickListener onClickItem) {
        this.context = context;
        this.itemList = itemList;
        this.onClickItem = onClickItem;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.search_result_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ResultData item = itemList.get(position);
        holder.place_image.setImageBitmap(item.getImage());
        holder.place_name.setText(item.getPlaceName());
        holder.place_ratingbar.setRating(item.getRating());
        holder.place_rating.setText(Integer.toString(item.getRating()));
        holder.rcm_person.setText(item.getRcm_person());


//        holder.textview.setOnClickListener(onClickItem);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView place_image;
        public TextView place_name;
        public RatingBar place_ratingbar;
        public TextView place_rating;
        public TextView rcm_person;
        public ViewHolder(View itemView) {
            super(itemView);
            place_image = itemView.findViewById(R.id.place_image);
            place_name = itemView.findViewById(R.id.place_name);
            place_ratingbar = itemView.findViewById(R.id.place_ratingbar);
            place_rating = itemView.findViewById(R.id.place_rating);
            rcm_person = itemView.findViewById(R.id.rcm_person);
        }
    }//View Holder Class

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