package com.example.adefault.FeedPost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adefault.OtherUserPageActivity;
import com.example.adefault.R;
import com.example.adefault.ReplyActivity;
import com.example.adefault.data.FollowFeedRepository;
import com.example.adefault.model.FollowFeedResponseDTO;
import com.example.adefault.model.FollowFeedReview_data;
import com.example.adefault.model.FollowFeedFollow_list;
import com.example.adefault.model.LikeDTO;
import com.example.adefault.model.ReplyDTO;
import com.example.adefault.model.SharedViewModel;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


// 뷰 역할만 갱신
public class FollowFeedFragment extends Fragment implements FollowFeedContract.View,
        FollowFeedRecyclerViewAdapter.FollowFeedClickListener,
        TabAdapter.tabClickListener{

    private static FollowFeedResponseDTO dto;
    private static FollowFeedResponseDTO mFollowFeedResponseDTO;
//    private LikeResponseDTO mLikeResponseDTO;
    private FollowFeedRepository mFollowFeedPostRepository;
    private FollowFeedPresenter mPresenter;
    private FollowFeedRecyclerViewAdapter mAdapterPost;
    private TabAdapter mAdapterTab;

    private ReplyDTO replyDTO;

    private boolean validLike;

    private RecyclerView mRecyclerViewPost, mRecyclerViewTab;
    private ArrayList<FollowFeedFollow_list> recyclerList = new ArrayList<>();



    public FollowFeedFragment() {
    } // 반드시 필요함

    public static FollowFeedFragment newInstace() {
        return new FollowFeedFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        OkHttpClient innerClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES) // read timeout
                .build();
        //////////////////////// view
        View view = inflater.inflate(R.layout.fragment_follow_post, container, false);

        mFollowFeedResponseDTO = getmFollowFeedResponseDTO();

        mRecyclerViewPost = view.findViewById(R.id.recycler_menu_post);
        LinearLayoutManager postLinearLayoutManager = new LinearLayoutManager(getActivity());
        postLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewPost.setLayoutManager(postLinearLayoutManager);

        mAdapterPost = new FollowFeedRecyclerViewAdapter(getActivity(), mFollowFeedResponseDTO, validLike);
        mRecyclerViewPost.setAdapter(mAdapterPost);

        mRecyclerViewTab = view.findViewById(R.id.recycler_tab);
        LinearLayoutManager tabLinearLayoutManager = new LinearLayoutManager(getActivity());
        tabLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewTab.setLayoutManager(tabLinearLayoutManager);

        mAdapterTab = new TabAdapter(getActivity(), recyclerList);
        mRecyclerViewTab.setAdapter(mAdapterTab);

        // Inflate the layout for this fragment
        return view; // 프래그먼트 완성...?
    }


    public FollowFeedResponseDTO getmFollowFeedResponseDTO() {

        mFollowFeedPostRepository = new FollowFeedRepository();
        System.out.println("getArguments2");
        mPresenter = new FollowFeedPresenter(mFollowFeedPostRepository, this);
        mFollowFeedResponseDTO = mPresenter.callPostData();
//        replyDTO.setPosting_id(mFollowFeedResponseDTO.getFollowFeedReview_data().get().getPosting_id());

        return mFollowFeedResponseDTO;
    }


    // 새로 생기면서 데이터 다시 불러온다? -> 날라갈 때 표시됐던 데이터를 세이브 해놨다가 복원
    // 일단 냅두자..
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("onSaveInstanceState");
    }

    //////////////////////////////////////////////

    @Override
    public void showPostData(FollowFeedResponseDTO followFeedResponseDTO) {

        ArrayList<FollowFeedReview_data> followFeedReview_datas = followFeedResponseDTO.getFollowFeedReview_data();

        System.out.println("null? " + followFeedReview_datas.get(0).getContext());
        mAdapterPost = new FollowFeedRecyclerViewAdapter(getActivity(), followFeedResponseDTO, validLike);
        mRecyclerViewPost.setAdapter(mAdapterPost);

        System.out.println("showFollowList");
        ArrayList<FollowFeedFollow_list> followFeedFollow_list = followFeedResponseDTO.getFollowFeedFollow_list();
        System.out.println(followFeedFollow_list.size());

        mRecyclerViewPost.setHasFixedSize(false); // 점점 추가하거나 그럴려면 false
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerViewPost.setLayoutManager(linearLayoutManager);

        mAdapterTab = new TabAdapter(getActivity(), followFeedFollow_list);//, onClickItem);
        mRecyclerViewTab.setAdapter(mAdapterTab);

        mAdapterPost.setOnCLickListener(this);
        mAdapterTab.setOnCLickListener(this);

        dto = followFeedResponseDTO;
    }

    @Override
    public void showLike(boolean valid) {
        validLike = valid;
    }

    @Override
    public void onFollowListClicked(int position) {
        System.out.println("int " + position);
        String posting_id = dto.getFollowFeedReview_data().get(position).getPosting_id();
        Toast.makeText(getContext(),"ps"+position,Toast.LENGTH_SHORT).show();
        //user_nickname = intent.getStringExtra("user_nickname");
        Intent intent = new Intent(getContext(), OtherUserPageActivity.class);
        intent.putExtra("user_nickname",dto.getFollowFeedReview_data().get(position).getNickname());
        startActivity(intent);

    }


    @Override
    public void onFollowFeedClicked(int position) {

    }

    @Override
    public void onFollowUserPicClicked(int position) {
        int Idx = mFollowFeedResponseDTO.getFollowFeedFollow_list().get(position).getIdx();
    }

    @Override
    public void onFollowUserNameClicked(int position) {
        ArrayList<FollowFeedReview_data> review = dto.getFollowFeedReview_data();

        String posting_id = review.get(position).getPosting_id();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.Main_Frame, UserFragment.newInstance(posting_id));
        fragmentTransaction.commit();
    }

    @Override
    public void onFollowFeedLikeClicked(int position, View v) {
        boolean valid = Boolean.valueOf(dto.getFollowFeedReview_data().get(position).getLike_valid());
        ImageView like = (ImageView) v.findViewById(R.id.image_post_like);

        if(valid) {
            like.setImageResource(R.drawable.fill_heart);
            Toast.makeText(getContext(), "좋아요", Toast.LENGTH_SHORT).show();
        } else {
            like.setImageResource(R.drawable.heart);
            Toast.makeText(getContext(), "좋아요 취소", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFollowFeedReplyClicked(int position, View v) {  //댓글입력
        ArrayList<FollowFeedReview_data> review = dto.getFollowFeedReview_data();
        String posting_id = review.get(position).getPosting_id();
        Intent intent = new Intent(getContext(), ReplyActivity.class);
        intent.putExtra("posting_id",posting_id);
        startActivity(intent);

    }




}