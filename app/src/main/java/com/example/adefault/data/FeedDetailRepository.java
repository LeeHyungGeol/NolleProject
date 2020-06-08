package com.example.adefault.data;

import com.example.adefault.model.feedDetail.FeedDetailLikeResponseDTO;
import com.example.adefault.model.feedDetail.FeedDetailPickResponseDTO;
import com.example.adefault.model.feedDetail.FeedDetailResponseDTO;

import retrofit2.Callback;

public interface FeedDetailRepository {
    void getFeedDetailData(Callback<FeedDetailResponseDTO> callback);
    void setFeedDetailPick(Callback<FeedDetailPickResponseDTO> callback);
    void setFeedDetailLike(Callback<FeedDetailLikeResponseDTO> callback);
}
