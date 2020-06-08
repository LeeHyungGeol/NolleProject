package com.example.adefault.FeedPost;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adefault.R;
import com.example.adefault.Reply.ReplyFragment;
import com.example.adefault.manager.AppManager;
import com.example.adefault.model.FollowFeedResponseDTO;
import com.example.adefault.model.FollowFeedReview_data;
import com.example.adefault.model.SliderItem;
import com.example.adefault.util.FollowFeedAPI;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import co.lujun.androidtagview.TagContainerLayout;

import static com.example.adefault.util.FollowFeedAPI.BASE_URL;

public class FollowFeedRecyclerViewAdapter extends RecyclerView.Adapter<FollowFeedRecyclerViewAdapter.FeedPostRecyclerViewHolder> {

    private FollowFeedResponseDTO mFollowFeedResponseDTO;
    private boolean validLike;
    private ArrayList<FollowFeedResponseDTO> mFollowFeedResponseDTOS;
    private Context context;
    private String posting_id;

    Drawable drawable;

    private FollowFeedReview_data review;
    private FollowFeedFragment fragment;
    private ReplyFragment replyFragment;

    TagContainerLayout mTagContainerLayout;
    ArrayList<String> tags;


    ///////////////////////////대충 뭐가 문제인지 알거는 같은데...

    public interface FollowFeedClickListener {

        void onFollowFeedClicked(int position);

        void onFollowUserPicClicked(int position);
        void onFollowUserNameClicked(int position);

        void onFollowFeedLikeClicked(int position, View v);
        void onFollowFeedReplyClicked(int position, View v);

    }

    private FollowFeedClickListener mListener;

    public void setOnCLickListener(FollowFeedClickListener listener) {
        mListener = listener;
    }

    public FollowFeedRecyclerViewAdapter(Context context, FollowFeedResponseDTO followFeedResponseDTO, boolean validLike) {
        this.context = context;
        this.mFollowFeedResponseDTO = followFeedResponseDTO;
        this.validLike = validLike;
        System.out.println("ADAPTER 1");
    }

