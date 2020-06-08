package com.example.adefault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adefault.adapter.OtherUserPageLikeHistoryAdapter;
import com.example.adefault.manager.AppManager;
import com.example.adefault.model.LikeHistory;
import com.example.adefault.model.OtherUserFollowResponseDTO;
import com.example.adefault.model.UserPageResponseDTO;
import com.example.adefault.util.RestApiUtil;
import com.example.adefault.util.UserToken;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherUserPageActivity extends AppCompatActivity implements OtherUserPageLikeHistoryAdapter.OtherUserPageLikeClickListener{

    private TextView otherUserNickName;
    private TextView otherUserSex;
    private TextView otherUserAge;
    private TextView otherUserBoardCnt;
    private TextView otherUserFollwerCnt;
    private TextView otherUserFollwingCnt;
    private TextView otherPageLikedTextview;
    private CircleImageView otherUserImage;
    private RestApiUtil mRestApiUtil;
    private LayoutInflater inflater;
    private String user_nickname;
    private Button followBtn;
    private ArrayList<LikeHistory> dataList;
    private RecyclerView otherUserPageLikeRecyclerView;

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
        dataList = new ArrayList<>();
        otherUserPageLikeRecyclerView = findViewById(R.id.otherUserPageLikeRecyclerView);
        otherUserNickName = findViewById(R.id.otherUserNickName);
        otherUserSex = findViewById(R.id.otherUserSex);
        otherUserAge = findViewById(R.id.otherUserAge);
        otherUserBoardCnt = findViewById(R.id.otherUserBoardCnt);
        otherUserFollwerCnt = findViewById(R.id.otherUserFollwerCnt);
        otherUserFollwingCnt = findViewById(R.id.otherUserFollwingCnt);
        otherPageLikedTextview = findViewById(R.id.otherPageLikedTextview);
        otherUserImage = findViewById(R.id.otherUserImage);
        mRestApiUtil = new RestApiUtil();
        followBtn = findViewById(R.id.followBtn);
        inflater=LayoutInflater.from(this);
        Spannable span2 = (Spannable)otherPageLikedTextview.getText();
        span2.setSpan(new ForegroundColorSpan(Color.parseColor("#EB6D55")),0,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        otherPageLikedTextview.setText(span2);

        LinearLayoutManager otherUserPageLayoutManager = new LinearLayoutManager(AppManager.getInstance().getContext());
        otherUserPageLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        otherUserPageLikeRecyclerView.setLayoutManager(otherUserPageLayoutManager);

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
                        dataList.add(userPageResponseDTO.getUserpage().getLike_history().get(i));
                    }

                    OtherUserPageLikeHistoryAdapter otherUserPageLikeHistoryAdapter = new OtherUserPageLikeHistoryAdapter(dataList);
                    otherUserPageLikeRecyclerView.setAdapter(otherUserPageLikeHistoryAdapter);
                    otherUserPageLikeHistoryAdapter.setOnClickListener(OtherUserPageActivity.this);

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

        followBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRestApiUtil.getApi().follow("Token "+UserToken.getToken(),user_nickname).enqueue(new Callback<OtherUserFollowResponseDTO>() {
                    @Override
                    public void onResponse(Call<OtherUserFollowResponseDTO> call, Response<OtherUserFollowResponseDTO> response) {
                        if(response.isSuccessful()){
                            OtherUserFollowResponseDTO otherUserFollowResponseDTO = response.body();
                            if(!user_nickname.equals(AppManager.getInstance().getUser().getNickname())){
                                if(otherUserFollowResponseDTO.getCode()==100){
                                    Toast.makeText(getApplicationContext(),otherUserFollowResponseDTO.getMsg(),Toast.LENGTH_SHORT).show();
                                }
                                else if(otherUserFollowResponseDTO.getCode()==101){
                                    Toast.makeText(getApplicationContext(),otherUserFollowResponseDTO.getMsg(),Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"자기 자신과 팔로우 할 수 없습니다",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<OtherUserFollowResponseDTO> call, Throwable t) {

                    }
                });

            }
        });
    }


    @Override
    public void onMyPageLikeItemClicked(int position) {

        Toast.makeText(getApplicationContext(), "item" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMyPageLikeImageClicked(int position) {

        Toast.makeText(getApplicationContext(), "item" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMyPageLikePlaceNameClicked(int position) {

        Toast.makeText(getApplicationContext(), "item" + position, Toast.LENGTH_SHORT).show();
    }
}
