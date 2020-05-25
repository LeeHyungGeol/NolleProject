package com.example.adefault.data;

import com.example.adefault.model.LoginResponseDTO;

import retrofit2.Callback;

public interface LoginRepository {
    boolean isAvailable(); //정보를 가져 올 수 있느냐 없느냐
    void getLoginData(Callback<LoginResponseDTO> callback);

}
