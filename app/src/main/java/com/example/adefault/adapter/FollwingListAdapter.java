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
import com.example.adefault.model.FollwingList;
import com.example.adefault.util.UserToken;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollwingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<FollwingList> followers;
    private Context context;

    public FollwingListAdapter(List<FollwingList> followlist,Context context){
        followers=followlist;
        this.context=context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.followlist_item, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.followlist_item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder,final int position) { //클릭 이벤트 처리

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v. getContext();
                Intent intent =new Intent(context, OtherUserPageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("user_nickname",followers.get(position).getFollow_nickname());
                context.startActivity(intent);
            }
        });
        if (viewHolder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return followers == null ? 0 : followers.size();
    }


    @Override
    public int getItemViewType(int position) {
        return followers.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView textView;
        TextView userName;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.followlistImage);
            textView = itemView.findViewById(R.id.followListNickName);
            userName = itemView.findViewById(R.id.followListUserName);
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

        String image = followers.get(position).getFollow_image();
        String nickname = followers.get(position).getFollow_nickname();
        String userName = followers.get(position).getFollow_name();

        Glide.with(context)
                .load(UserToken.getUrl()+image)
                .into(viewHolder.circleImageView);
        viewHolder.textView.setText(nickname);
        viewHolder.userName.setText(userName);


    }
}
