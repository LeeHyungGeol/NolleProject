package com.example.adefault.data;

import com.example.adefault.model.RegisterResponseDTO;

import retrofit2.Callback;

public interface RegisterRepository {
    boolean isAvailable(); //정보를 가져 올 수 있느냐 없느냐
    void getRegisterData(Callback<RegisterResponseDTO> callback); //정보를 가져오는 함수
}