    @NonNull
    @Override
    public FeedPostRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_post_list, parent, false);

        FeedPostRecyclerViewHolder viewHolder = new FollowFeedRecyclerViewAdapter.FeedPostRecyclerViewHolder(view);
        context = parent.getContext();
        System.out.println("ADAPTER 2");

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FeedPostRecyclerViewHolder holder, final int position) {

        System.out.println("ADAPTER 3");

        try {
            review = mFollowFeedResponseDTO.getFollowFeedReview_data().get(position);

            tags = new ArrayList<>();


            System.out.println(BASE_URL + review.getImage().substring(1));
            Glide.with(context).load(BASE_URL + review.getImage().substring(1)).into(holder.image_post_user_pic);
            holder.text_post_user_nickname.setText(review.getNickname());
            holder.text_post_date.setText(review.getDate());

            holder.text_post_context.setText(review.getContext());
            System.out.println("review 1 " + review.getContext());
//                if(review.getLike_cnt() == null) holder.text_like_cnt.setText("0 명이 좋아합니다.");
//                else holder.text_like_cnt.setText(review.getLike_cnt() + " 명이 좋아합니다.");

            if (review.getTag_1() != null) tags.add(review.getTag_1());
            if (review.getTag_2() != null) tags.add(review.getTag_2());
            if (review.getTag_3() != null) tags.add(review.getTag_3());
            if (review.getTag_4() != null) tags.add(review.getTag_4());
            if (review.getTag_5() != null) tags.add(review.getTag_5());
            if (tags != null) mTagContainerLayout.setTags(tags);


//            Glide.with(context).load(BASE_URL + review.getImage().substring(1)).into(holder.image_post_title_picture);
            System.out.println("title");
            holder.text_post_title.setText(review.getPlace_name());

            holder.ratingBar.setRating(review.getRating());
            holder.text_post_rating.setText(String.valueOf(review.getRating()));


            PostImageSliderAdapter adapter = new PostImageSliderAdapter(AppManager.getInstance().getContext());

            System.out.println(BASE_URL + "media/posting/2020/05/19/33/8933623b59084522bc326641eba7fb79.jpg");
//                adapter.addItem(new SliderItem("Default", BASE_URL + "media/posting/2020/05/19/33/8933623b59084522bc326641eba7fb79.jpg"));

            try {
                String str = review.getImg_1();
                System.out.println("str " + str);

                if (mFollowFeedResponseDTO.getFollowFeedReview_data().get(position).getImg_1() != null)
                    adapter.addItem(new SliderItem("Demo image 1", FollowFeedAPI.BASE_URL + review.getImg_1().substring(1)));
                if (mFollowFeedResponseDTO.getFollowFeedReview_data().get(position).getImg_2() != null)
                    adapter.addItem(new SliderItem("Demo image 2", FollowFeedAPI.BASE_URL + review.getImg_2().substring(1)));
                if (mFollowFeedResponseDTO.getFollowFeedReview_data().get(position).getImg_3() != null)
                    adapter.addItem(new SliderItem("Demo image 3", FollowFeedAPI.BASE_URL + review.getImg_3().substring(1)));
                if (mFollowFeedResponseDTO.getFollowFeedReview_data().get(position).getImg_4() != null)
                    adapter.addItem(new SliderItem("Demo image 4", FollowFeedAPI.BASE_URL + review.getImg_4().substring(1)));
                if (mFollowFeedResponseDTO.getFollowFeedReview_data().get(position).getImg_5() != null)
                    adapter.addItem(new SliderItem("Demo image 5", FollowFeedAPI.BASE_URL + review.getImg_5().substring(1)));

            } catch (Exception e) {
                e.printStackTrace();
            }
//                if(review.getImg_1() != null) System.out.println("image_1 " + review.getImg_1());
//                adapter.addItem(new SliderItem("default", "https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"));
            holder.sliderView.setSliderAdapter(adapter);

        } catch (Exception e) {
            System.out.println("null...");
            e.printStackTrace();
        }

        if(mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("cilck1");
                    mListener.onFollowFeedClicked(position);
                    System.out.println("cilck1");
                }
            });
            holder.image_reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("reply");
                    mListener.onFollowFeedReplyClicked(position, v);
                    System.out.println("cilck3");
                }
            });
            holder.image_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onFollowFeedLikeClicked(position, v);
                    System.out.println("cilck4");
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return mFollowFeedResponseDTO.getFollowFeedReview_data() == null ? 0 : mFollowFeedResponseDTO.getFollowFeedReview_data().size();
    }


    public class FeedPostRecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView image_follow_user_pic;
        TextView text_follow_user_nickname;

        ImageView image_post_user_pic;
        TextView text_post_user_nickname;
        TextView text_post_date;

        SliderView sliderView;
        TextView text_post_context;

        ImageView image_reply;
        ImageView image_like;

        TextView text_like_cnt;
        TagContainerLayout tagContainerLayout;

        LinearLayout title;
        TextView text_post_title;
        ImageView image_post_title_picture;

        RatingBar ratingBar;
        TextView text_post_rating;


        public FeedPostRecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);
            System.out.println("ADAPTER 4");

            image_follow_user_pic = itemView.findViewById(R.id.image_follow_user_pic);
            text_follow_user_nickname = itemView.findViewById(R.id.text_follow_user_nickname);

            tagContainerLayout = itemView.findViewById(R.id.title_tag);

            image_post_user_pic = itemView.findViewById(R.id.image_post_user_picture);
            text_post_user_nickname = itemView.findViewById(R.id.text_post_user_nickname);
            text_post_date = itemView.findViewById(R.id.text_post_date);

            text_like_cnt = itemView.findViewById(R.id.text_like_cnt);
            title = itemView.findViewById(R.id.title);
            text_post_title = itemView.findViewById(R.id.text_posting_name);

            image_reply = itemView.findViewById(R.id.image_post_reply);
            image_like = itemView.findViewById(R.id.image_post_like);

            drawable = context.getResources().getDrawable(R.drawable.fill_heart);
            image_like.setImageDrawable(drawable);

            text_like_cnt = itemView.findViewById(R.id.text_like_cnt);


            sliderView = itemView.findViewById(R.id.imageSlider);
            PostImageSliderAdapter adapter = new PostImageSliderAdapter(AppManager.getInstance().getContext());
            sliderView.setSliderAdapter(adapter);

            adapter.addItem(new SliderItem("default", "https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"));

            System.out.println(BASE_URL + "media/posting/2020/05/19/33/8933623b59084522bc326641eba7fb79.jpg");
            adapter.addItem(new SliderItem("Default", BASE_URL + "media/posting/2020/05/19/33/8933623b59084522bc326641eba7fb79.jpg"));


            image_post_title_picture = itemView.findViewById(R.id.image_post_title_picture);
            text_post_context = itemView.findViewById(R.id.text_post_context);

            ratingBar = itemView.findViewById(R.id.ratingBar);

            text_post_rating = itemView.findViewById(R.id.text_rating);

            System.out.println("ADAPTER 5");

        }
    }
}