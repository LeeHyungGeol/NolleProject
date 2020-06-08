package com.example.adefault.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FollowFeedResponseDTO {
    @SerializedName("review_data") // api 리스폰 시 들어올 age라는 json key
    private ArrayList<FollowFeedReview_data> followFeedReview_data;
    @SerializedName("follow_list") // api 리스폰 시 들어올 age라는 json key
    private ArrayList<FollowFeedFollow_list> followFeedFollow_list;

    public FollowFeedResponseDTO() {
    }

    public FollowFeedResponseDTO(ArrayList<FollowFeedReview_data> followFeedReview_data, ArrayList<FollowFeedFollow_list> followFeedFollow_list) {
        this.followFeedReview_data = followFeedReview_data;
        this.followFeedFollow_list = followFeedFollow_list;
    }

    public ArrayList<FollowFeedReview_data> getFollowFeedReview_data() {
        return followFeedReview_data;
    }

//    public JsonObject getReview(int i){
//        return this.getReview_data().get(i);
//    }

    public void setFollowFeedReview_data(ArrayList<FollowFeedReview_data> followFeedReview_data) {
        this.followFeedReview_data = followFeedReview_data;
    }

    public ArrayList<FollowFeedFollow_list> getFollowFeedFollow_list() {
        return followFeedFollow_list;
    }



    public void setFollowFeedFollow_list(ArrayList<FollowFeedFollow_list> followFeedFollow_list) {
        this.followFeedFollow_list = followFeedFollow_list;
    }

    @Override
    public String toString() {
        return "FeedPostResponseDTO{" +
                "review_data=" + followFeedReview_data +
                ", follow_list=" + followFeedFollow_list +
                '}';
    }
}