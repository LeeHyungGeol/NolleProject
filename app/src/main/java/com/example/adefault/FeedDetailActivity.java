package com.example.adefault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adefault.adapter.feedDetail.FeedDetailSliderAdapter;
import com.example.adefault.adapter.home.HomeSliderAdapter;
import com.example.adefault.data.UserFeedDetailRepository;
import com.example.adefault.manager.AppManager;
import com.example.adefault.manager.ImageManager;
import com.example.adefault.model.feedDetail.FeedDetailLikeResponseDTO;
import com.example.adefault.model.feedDetail.FeedDetailPickResponseDTO;
import com.example.adefault.model.feedDetail.FeedDetailResponseDTO;
import com.example.adefault.model.feedDetail.FeedDetailSliderItem;
import com.example.adefault.model.feedDetail.LikeDTO;
import com.example.adefault.model.feedDetail.PickDTO;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedDetailActivity extends AppCompatActivity {

    private UserFeedDetailRepository mUserFeedDetailRepository;
    private UserFeedDetailRepository mFeedDetailLike;
    private UserFeedDetailRepository mFeedDetailPick;
    private FeedDetailResponseDTO mFeedDetailResponseDTO;
    private ConfirmDialog mConfirmDialog;
    private CustomActionBar ca;

    private ImageView iv_anotherUserImage;
    private TextView tv_anotherUserName;
    private RatingBar ratingBar;
    private TextView tv_date;
    private TextView tv_like_cnt;
    private ImageView iv_heart;
    private ImageView iv_pick;
    private ImageView iv_reply;
    private TextView tv_content;
    private TagContainerLayout mTagContainerLayout;
    private List<String> mTags;
    private SliderView mSliderView;
    private FeedDetailSliderAdapter adapter;

    private Bitmap bitmap;
    private Intent intent;
    private int idx;

    private PickDTO mPickDTO;
    private LikeDTO mLikeDTO;
    private Intent activityIntent;
    private final static int PICK_CLICK = 1;
    private final static int LIKE_CLICK = 2;
    private final static int REPLY_CLICK = 3;
    private final static int USER_IMAGE_CLICK = 4;
    private final static int USER_NAME_CLICK = 5;
    private final static int TAG_CLICK = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getInstance().setContext(this); //singleTon pattern
        AppManager.getInstance().setResources(getResources());

        setContentView(R.layout.activity_feed_detail);
        setActionBar();
        initView();
        initListener();
        getFeedDetailData();
    }

    public void initView() {
        intent = getIntent();
        idx = intent.getIntExtra("posting_idx", 0);

        mConfirmDialog = new ConfirmDialog(AppManager.getInstance().getContext());
        iv_anotherUserImage = findViewById(R.id.iv_FeedDetail_anotherUserImage);
        ratingBar = findViewById(R.id.ratingBar_feedDetail);
        tv_date = findViewById(R.id.textView_FeedDetail_date);
        iv_heart = findViewById(R.id.imageView_FeedDetail_heart);
        iv_pick = findViewById(R.id.imageView_FeedDetail_pick);
        iv_reply = findViewById(R.id.imageView_FeedDetail_reply);
        tv_content = findViewById(R.id.textView_feedDetail_content);
        tv_anotherUserName = findViewById(R.id.textView_FeedDetail_user_nickname);
        tv_like_cnt = findViewById(R.id.textView_feedDetail_like_cnt);

        mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tagContainerLayout_feedDetail);
        mTags = new ArrayList<>();

        mSliderView = findViewById(R.id.imageSlider_feedDetail);
        adapter = new FeedDetailSliderAdapter(AppManager.getInstance().getContext());
        mSliderView.setSliderAdapter(adapter);

        mSliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        mSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        mSliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        mSliderView.setScrollTimeInSec(6000);
        mSliderView.setAutoCycle(true);
        mSliderView.startAutoCycle();
    }

    public void initListener() {
        mSliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                mSliderView.setCurrentPagePosition(position);
            }
        });

        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {   // Tag 가 클릭되었을때 실행
                System.out.println("text : " + text);
                Intent intent = new Intent(AppManager.getInstance().getContext(), SearchResultActivity.class);
                intent.putExtra("searchSentence", text);
                intent.putExtra("uri", "");
                startActivity(intent);
            }

            @Override public void onTagLongClick(int position, String text) {

            } @Override public void onSelectedTagDrag(int position, String text) {

            } @Override public void onTagCrossClick(int position) {

            }

        });

        iv_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCase(LIKE_CLICK);
            }
        });

        iv_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCase(PICK_CLICK);
            }
        });

        iv_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCase(REPLY_CLICK);
            }
        });

        iv_anotherUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCase(USER_IMAGE_CLICK);
            }
        });

        tv_anotherUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCase(USER_NAME_CLICK);
            }
        });
    }

    public void setActionBar() {
        ca = new CustomActionBar(this, getSupportActionBar());
        ca.setActionBar();
    }

    public void checkCase(int Case) {
        switch(Case) {
            case PICK_CLICK: // pick 클릭
                setPick();
                break;
            case LIKE_CLICK: // 좋아요 클릭
                setLike();
                break;
            case REPLY_CLICK: //댓글 클릭

                break;
            case USER_IMAGE_CLICK: //유저 프로필 사진 클릭
            case USER_NAME_CLICK:  //유저 닉네임 클릭
                activityIntent = new Intent(AppManager.getInstance().getContext(), OtherUserPageActivity.class);
                activityIntent.putExtra("user_nickname", mFeedDetailResponseDTO.getNickname());
                startActivity(activityIntent);
                break;
        }
    }

    public void setFeedDetailData() {
        ImageManager.getInstance().GlideWithContext(AppManager.getInstance().getContext(), iv_anotherUserImage, ImageManager.getInstance().getFullImageString(mFeedDetailResponseDTO.getImage()));
        tv_anotherUserName.setText(mFeedDetailResponseDTO.getNickname());
        tv_date.setText(mFeedDetailResponseDTO.getDate());
        ratingBar.setRating(mFeedDetailResponseDTO.getRating());
        setPlaceDetailImages();
        setTags();
        tv_content.setText(mFeedDetailResponseDTO.getContext());
        tv_like_cnt.setText("좋아요 " + mFeedDetailResponseDTO.getLike_cnt() + "개"); // null 개 일수도 있음 //서버 수정 바람
        if(mFeedDetailResponseDTO.getLike_valid().contains("True")) { iv_heart.setImageResource(R.drawable.heart_filled); }
        else { iv_heart.setImageResource(R.drawable.heart_blank); }
        if(mFeedDetailResponseDTO.getPick_valid().contains("True")) { iv_pick.setImageResource(R.drawable.pick_fill); }
        else { iv_pick.setImageResource(R.drawable.pick_blank); }
        System.out.println("날짜 : " + mFeedDetailResponseDTO.getDate());
        System.out.println("별점 : " + mFeedDetailResponseDTO.getRating());
    }

    public void getFeedDetailData() {
        progressON("게시글 정보를 불러오는 중입니다...");
        String token = "Token " + AppManager.getInstance().getUser().getToken();

        mUserFeedDetailRepository = new UserFeedDetailRepository(token, idx);
        mUserFeedDetailRepository.getFeedDetailData(new Callback<FeedDetailResponseDTO>() {
            @Override
            public void onResponse(Call<FeedDetailResponseDTO> call, Response<FeedDetailResponseDTO> response) {

                System.out.println("response.isSuccessful : " + response.isSuccessful());
                if(response.isSuccessful()) {
                    mFeedDetailResponseDTO = response.body();
                    setFeedDetailData();
                    progressOFF();
                }
                else {
                    progressOFF();
                    mConfirmDialog.setMessage("onResponse : 서버와 통신이 원활하지 않습니다.");
                    mConfirmDialog.show();
                }
            }

            @Override
            public void onFailure(Call<FeedDetailResponseDTO> call, Throwable t) {
                System.out.println(t.getMessage());
                progressOFF();
                mConfirmDialog.setMessage("onFailure : 서버와 통신이 원활하지 않습니다.");
                mConfirmDialog.show();
            }
        });
    }

    public void setPick() {
        String token = "Token " + AppManager.getInstance().getUser().getToken();
        mPickDTO = new PickDTO(mFeedDetailResponseDTO.getPlace_id());
        mFeedDetailPick = new UserFeedDetailRepository(token, mPickDTO);

        mFeedDetailPick.setFeedDetailPick(new Callback<FeedDetailPickResponseDTO>() {
            @Override
            public void onResponse(Call<FeedDetailPickResponseDTO> call, Response<FeedDetailPickResponseDTO> response) {
                System.out.println("response.isSuccessful : " + response.isSuccessful());
                if(response.isSuccessful()) {
                    FeedDetailPickResponseDTO feedDetailPickResponseDTO = response.body();
                    if(feedDetailPickResponseDTO.getValid().contains("False")) {
                        iv_pick.setImageResource(R.drawable.pick_fill);
                        Toast.makeText(AppManager.getInstance().getContext(), "장소를 PICK 했습니다!!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        iv_pick.setImageResource(R.drawable.pick_blank);
                        Toast.makeText(AppManager.getInstance().getContext(), "장소 PICK 취소", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Log.d("onResponse pick : ", "통신 실패");
                }
            }

            @Override
            public void onFailure(Call<FeedDetailPickResponseDTO> call, Throwable t) {
                Log.d("onResponse pick : ", "통신 실패");
            }
        });
    }

    public void setLike() {
        String token = "Token " + AppManager.getInstance().getUser().getToken();
        mLikeDTO = new LikeDTO(Integer.toString(mFeedDetailResponseDTO.getIdx()));
        mFeedDetailLike = new UserFeedDetailRepository(token, mLikeDTO);

        mFeedDetailLike.setFeedDetailLike(new Callback<FeedDetailLikeResponseDTO>() {
            @Override
            public void onResponse(Call<FeedDetailLikeResponseDTO> call, Response<FeedDetailLikeResponseDTO> response) {
                System.out.println("response.isSuccessful : " + response.isSuccessful());
                if (response.isSuccessful()) {
                    FeedDetailLikeResponseDTO feedDetailLikeResponseDTO = response.body();
                    if(feedDetailLikeResponseDTO.getValid().contains("False")) {
                        iv_heart.setImageResource(R.drawable.heart_filled);
                        Toast.makeText(AppManager.getInstance().getContext(), "좋아요!!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        iv_heart.setImageResource(R.drawable.heart_blank);
                        Toast.makeText(AppManager.getInstance().getContext(), "좋아요 취소", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Log.d("onResponse like : ", "통신 실패");
                }
            }
            @Override
            public void onFailure(Call<FeedDetailLikeResponseDTO> call, Throwable t) {
                Log.d("onResponse like ", "통신 실패");
            }
        });

    }

    public void setPlaceDetailImages() {

        if (mFeedDetailResponseDTO.getImg_url_1() != null)
            adapter.addItem(new FeedDetailSliderItem(ImageManager.getInstance().getFullImageString(mFeedDetailResponseDTO.getImg_url_1())));
        if (mFeedDetailResponseDTO.getImg_url_2() != null)
            adapter.addItem(new FeedDetailSliderItem(ImageManager.getInstance().getFullImageString(mFeedDetailResponseDTO.getImg_url_2())));
        if (mFeedDetailResponseDTO.getImg_url_3() != null)
            adapter.addItem(new FeedDetailSliderItem(ImageManager.getInstance().getFullImageString(mFeedDetailResponseDTO.getImg_url_3())));
        if (mFeedDetailResponseDTO.getImg_url_4() != null)
            adapter.addItem(new FeedDetailSliderItem(ImageManager.getInstance().getFullImageString(mFeedDetailResponseDTO.getImg_url_4())));
        if (mFeedDetailResponseDTO.getImg_url_5() != null)
            adapter.addItem(new FeedDetailSliderItem(ImageManager.getInstance().getFullImageString(mFeedDetailResponseDTO.getImg_url_5())));
    }

    public void setTags() {
        if (mFeedDetailResponseDTO.getTag_1() != null)
            mTags.add(mFeedDetailResponseDTO.getTag_1());
        if (mFeedDetailResponseDTO.getTag_2() != null)
            mTags.add(mFeedDetailResponseDTO.getTag_2());
        if (mFeedDetailResponseDTO.getTag_3() != null)
            mTags.add(mFeedDetailResponseDTO.getTag_3());
        if (mFeedDetailResponseDTO.getTag_4() != null)
            mTags.add(mFeedDetailResponseDTO.getTag_4());
        if (mFeedDetailResponseDTO.getTag_5() != null)
            mTags.add(mFeedDetailResponseDTO.getTag_5());

        mTagContainerLayout.setTags(mTags);
    }

    public void progressON(String message) {
        ImageManager.getInstance().progressON((Activity)AppManager.getInstance().getContext(), message);
    }

    public void progressOFF() {
        ImageManager.getInstance().progressOFF();
    }

}
