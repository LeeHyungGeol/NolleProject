package com.example.adefault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import com.example.adefault.adapter.FollwingListAdapter;
import com.example.adefault.model.FollwingList;
import com.example.adefault.model.MyFollowResponseDTO;
import com.example.adefault.model.UserFollowResponseDTO;
import com.example.adefault.util.RestApiUtil;
import com.example.adefault.util.UserToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherUserFollowingListActivity extends AppCompatActivity {

    private RestApiUtil mRestApiUtil;
    private RecyclerView recyclerView;
    private ArrayList<FollwingList> followingList;
    private ArrayList<FollwingList> following;
    private FollwingListAdapter follwingListAdapter;
    private TextView followingListTitle;
    private boolean isLoading;
    private String user_nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_following_list);
        setActionBar();
        init();
        firstData();
        initAdapter();
        initScrollListener();
    }

    private void init() {
        mRestApiUtil = new RestApiUtil();
        recyclerView = findViewById(R.id.otherfollowingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        isLoading=false;
        followingList = new ArrayList<>();
        following = new ArrayList<>();
        followingListTitle = findViewById(R.id.otherfollowingListTitle);
        Intent intent = getIntent();
        user_nickname = intent.getStringExtra("user_nickname");
    }

    private void setActionBar() {
        CustomActionBar ca = new CustomActionBar(this, getSupportActionBar());
        ca.setActionBar();
    }


    private void firstData() {
        mRestApiUtil.getApi().user_follow("Token "+UserToken.getToken(),user_nickname).enqueue(new Callback<UserFollowResponseDTO>() {
            @Override
            public void onResponse(Call<UserFollowResponseDTO> call, Response<UserFollowResponseDTO> response) {
                if(response.isSuccessful()){
                    follwingListAdapter.notifyDataSetChanged();
                    UserFollowResponseDTO userFollowResponseDTO = response.body();
                    followingListTitle.setText(userFollowResponseDTO.getFollow().getNickname()+"님의 팔로우");
                    Spannable span = (Spannable)followingListTitle.getText();
                    span.setSpan(new ForegroundColorSpan(Color.parseColor("#EB6D55")),
                            followingListTitle.getText().length()-3,followingListTitle.getText().length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    followingListTitle.setText(span);
                    followingList = userFollowResponseDTO.getFollow().getFollow_list();
                    for(int i=0;i<followingList.size();i++) {
                        following.add(followingList.get(i));
                    }
                }
                else{
                    Log.d("response","실패");
                }


            }

            @Override
            public void onFailure(Call<UserFollowResponseDTO> call, Throwable t) {

            }
        });


    }


    private void dataMore() {
        following.add(null);
        follwingListAdapter.notifyItemInserted(following.size() -1 );

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                following.remove(following.size() -1 );
                int scrollPosition = following.size();
                follwingListAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

                for (int i=currentSize; i<nextLimit; i++) {
                    if (i == followingList.size()) {
                        return;
                    }
                    following.add(followingList.get(i));
                }

                follwingListAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);

    }


    private void initAdapter() {
        follwingListAdapter = new FollwingListAdapter(following,this);
        recyclerView.setAdapter(follwingListAdapter);
    }

    // 리싸이클러뷰 이벤트시
    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == following.size() - 1) {
                        dataMore();
                        isLoading = true;
                    }
                }
            }
        });
    }


}
