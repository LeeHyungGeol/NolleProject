package com.example.adefault;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adefault.adapter.MyPageLikeHistoryAdapter;
import com.example.adefault.manager.AppManager;
import com.example.adefault.model.DeleteUserResponseDTO;
import com.example.adefault.model.LikeHistory;
import com.example.adefault.model.LogoutResponseDTO;
import com.example.adefault.model.MyPageResponseDTO;
import com.example.adefault.util.RestApiUtil;
import com.example.adefault.util.UserToken;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyPageFragment extends Fragment implements MyPageLikeHistoryAdapter.MyPageLikeClickListener{

    private View view;
    private Context context;
    private RecyclerView myPageLikeRecyclerView;
    private RestApiUtil mRestApiUtil;
    private TextView myPagePickTextView;
    private TextView myPageLikedTextView;
    private TextView followMapTextview;
    private TextView myPageBoardCnt;
    private TextView myPageFollowerCnt;
    private TextView myPageFollwingCnt;
    private TextView myPageNickName;
    private TextView myPageSex;
    private TextView myPageAge;
    private TextView followMapUserName; //팔로우맵 가운데 자기 이름
    private CircleImageView followMapMainImage; //팔로우맵 가운데 자기 이미지
    private CircleImageView myPageUserImage;
    private Button profileEditBtn;
    private Button logoutBtn;
    private Button deleteUserBtn;
    private ArrayList<LikeHistory> dataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_page, container, false);
        context= container.getContext();
        init();
        setMyPage();
        setFollowMap();
        addListener();
        return view;
    }



    private void init() {
        deleteUserBtn = view.findViewById(R.id.deleteUserBtn);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        profileEditBtn = view.findViewById(R.id.profileEditBtn);
        mRestApiUtil = new RestApiUtil();
        myPageFollowerCnt = view.findViewById(R.id.myPageFollowerCnt);
        myPageFollwingCnt = view.findViewById(R.id.myPageFollwingCnt);
        myPageBoardCnt = view.findViewById(R.id.myPageBoardCnt);
        myPageNickName = view.findViewById(R.id.myPageNickName);
        myPageSex = view.findViewById(R.id.myPageSex);
        myPageAge = view.findViewById(R.id.myPageAge);
        myPageUserImage = view.findViewById(R.id.myPageUserImage);
        myPagePickTextView = view.findViewById(R.id.myPagePickTextView);
        myPageLikedTextView = view.findViewById(R.id.myPageLikedTextview);
        followMapTextview = view.findViewById(R.id.followMapTextview);
        followMapUserName = view.findViewById(R.id.followMapUserName);
        followMapMainImage= view.findViewById(R.id.followMap_main);
        dataList = new ArrayList<>();

        myPageLikeRecyclerView = view.findViewById(R.id.myPageLikeRecyclerView);
        LinearLayoutManager myPageLayoutManager = new LinearLayoutManager(AppManager.getInstance().getContext());
        myPageLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myPageLikeRecyclerView.setLayoutManager(myPageLayoutManager);

        Spannable span1 = (Spannable)myPagePickTextView.getText();
        span1.setSpan(new ForegroundColorSpan(Color.parseColor("#EB6D55")),3,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //텍스트 부분색상
        myPagePickTextView.setText(span1);

        Spannable span2 = (Spannable)myPageLikedTextView.getText();
        span2.setSpan(new ForegroundColorSpan(Color.parseColor("#EB6D55")),0,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        myPageLikedTextView.setText(span2);

        Spannable span3 = (Spannable)followMapTextview.getText();
        span3.setSpan(new ForegroundColorSpan(Color.parseColor("#EB6D55")),0,3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        followMapTextview.setText(span3);
    }

    private void addListener() {
        myPageBoardCnt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyPostingListActivity.class);
                intent.putExtra("user_nickname",myPageNickName.getText().toString());
                startActivity(intent);
            }
        });

        myPageFollwingCnt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyPageFollowingListActivity.class);
                startActivity(intent);
            }
        });

        myPageFollowerCnt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyPageFollwerListActivity.class);
                startActivity(intent);
            }
        });


        profileEditBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PopUpProfileEdit.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRestApiUtil.getApi().logout("Token "+ UserToken.getToken()).enqueue(new Callback<LogoutResponseDTO>() {
                    @Override
                    public void onResponse(Call<LogoutResponseDTO> call, Response<LogoutResponseDTO> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context,"로그아웃되었습니다",Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }
                        else{
                            Log.d("response","실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<LogoutResponseDTO> call, Throwable t) {

                    }
                });

            }
        });
        deleteUserBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRestApiUtil.getApi().delete_user("Token "+ UserToken.getToken()).enqueue(new Callback<DeleteUserResponseDTO>() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context,"회원탈퇴되었습니다",Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }
                        else{
                            Log.d("response","실패");
                        }

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });

            }
        });

    }

    private void setMyPage() {
        mRestApiUtil.getApi().mypage("Token "+ UserToken.getToken()).enqueue(new Callback<MyPageResponseDTO>() {
            @Override
            public void onResponse(Call<MyPageResponseDTO> call, Response<MyPageResponseDTO> response) {
                if(response.isSuccessful()){
                    MyPageResponseDTO myPageResponseDTO = response.body();
                    myPageNickName.setText(myPageResponseDTO.getMypage().getNickname());
                    myPageBoardCnt.setText(String.valueOf(myPageResponseDTO.getMypage().getPosting_cnt()));
                    myPageFollowerCnt.setText(String.valueOf(myPageResponseDTO.getMypage().getFollower_cnt()));
                    myPageFollwingCnt.setText(String.valueOf(myPageResponseDTO.getMypage().getFollowing_cnt()));
                    followMapUserName.setText(myPageResponseDTO.getMypage().getUser_nm());
                    myPageSex.setText(myPageResponseDTO.getMypage().getSex());
                    String[] str = myPageResponseDTO.getMypage().getAge().split("-");
                    myPageAge.setText(String.valueOf(2020-Integer.parseInt(str[0]))+"세");
                    try{
                        Glide.with(getActivity())
                                .load(UserToken.getUrl()+myPageResponseDTO.getMypage().getImage())
                                .into(myPageUserImage);
                        Glide.with(getActivity())
                                .load(UserToken.getUrl()+myPageResponseDTO.getMypage().getImage())
                                .into(followMapMainImage);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    for(int i=0;i<myPageResponseDTO.getMypage().getLike_history().size();i++){
                        dataList.add(myPageResponseDTO.getMypage().getLike_history().get(i));
                    }
                    Log.d("이미지 url",dataList.get(0).getPosting().getImg_url_1());

                    MyPageLikeHistoryAdapter myPageLikeHistoryAdapter = new MyPageLikeHistoryAdapter(dataList);
                    myPageLikeRecyclerView.setAdapter(myPageLikeHistoryAdapter);
                    myPageLikeHistoryAdapter.setOnClickListener(MyPageFragment.this);


                }
                else{
                    Log.d("마이페이지","response 실패");
                }
            }

            @Override
            public void onFailure(Call<MyPageResponseDTO> call, Throwable t) {

            }
        });


    }


    private void setFollowMap() {

    }


    @Override
    public void onMyPageLikeItemClicked(int position) {
        Toast.makeText(getContext(), "image" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMyPageLikeImageClicked(int position) {
        Toast.makeText(getContext(), "image" + position, Toast.LENGTH_SHORT).show();
    }
}
