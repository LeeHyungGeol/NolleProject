package com.example.adefault.util;

import com.example.adefault.model.LoginDTO;
import com.example.adefault.model.LoginResponseDTO;
import com.example.adefault.model.RegisterResponseDTO;
import com.example.adefault.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RestApi {
    String BASE_URL = "http://e75e600a.ngrok.io/";
    //String BASE_URL = "http://172.30.1.42:8000/";

    @POST("loginApp/auth/register/")
    Call<RegisterResponseDTO> register(@Body User user);

    @POST("loginApp/auth/login/")
    Call<LoginResponseDTO> login(@Body LoginDTO loginDTO);


}
