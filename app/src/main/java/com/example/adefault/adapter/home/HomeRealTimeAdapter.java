package com.example.adefault.adapter.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adefault.R;
import com.example.adefault.manager.ImageManager;
import com.example.adefault.model.home.HomeRealTimeItem;

import java.util.List;

public class HomeRealTimeAdapter extends RecyclerView.Adapter<HomeRealTimeAdapter.HomeRealTimeViewHolder>{

    private final List<HomeRealTimeItem> mDataList;

    public interface HomeRealTimeClickListener {
        void onRealTimeItemClicked(int position);
        void onRealTimeUserImageClicked(int position);
        void onRealTimeUserNameClicked(int position);
        void onRealTimePlaceNameClicked(int position);
        void onRealTimePlaceImageClicked(int position);
    }

    private HomeRealTimeClickListener mListener;

    public void setOnClickListener(HomeRealTimeClickListener listener) {
        mListener = listener;
    }

    public HomeRealTimeAdapter(List<HomeRealTimeItem> dataList) {
        mDataList = dataList;
    }

    @NonNull
    @Override
    public HomeRealTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item_real_time_review, parent, false);
        return new HomeRealTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRealTimeViewHolder holder, int position) {
        HomeRealTimeItem item = mDataList.get(position);
        holder.userName.setText(item.getHomeRealUserName());
        ImageManager.getInstance().GlideWithView(holder.itemView, holder.userImage, item.getHomeRealUserImg_url());
        holder.placeName.setText(item.getHomeRealPlaceName());
        holder.placeReview.setText(item.getHomeRealPlaceReview());
        holder.ratingBar.setRating(item.getHomeRealRating());
        ImageManager.getInstance().GlideWithView(holder.itemView, holder.placeImage, item.getHomeRealPlaceImg_url());

        if(mListener != null) {
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onRealTimeItemClicked(pos); // pos = holder.getAdapterPosition()
                }
            });
            holder.userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onRealTimeUserImageClicked(pos);
                }
            });
            holder.userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onRealTimeUserNameClicked(pos);
                }
            });
            holder.placeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onRealTimePlaceImageClicked(pos);
                }
            });
            holder.placeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onRealTimePlaceNameClicked(pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class HomeRealTimeViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        ImageView userImage;
        TextView placeName;
        TextView placeReview;
        ImageView placeImage;
        RatingBar ratingBar;
        public HomeRealTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.textView_homeFragment_real_time_review_user_name);
            userImage = itemView.findViewById(R.id.imageView_homeFragment_real_time_review_user_image);
            placeName = itemView.findViewById(R.id.textView_homeFragment_real_time_review_place_name);
            placeReview = itemView.findViewById(R.id.textView_homeFragment_real_time_review_content);
            placeImage = itemView.findViewById(R.id.imageView_homeFragment_real_time_review_place_image);
            ratingBar = itemView.findViewById(R.id.ratingBar_homeFragment_real_time_review);
        }
    }
}
