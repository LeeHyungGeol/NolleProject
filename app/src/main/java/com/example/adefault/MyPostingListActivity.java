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
import android.widget.TextView;

import com.example.adefault.adapter.FollwingListAdapter;
import com.example.adefault.adapter.MyPostingListAdapter;
import com.example.adefault.model.FollwingList;
import com.example.adefault.model.MyFollowResponseDTO;
import com.example.adefault.model.MyPosting;
import com.example.adefault.model.MyPostingResponseDTO;
import com.example.adefault.util.RestApiUtil;
import com.example.adefault.util.UserToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostingListActivity extends AppCompatActivity {

    private RestApiUtil mRestApiUtil;
    private RecyclerView recyclerView;
    private ArrayList<MyPosting> myPostingList;
    private ArrayList<MyPosting> myPosting;
    private MyPostingListAdapter myPostingListAdapter;
    private TextView myPostingTitle;
    private boolean isLoading;
    private String user_nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posting_list);
        setActionBar();
        init();
        firstData();
        initAdapter();
        initScrollListener();
    }

    private void init() {
        mRestApiUtil = new RestApiUtil();
        recyclerView = findViewById(R.id.myPostingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        isLoading=false;
        myPostingList = new ArrayList<>();
        myPosting = new ArrayList<>();
        myPostingTitle = findViewById(R.id.myPostingTitle);
        Intent intent = getIntent();
        user_nickname=intent.getStringExtra("user_nickname");
    }

    private void setActionBar() {
        CustomActionBar ca = new CustomActionBar(this, getSupportActionBar());
        ca.setActionBar();
    }

    private void firstData(){
        mRestApiUtil.getApi().my_posting("Token "+ UserToken.getToken()).enqueue(new Callback<MyPostingResponseDTO>() {
            @Override
            public void onResponse(Call<MyPostingResponseDTO> call, Response<MyPostingResponseDTO> response) {
                if(response.isSuccessful()){
                    myPostingListAdapter.notifyDataSetChanged();
                    MyPostingResponseDTO myPostingResponseDTO = response.body();
                    myPostingTitle.setText(user_nickname+"님의 게시글");
                    Spannable span = (Spannable)myPostingTitle.getText();
                    span.setSpan(new ForegroundColorSpan(Color.parseColor("#EB6D55")),
                            myPostingTitle.getText().length()-3,myPostingTitle.getText().length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    myPostingTitle.setText(span);
                    myPostingList = myPostingResponseDTO.getMy_posting();
                    for(int i=0;i<myPostingList.size();i++){
                        myPosting.add(myPostingList.get(i));
                    }
                }
            }

            @Override
            public void onFailure(Call<MyPostingResponseDTO> call, Throwable t) {

            }
        });
    }


    private void dataMore() {
        myPosting.add(null);
        myPostingListAdapter.notifyItemInserted(myPosting.size() -1 );

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myPosting.remove(myPosting.size() -1 );
                int scrollPosition = myPosting.size();
                myPostingListAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

                for (int i=currentSize; i<nextLimit; i++) {
                    if (i == myPostingList.size()) {
                        return;
                    }
                    myPosting.add(myPostingList.get(i));
                }

                myPostingListAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);

    }


    private void initAdapter() {
        myPostingListAdapter = new MyPostingListAdapter(myPosting,this);
        recyclerView.setAdapter(myPostingListAdapter);
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
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == myPosting.size() - 1) {
                        dataMore();
                        isLoading = true;
                    }
                }
            }
        });
    }
}
