package com.example.adefault.util;

import com.example.adefault.Decoration.PickResult;
import com.example.adefault.Decoration.Recommend;
import com.example.adefault.Decoration.Result;
import com.example.adefault.Decoration.SearchResult;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    @GET("/homeApp/search/")
    Call<Result> getretrofitdata(@Header("Authorization") String token);

    @POST("/pick/1/")
    Call<PickResult> getretrofitdata_pick(@Header("Authorization") String token);


    @POST("/recommendApp/text/recommend/")
    Call<SearchResult> getretrofitdata_search_result(@Body Recommend recommend, @Header("Authorization") String token);

    @Multipart
    @POST("/recommendApp/image/recommend/")
    Call<SearchResult> getretrofitdata_search_result_image(@Part MultipartBody.Part file,@Header("Authorization") String token);


}
