package com.example.adefault.adapter.feedDetail;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adefault.R;
import com.example.adefault.manager.ImageManager;
import com.example.adefault.model.feedDetail.FeedDetailSliderItem;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class FeedDetailSliderAdapter extends SliderViewAdapter<FeedDetailSliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<FeedDetailSliderItem> mSliderItems = new ArrayList<>();

    public FeedDetailSliderAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<FeedDetailSliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(FeedDetailSliderItem sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public FeedDetailSliderAdapter.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new FeedDetailSliderAdapter.SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(FeedDetailSliderAdapter.SliderAdapterVH viewHolder, final int position) {

        FeedDetailSliderItem sliderItem = mSliderItems.get(position);

        viewHolder.textViewDescription.setText(sliderItem.getDescription());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);

        //viewHolder.imageViewBackground.setImageBitmap(sliderItem.getBitmap()); //비트맵으로 이미지 지정

        ImageManager.getInstance().GlideWithView(viewHolder.itemView, viewHolder.imageViewBackground, sliderItem.getImageUrl()); // url 로 이미지 저장

//        Glide.with(viewHolder.itemView)
//                .load(sliderItem.getImageUrl())
//                .fitCenter()
//                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}
