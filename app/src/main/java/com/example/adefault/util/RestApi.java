package com.example.adefault.util;

import com.example.adefault.model.BoardResponseDTO;
import com.example.adefault.model.LoginDTO;
import com.example.adefault.model.LoginResponseDTO;
import com.example.adefault.model.RegisterResponseDTO;
import com.example.adefault.model.User;
import com.example.adefault.model.home.HomeResponseDTO;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface RestApi {
    String BASE_URL = "http://3cd9a0187c82.ngrok.io/";
    //String BASE_URL = "http://172.30.1.42:8000/";

    @POST("loginApp/auth/register/")
    Call<RegisterResponseDTO> register(@Body User user);

    @POST("loginApp/auth/login/")
    Call<LoginResponseDTO> login(@Body LoginDTO loginDTO);

    @Multipart
    @POST("posting/1/")
    Call<BoardResponseDTO> uploadBoard(@Header("Authorization") String token, @PartMap Map<String, RequestBody> boardMaps);

    @GET("homeApp/home/")
    Call<HomeResponseDTO> home(@Header("Authorization") String token);

}
