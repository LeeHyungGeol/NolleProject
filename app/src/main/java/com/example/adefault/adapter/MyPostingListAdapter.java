package com.example.adefault.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adefault.OtherUserPageActivity;
import com.example.adefault.R;
import com.example.adefault.model.FollowerList;
import com.example.adefault.model.MyPosting;
import com.example.adefault.util.UserToken;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPostingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<MyPosting> myPostings;
    private Context context;

    public MyPostingListAdapter(List<MyPosting> myPostings,Context context){
        this.myPostings=myPostings;
        this.context=context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_posting_item, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.followlist_item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,final int position) { //클릭 이벤트 처리

        if (viewHolder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return myPostings == null ? 0 : myPostings.size();
    }


    @Override
    public int getItemViewType(int position) {
        return myPostings.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView placeNameTextview;
        TextView placeContextTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.myPostingImage);
            placeNameTextview = itemView.findViewById(R.id.myPostingPlaceName);
            placeContextTextView = itemView.findViewById(R.id.myPostingContext);
        }
    }


    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //
    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {

        String image = myPostings.get(position).getImg_url_1();
        String placeName = myPostings.get(position).getPlace_name();
        String placeContext = myPostings.get(position).getContext();

        Glide.with(context)
                .load(UserToken.getUrl()+image)
                .into(viewHolder.circleImageView);
        viewHolder.placeNameTextview.setText(placeName);
        viewHolder.placeContextTextView.setText(placeContext);

    }
}
