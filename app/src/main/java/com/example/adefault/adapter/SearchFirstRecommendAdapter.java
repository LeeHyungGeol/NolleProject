package com.example.adefault.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adefault.FeedDetailActivity;
import com.example.adefault.PlaceDetailActivity;
import com.example.adefault.data.FirstRecommendData;
import com.example.adefault.R;
import com.example.adefault.manager.AppManager;
import com.example.adefault.util.TaskServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SearchFirstRecommendAdapter extends RecyclerView.Adapter<SearchFirstRecommendAdapter.ViewHolder> {

    private ArrayList<FirstRecommendData> itemList;
    private Context context;
    private View.OnClickListener onClickItem;

    public SearchFirstRecommendAdapter(Context context, ArrayList<FirstRecommendData> itemList, View.OnClickListener onClickItem) {
        this.context = context;
        this.itemList = itemList;
        this.onClickItem = onClickItem;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.search_first_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FirstRecommendData item = itemList.get(position);
        Glide.with(context)
                .load(item.getImage())
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String place_id = item.getPlace_id();
                Intent activityIntent = new Intent(AppManager.getInstance().getContext(), PlaceDetailActivity.class);
                activityIntent.putExtra("place_id", place_id);
                context.startActivity(activityIntent);
            }
        });
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
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
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