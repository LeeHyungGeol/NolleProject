package com.example.adefault;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adefault.adapter.ReplyAdapter;
import com.example.adefault.manager.AppManager;
import com.example.adefault.manager.ImageManager;
import com.example.adefault.model.ReplyDTO;
import com.example.adefault.model.ReplyResponseDTO;
import com.example.adefault.model.ReplyReview_data;
import com.example.adefault.model.ReplyWriteDTO;
import com.example.adefault.model.ReplyWriteResponseDTO;
import com.example.adefault.util.RestApiUtil;
import com.example.adefault.util.UserToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReplyActivity extends AppCompatActivity {

    private String posting_id;
    private RestApiUtil mRestApiUtil;
    private ArrayList<ReplyReview_data> data;
    private RecyclerView recyclerView;
    private ArrayList<ReplyReview_data> dataList;
    private ReplyAdapter replyAdapter;
    private Boolean isLoading;
    private EditText replyText;
    private Button writeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reply);
        init();
    }




    private void init() {
        Intent intent =getIntent();
        posting_id= intent.getStringExtra("posting_id");
        mRestApiUtil = new RestApiUtil();
        data = new ArrayList<>();
        dataList = new ArrayList<>();
        isLoading=false;
        writeBtn = findViewById(R.id.writeBtn);
        replyText = findViewById(R.id.replyText);
        recyclerView = findViewById(R.id.replyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firstData();
        initAdapter();
        initScrollListener();
        addListener();
    }

    private void addListener() {
        writeBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!replyText.getText().toString().equals("")){
                    ReplyWriteDTO replyWriteDTO = new ReplyWriteDTO();
                    replyWriteDTO.setContext(replyText.getText().toString());
                    replyText.setText("");
                    mRestApiUtil.getApi().reply_write("Token "+UserToken.getToken(),replyWriteDTO,posting_id).enqueue(new Callback<ReplyWriteResponseDTO>() {
                        @Override
                        public void onResponse(Call<ReplyWriteResponseDTO> call, Response<ReplyWriteResponseDTO> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"댓글을 작성하였습니다",Toast.LENGTH_SHORT).show();
                                finish();

                            }else{
                                Log.d("댓글쓰기","response실패");
                            }
                        }

                        @Override
                        public void onFailure(Call<ReplyWriteResponseDTO> call, Throwable t) {
                            Log.d("댓글쓰기","통신 실패");
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"댓글 내용을 입력해주세요",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    private void firstData() {
        progressON("댓글을 불러오는중입니다");
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setPosting_id(posting_id);
        mRestApiUtil.getApi().reply("Token "+ UserToken.getToken(), replyDTO).enqueue(new Callback<ReplyResponseDTO>() {
            @Override
            public void onResponse(Call<ReplyResponseDTO> call, Response<ReplyResponseDTO> response) {
                if(response.isSuccessful()){
                    replyAdapter.notifyDataSetChanged();
                    ReplyResponseDTO replyResponseDTO = response.body();
                    int replySize = replyResponseDTO.getReview_data().size();

                    dataList = replyResponseDTO.getReview_data();
                    for(int i=0;i<replySize;i++){
                        data.add(dataList.get(i));
                    }

                    progressOFF();
                    Log.d("댓글을 불러왔습니다","");

                }else{
                    Log.d("reply","response실패");
                }
            }

            @Override
            public void onFailure(Call<ReplyResponseDTO> call, Throwable t) {
                Log.d("reply","통신실패");

            }
        });
    }


    private void dataMore() {
        data.add(null);
        replyAdapter.notifyItemInserted(data.size() -1 );

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                data.remove(data.size() -1 );
                int scrollPosition = data.size();
                replyAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

                for (int i=currentSize; i<nextLimit; i++) {
                    if (i == dataList.size()) {
                        return;
                    }
                    data.add(dataList.get(i));
                }

                replyAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);

    }


    private void initAdapter() {
        replyAdapter = new ReplyAdapter(data,getApplicationContext());
        recyclerView.setAdapter(replyAdapter);
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
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == data.size() - 1) {
                        dataMore();
                        isLoading = true;
                    }
                }
            }
        });
    }


    //확인 버튼 클릭
    public void mOnClose(View v){

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

    public void progressON(String message) {
        ImageManager.getInstance().progressON((Activity) AppManager.getInstance().getContext(), message);
    }
    public void progressOFF() {
        ImageManager.getInstance().progressOFF();
    }
}
