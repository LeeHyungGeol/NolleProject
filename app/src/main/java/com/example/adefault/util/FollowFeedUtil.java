package com.example.adefault.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FollowFeedUtil {

    private FollowFeedAPI followFeedApi;

    public FollowFeedUtil() {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(FollowFeedAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        followFeedApi = mRetrofit.create(FollowFeedAPI.class);

    }

    public FollowFeedAPI getAPI() { return followFeedApi; }

}
