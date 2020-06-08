package com.example.adefault.data;

import com.example.adefault.model.FollowFeedResponseDTO;
import com.example.adefault.model.LikeResponseDTO;

import retrofit2.Callback;

public interface FollowFeedLikeRepositoryInter {
    boolean isAvailable();
    void getHeart(Callback<LikeResponseDTO> callback);

}
