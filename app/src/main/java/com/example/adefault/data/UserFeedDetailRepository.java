package com.example.adefault.data;

import com.example.adefault.model.feedDetail.FeedDetailLikeResponseDTO;
import com.example.adefault.model.feedDetail.FeedDetailPickResponseDTO;
import com.example.adefault.model.feedDetail.FeedDetailResponseDTO;
import com.example.adefault.model.feedDetail.LikeDTO;
import com.example.adefault.model.feedDetail.PickDTO;
import com.example.adefault.util.RestApiUtil;

import retrofit2.Callback;

public class UserFeedDetailRepository implements FeedDetailRepository{

    private RestApiUtil mRestApiUtil;
    private String mToken;
    private int mIdx;
    private PickDTO mPickDTO;
    private LikeDTO mLikeDTO;

    public UserFeedDetailRepository() {
        this.mRestApiUtil = new RestApiUtil();
    }

    public UserFeedDetailRepository(String token, int idx) {
        this();
        this.mToken = token;
        this.mIdx = idx;
    }

    public UserFeedDetailRepository(String token, PickDTO pickDTO) {
        this();
        this.mToken = token;
        this.mPickDTO = pickDTO;
    }

    public UserFeedDetailRepository(String token, LikeDTO likeDTO) {
        this();
        this.mToken = token;
        this.mLikeDTO = likeDTO;
    }

    @Override
    public void getFeedDetailData(Callback<FeedDetailResponseDTO> callback) {
        mRestApiUtil.getApi().feed_detail(mToken, mIdx)
                .enqueue(callback);
    }

    @Override
    public void setFeedDetailPick(Callback<FeedDetailPickResponseDTO> callback) {
        mRestApiUtil.getApi().feed_detail_pick(mToken, mPickDTO)
                .enqueue(callback);
    }

    @Override
    public void setFeedDetailLike(Callback<FeedDetailLikeResponseDTO> callback) {
        mRestApiUtil.getApi().feed_detail_like(mToken, mLikeDTO)
                .enqueue(callback);
    }
}
