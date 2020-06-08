package com.example.adefault.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FollowFeedLikeUtil {

    private FollowFeedAPI followFeedApi;
    private static Retrofit mRetrofit;

    public FollowFeedLikeUtil() {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(FollowFeedAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        followFeedApi = mRetrofit.create(FollowFeedAPI.class);
        System.out.println("util 성공일까요 아닐까요......");

    }

    public FollowFeedAPI getAPI() { return followFeedApi; }

//    public static Retrofit getRetrofitClient(Context context){
//        if(mRetrofit == null){
////            OkHttpClient okHttpClient = new OkHttpClient.Builder()
////                    .connectTimeout(1, TimeUnit.MINUTES)
////                    .readTimeout(30, TimeUnit.SECONDS)
////                    .writeTimeout(15, TimeUnit.SECONDS)
////                    .build();
//
//            mRetrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
////                    .client(okHttpClient)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return mRetrofit;
//    }

}
