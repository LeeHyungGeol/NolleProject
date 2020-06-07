package com.example.adefault.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adefault.R;
import com.example.adefault.manager.ImageManager;
import com.example.adefault.model.PlaceData;

import java.util.List;

public class PlaceDetailReviewAdapter extends RecyclerView.Adapter<PlaceDetailReviewAdapter.PlaceDetailViewHolder> {
    private final List<PlaceData> mDataList;


    public interface PlaceDetailClickListener {
        void onPlaceDetailReviewItemClicked(int position);
        void onPlaceDetailReviewImageClicked(int position);
        void onPlaceDetailReviewNickNameClicked(int position);
        void onPlaceDetailReviewContentClicked(int position);
    }

    private PlaceDetailClickListener mListener;


    public void setOnClickListener(PlaceDetailClickListener listener) {
        mListener = listener;
    }

    public PlaceDetailReviewAdapter(List<PlaceData> dataList) {
        mDataList = dataList;
    }


    @NonNull
    @Override
    public PlaceDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_place_review_item, parent, false);
        return new PlaceDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceDetailViewHolder holder, int position) {

        holder.userName.setText(mDataList.get(position).getNickname());
        holder.userContext.setText(mDataList.get(position).getContext());
        String url = ImageManager.getInstance().getFullImageString(mDataList.get(position).getImg_url_1());
        ImageManager.getInstance().GlideWithView(holder.itemView, holder.userImage, url);

        if(mListener != null) {
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onPlaceDetailReviewItemClicked(pos); // pos == holder.getAdapterPosition()
                }
            });
            holder.userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onPlaceDetailReviewImageClicked(pos);
                }
            });
            holder.userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onPlaceDetailReviewNickNameClicked(pos);
                }
            });
            holder.userContext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onPlaceDetailReviewContentClicked(pos);
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return mDataList.size();
    };

    public static class PlaceDetailViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        ImageView userImage;
        TextView userContext;

        public PlaceDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.reviewPictureImageView);
            userName = itemView.findViewById(R.id.reviewIdTextView);
            userContext = itemView.findViewById(R.id.reviewContentTextView);
        }
    }


}
