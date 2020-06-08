package com.example.adefault.data;

import com.example.adefault.model.FollowFeedResponseDTO;
import com.example.adefault.model.LikeDTO;
import com.example.adefault.model.LikeResponseDTO;
import com.example.adefault.util.FollowFeedAPI;
import com.example.adefault.util.FollowFeedLikeUtil;
import com.example.adefault.util.FollowFeedUtil;
import com.example.adefault.util.UserToken;

import retrofit2.Callback;

public class FollowFeedLikeRepository implements FollowFeedLikeRepositoryInter {

    private FollowFeedUtil mMenuPostUtil;

    public LikeDTO mLikeDTO;

    public FollowFeedLikeRepository() {
        FollowFeedLikeUtil followFeedLikeUtil = new FollowFeedLikeUtil();
    }

    public FollowFeedLikeRepository(LikeDTO likeDTO) {
        this();
        mLikeDTO = likeDTO;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public void getHeart(Callback<LikeResponseDTO> callback) {
        mMenuPostUtil.getAPI().getLike(mLikeDTO, "Token "+UserToken.getToken())
                .enqueue(callback);
    }
}
