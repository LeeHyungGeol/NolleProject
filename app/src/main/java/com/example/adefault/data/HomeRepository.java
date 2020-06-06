package com.example.adefault.data;

import com.example.adefault.model.home.HomeResponseDTO;

import retrofit2.Callback;

public interface HomeRepository {
    boolean isAvailable();
    void getHomeFragmentData(Callback<HomeResponseDTO> callback);
}
