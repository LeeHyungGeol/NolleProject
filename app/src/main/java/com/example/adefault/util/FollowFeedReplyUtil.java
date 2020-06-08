package com.example.adefault.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FollowFeedReplyUtil {

    private FollowFeedAPI followFeedApi;

    public FollowFeedReplyUtil() {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(FollowFeedAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        followFeedApi = mRetrofit.create(FollowFeedAPI.class);
        System.out.println("util 성공일까요 아닐까요......");

    }

    public FollowFeedAPI getAPI() { return followFeedApi; }

}
