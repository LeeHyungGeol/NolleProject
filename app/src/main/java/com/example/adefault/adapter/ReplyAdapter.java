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
import com.example.adefault.R;
import com.example.adefault.model.ReplyReview_data;
import com.example.adefault.util.UserToken;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public List<ReplyReview_data> datas;
    private Context context;


    public ReplyAdapter(List<ReplyReview_data> data,Context context) {
        datas=data;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_item, parent, false);
            return new ReplyAdapter.ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.followlist_item_loading, parent, false);
            return new ReplyAdapter.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) { //클릭 이벤트 처리


        if (viewHolder instanceof ReplyAdapter.ItemViewHolder) {
            populateItemRows((ReplyAdapter.ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof ReplyAdapter.LoadingViewHolder) {
            showLoadingView((ReplyAdapter.LoadingViewHolder) viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    @Override
    public int getItemViewType(int position) {
        return datas.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView replyUserName;
        TextView replyContext;
        TextView replyDate;
        CircleImageView replyUserImage;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            replyUserName = itemView.findViewById(R.id.replyUserName);
            replyContext = itemView.findViewById(R.id.replyContext);
            replyDate = itemView.findViewById(R.id.replyDate);
            replyUserImage = itemView.findViewById(R.id.replyUserImage);
        }
    }


    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }

    private void showLoadingView(ReplyAdapter.LoadingViewHolder viewHolder, int position) {
        //
    }

    private void populateItemRows(ReplyAdapter.ItemViewHolder viewHolder, int position) {

        String image = datas.get(position).getImage();
        String content = datas.get(position).getContext();
        String date = datas.get(position).getDate();
        String Name = datas.get(position).getNickname();

        String str;
        String[] dates = date.split("\\.");
        String[] a = dates[0].split("T");
        String[] b = a[0].split("-");
        String[] c = a[1].split(":");
        str= b[1]+"월 "+b[2]+"일 "+c[0]+"시 "+c[1]+"분";

        Glide.with(context)
                .load(UserToken.getUrl()+image)
                .into(viewHolder.replyUserImage);
        viewHolder.replyUserName.setText(Name);
        viewHolder.replyContext.setText(content);
        viewHolder.replyDate.setText(str);

    }
}
