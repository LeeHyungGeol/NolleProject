package com.example.adefault.data;

import com.example.adefault.model.FollowFeedResponseDTO;
import com.example.adefault.model.LikeResponseDTO;

import retrofit2.Callback;

// 데이터 추상화
// 나중에 코딩 안 복잡하기 위해서...?
public interface FollowFeedRepositoryInterface {
    boolean isAvailable();

//    void getFollowList(Callback<MenuPostResponseDTO> callback);

    void getPostData(Callback<FollowFeedResponseDTO> callback);
    void getLike(Callback<LikeResponseDTO> callback);
}
