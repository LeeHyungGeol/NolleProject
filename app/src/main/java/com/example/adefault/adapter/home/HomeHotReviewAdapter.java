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
import com.example.adefault.model.home.HomeHotReviewItem;

import java.util.List;

public class HomeHotReviewAdapter extends RecyclerView.Adapter<HomeHotReviewAdapter.HomeMyPickViewHolder>{

    private final List<HomeHotReviewItem> mDataList;

    public interface HomeMyPickClickListener {
        void onHotReviewItemClicked(int position);
        void onHotReviewUserImageClicked(int position);
        void onHotReviewUserNameClicked(int position);
        void onHotReviewPlaceImageClicked(int position);
    }

    private HomeMyPickClickListener mListener;

    public void setOnClickListener(HomeMyPickClickListener listener) {
        mListener = listener;
    }

    public HomeHotReviewAdapter(List<HomeHotReviewItem> dataList) {
        mDataList = dataList;
    }

    @NonNull
    @Override
    public HomeMyPickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item_hot_review, parent, false);
        return new HomeMyPickViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeMyPickViewHolder holder, int position) {
        HomeHotReviewItem item = mDataList.get(position);
        holder.userName.setText(item.getHomeHotUserName());
        ImageManager.getInstance().GlideWithView(holder.itemView, holder.userImage, item.getHomeHotUserImg_url());
        ImageManager.getInstance().GlideWithView(holder.itemView, holder.placeImage, item.getHomeHotPlaceImg_url());

        if(mListener != null) {
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onHotReviewItemClicked(pos); // pos == holder.getAdapterPosition()
                }
            });
            holder.userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onHotReviewUserImageClicked(pos);
                }
            });
            holder.userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onHotReviewUserNameClicked(pos);
                }
            });
            holder.placeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onHotReviewPlaceImageClicked(pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class HomeMyPickViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        ImageView userImage;
        ImageView placeImage;

        public HomeMyPickViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.imageView_homeFragment_hot_review_user_image);
            userName = itemView.findViewById(R.id.textView_homeFragment_hot_review_user_name);
            placeImage = itemView.findViewById(R.id.imageView_homeFragment_hot_review_placeImage);
        }
    }

}
