package com.example.adefault.FeedPost;


import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adefault.R;
import com.example.adefault.model.FollowFeedFollow_list;

import java.util.ArrayList;

import static com.example.adefault.util.FollowFeedAPI.BASE_URL;

public class TabAdapter extends RecyclerView.Adapter<TabAdapter.ViewHolder> {

    private ImageView image_follow_user_pic;
    private TextView text_follow_user_nickname;
    private ArrayList<FollowFeedFollow_list> itemList;
    private Context context;

    public interface tabClickListener {
        void onFollowListClicked(int position);
    }

    private tabClickListener mListener;

    public void setOnCLickListener(tabClickListener listener) {
        mListener = listener;
    }



    public TabAdapter(Context context, ArrayList<FollowFeedFollow_list> itemList){//, View.OnClickListener onClickItem) {
        this.context = context;
        this.itemList = itemList;
//        this.onClickItem = onClickItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.menu_post_follower_tab, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        FollowFeedFollow_list item = itemList.get(position);

        Glide.with(context).load(BASE_URL + item.getImage().substring(1)).into(holder.image_follow_user_pic);
        holder.text_follow_user_nickname.setText(item.getNickname());

        Glide.with(context).load(BASE_URL + itemList.get(position).getImage().substring(1)).into(holder.image_follow_user_pic);
        holder.image_follow_user_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFollowListClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image_follow_user_pic;
        private TextView text_follow_user_nickname;

        public ViewHolder(View itemView) {
            super(itemView);

            image_follow_user_pic = itemView.findViewById(R.id.image_follow_user_pic);
            text_follow_user_nickname = itemView.findViewById(R.id.text_follow_user_nickname);

        }
    }
}