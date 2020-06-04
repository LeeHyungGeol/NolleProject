package com.example.adefault;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.adefault.model.UserPageResponseDTO;
import com.example.adefault.util.RestApiUtil;
import com.example.adefault.util.UserToken;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherUserPageActivity extends AppCompatActivity {

    private TextView otherUserNickName;
    private TextView otherUserSex;
    private TextView otherUserAge;
    private TextView otherUserBoardCnt;
    private TextView otherUserFollwerCnt;
    private TextView otherUserFollwingCnt;
    private TextView otherPageLikedTextview;
    private TextView otherfollowMapTextview;
    private LinearLayout otherPageGallery;
    private CircleImageView otherUserImage;
    private RestApiUtil mRestApiUtil;
    private LayoutInflater inflater;
    private String user_nickname;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_page);
        setActionBar();
        init();
        setMyPage();
        addListener();
    }
    private void setActionBar() {
        CustomActionBar ca = new CustomActionBar(this, getSupportActionBar());
        ca.setActionBar();
    }

    private void init() {
        otherUserNickName = findViewById(R.id.otherUserNickName);
        otherUserSex = findViewById(R.id.otherUserSex);
        otherUserAge = findViewById(R.id.otherUserAge);
        otherUserBoardCnt = findViewById(R.id.otherUserBoardCnt);
        otherUserFollwerCnt = findViewById(R.id.otherUserFollwerCnt);
        otherUserFollwingCnt = findViewById(R.id.otherUserFollwingCnt);
        otherPageLikedTextview = findViewById(R.id.otherPageLikedTextview);
        otherfollowMapTextview = findViewById(R.id.otherfollowMapTextview);
        otherUserImage = findViewById(R.id.otherUserImage);
        otherPageGallery = findViewById(R.id.otherPageGallery);
        mRestApiUtil = new RestApiUtil();
        inflater=LayoutInflater.from(this);
        Spannable span2 = (Spannable)otherPageLikedTextview.getText();
        span2.setSpan(new ForegroundColorSpan(Color.parseColor("#EB6D55")),0,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        otherPageLikedTextview.setText(span2);

        Spannable span3 = (Spannable)otherfollowMapTextview.getText();
        span3.setSpan(new ForegroundColorSpan(Color.parseColor("#EB6D55")),0,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        otherfollowMapTextview.setText(span3);
        Intent intent =getIntent();
        user_nickname = intent.getStringExtra("user_nickname");
    }

    private void setMyPage() {
        mRestApiUtil.getApi().user_page("Token "+ UserToken.getToken(),user_nickname).enqueue(new Callback<UserPageResponseDTO>() {
            @Override
            public void onResponse(Call<UserPageResponseDTO> call, Response<UserPageResponseDTO> response) {
                if(response.isSuccessful()){
                    UserPageResponseDTO userPageResponseDTO = response.body();
                    otherUserNickName.setText(userPageResponseDTO.getUserpage().getNickname());
                    otherUserSex.setText(userPageResponseDTO.getUserpage().getSex());
                    otherUserBoardCnt.setText(String.valueOf(userPageResponseDTO.getUserpage().getPosting_cnt()));
                    otherUserFollwerCnt.setText(String.valueOf(userPageResponseDTO.getUserpage().getFollower_cnt()));
                    otherUserFollwingCnt.setText(String.valueOf(userPageResponseDTO.getUserpage().getFollowing_cnt()));
                    String[] str = userPageResponseDTO.getUserpage().getAge().split("-");
                    otherUserAge.setText(String.valueOf(2020-Integer.parseInt(str[0]))+"세");
                    try{
                        Glide.with(getApplicationContext())
                                .load(UserToken.getUrl()+userPageResponseDTO.getUserpage().getImage())
                                .into(otherUserImage);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    for(int i=0;i<userPageResponseDTO.getUserpage().getLike_history().size();i++){
                        View view = inflater.inflate(R.layout.mypage_liked_gallery_item,otherPageGallery,false);
                        ImageView itemView = view.findViewById(R.id.myPageItemView);
                        Glide.with(getApplicationContext())
                                .load(UserToken.getUrl()+userPageResponseDTO.getUserpage().getLike_history().get(i).getPosting().getImg_url_1())
                                .into(itemView);
                        otherPageGallery.addView(view);
                    }


                }else{

                }

            }

            @Override
            public void onFailure(Call<UserPageResponseDTO> call, Throwable t) {

            }
        });
    }

    private void addListener() {
        otherUserFollwerCnt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OtherUserFollwerListActivity.class);
                intent.putExtra("user_nickname",user_nickname);
                startActivity(intent);


            }
        });

        otherUserFollwingCnt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OtherUserFollowingListActivity.class);
                intent.putExtra("user_nickname",user_nickname);
                startActivity(intent);

            }
        });
    }



}
