package com.example.adefault.FeedPost;

import android.app.Activity;
import android.util.Log;

import com.example.adefault.data.FollowFeedLikeRepository;
import com.example.adefault.data.FollowFeedRepository;
import com.example.adefault.manager.AppManager;
import com.example.adefault.manager.ImageManager;
import com.example.adefault.model.FollowFeedResponseDTO;
import com.example.adefault.model.LikeResponseDTO;
import com.example.adefault.util.FollowFeedAPI;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FollowFeedPresenter implements FollowFeedContract.Presenter {

    private final FollowFeedRepository mFollowFeedRepository;
    private final FollowFeedContract.View mView;
    private FollowFeedResponseDTO followFeedResponseDTO;
    private LikeResponseDTO likeResponseDTO;
    private boolean valid;

    public FollowFeedPresenter(FollowFeedRepository repository, FollowFeedContract.View view) {
        this.mFollowFeedRepository = repository;
        this.mView = view;
    }


    public FollowFeedResponseDTO callPostData() {
        progressON("피드를 불러오는 중입니다");
        final FollowFeedResponseDTO[] mFollowReedResponseDTO = {new FollowFeedResponseDTO()};
//        if (mRepository.isAvailable()) {
//            mRepository.getPostData(callback);
        mFollowFeedRepository.getPostData(new Callback<FollowFeedResponseDTO>() {
            @Override
            public void onResponse(Call<FollowFeedResponseDTO> call, Response<FollowFeedResponseDTO> response) {
                if(response.isSuccessful()){
                    mFollowReedResponseDTO[0] = response.body();
                    System.out.println("Dto " + mFollowReedResponseDTO[0].getFollowFeedReview_data().size());
                    mView.showPostData(mFollowReedResponseDTO[0]);
                    progressOFF();
                }
                else Log.d("response", "fail");
            }

            @Override
            public void onFailure(Call<FollowFeedResponseDTO> call, Throwable t) {
                Log.d("fail", "fail");
                t.printStackTrace();
            }
        });
        return mFollowReedResponseDTO[0];

    }

    public void progressON(String message) {
        ImageManager.getInstance().progressON((Activity) AppManager.getInstance().getContext(), message);
    }

    public void progressOFF() {
        ImageManager.getInstance().progressOFF();
    }

}