package com.example.adefault.adapter;

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
import com.example.adefault.model.LikeHistory;

import java.util.List;

public class OtherUserPageLikeHistoryAdapter extends RecyclerView.Adapter<OtherUserPageLikeHistoryAdapter.OtherUserPageLikeViewHolder>{
    private final List<LikeHistory> mDataList;

    public interface OtherUserPageLikeClickListener {
        void onMyPageLikeItemClicked(int position);
        void onMyPageLikeImageClicked(int position);
        void onMyPageLikePlaceNameClicked(int position);
    }

    private OtherUserPageLikeClickListener mListener;


    public void setOnClickListener(OtherUserPageLikeClickListener listener) {
        mListener = listener;
    }

    public OtherUserPageLikeHistoryAdapter(List<LikeHistory> dataList) {
        mDataList = dataList;
    }

    @NonNull
    @Override
    public OtherUserPageLikeHistoryAdapter.OtherUserPageLikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mypage_liked_gallery_item, parent, false);
        return new OtherUserPageLikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherUserPageLikeHistoryAdapter.OtherUserPageLikeViewHolder holder, int position) {


        String url = ImageManager.getInstance().getFullImageString(mDataList.get(position).getPosting().getImg_url_1());
        ImageManager.getInstance().GlideWithView(holder.itemView, holder.userImage, url);
        holder.ratingBar.setRating(mDataList.get(position).getPosting().getRating());
        holder.placeName.setText(mDataList.get(position).getPosting().getPlace_name());


        if(mListener != null) {
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onMyPageLikeItemClicked(pos); // pos == holder.getAdapterPosition()
                }
            });
            holder.userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onMyPageLikeImageClicked(pos);
                }
            });
            holder.placeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onMyPageLikePlaceNameClicked(pos);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    };

    public static class OtherUserPageLikeViewHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        RatingBar ratingBar;
        TextView placeName;

        public OtherUserPageLikeViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.myPageItemView);
            ratingBar = itemView.findViewById(R.id.myPage_liked_ratingstars);
            placeName = itemView.findViewById(R.id.myPage_liked_placeName);
        }
    }

}
