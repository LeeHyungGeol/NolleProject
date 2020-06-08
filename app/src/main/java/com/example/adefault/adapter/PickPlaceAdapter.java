package com.example.adefault.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.adefault.data.PickData;
import com.example.adefault.R;
import com.github.islamkhsh.CardSliderAdapter;

import java.util.ArrayList;

public class PickPlaceAdapter extends CardSliderAdapter<PickPlaceAdapter.PickViewHolder> {
    private Context context;
    private ArrayList<PickData> itemList;
    private View.OnClickListener onClickItem;

    public PickPlaceAdapter(Context context, ArrayList<PickData> itemList, View.OnClickListener onClickItem) {
        this.context = context;
        this.itemList = itemList;
        this.onClickItem = onClickItem;

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public PickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pick_item, parent, false);

        return new PickViewHolder(view);
    }

//    @Override
//    public void bindVH(int position, MovieViewHolder holder) {
//        //TODO bind item object with item layout view
//    }

    @Override
    public void bindVH(PickViewHolder pickViewHolder, int i) {
        PickData item = itemList.get(i);
        pickViewHolder.imageView.setImageBitmap(itemList.get(i).getImage());
        pickViewHolder.textView2.setText(itemList.get(i).getPlaceName());
    }

    class PickViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public TextView textView2;

        public PickViewHolder(View view) {
            super(view);

            imageView = itemView.findViewById(R.id.image);
            textView2 = itemView.findViewById(R.id.place_name);

        }
    }
}