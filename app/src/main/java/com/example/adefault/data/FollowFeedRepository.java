package com.example.adefault.data;

import com.example.adefault.model.FollowFeedResponseDTO;
import com.example.adefault.model.LikeDTO;
import com.example.adefault.model.LikeResponseDTO;
import com.example.adefault.model.User;
import com.example.adefault.util.FollowFeedUtil;
import com.example.adefault.util.UserToken;

import retrofit2.Callback;

//실제 동작하는 정보
//뭐가 있을까............
public class FollowFeedRepository implements FollowFeedRepositoryInterface {

    private FollowFeedUtil mUtil;

    public FollowFeedResponseDTO mFollowFeedResponseDTO;
    public LikeDTO mLikeDTO;
    public LikeResponseDTO mLikeResponseDTO;

    public FollowFeedRepository() {
        mUtil = new FollowFeedUtil();
    }

    public FollowFeedRepository(FollowFeedResponseDTO mFollowFeedResponseDTO) {
        this();
        this.mFollowFeedResponseDTO = mFollowFeedResponseDTO;
    }

    public FollowFeedRepository(LikeDTO likeDTO){
        this();
        this.mLikeDTO = likeDTO;
    }


    @Override
    public boolean isAvailable() {
        if(mFollowFeedResponseDTO != null) return true;
        return false;
    }

    @Override
    public void getPostData(Callback<FollowFeedResponseDTO> callback) {
         mUtil.getAPI().getPostData("Token "+UserToken.getToken())
                 .enqueue(callback);
    }

    @Override
    public void getLike(Callback<LikeResponseDTO> callback) {
        mUtil.getAPI().getLike(mLikeDTO,"Token "+ UserToken.getToken())
                .enqueue(callback);
    }

}
