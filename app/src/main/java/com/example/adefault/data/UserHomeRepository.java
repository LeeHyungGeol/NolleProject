package com.example.adefault.data;

import com.example.adefault.model.home.HomeResponseDTO;
import com.example.adefault.util.RestApiUtil;

import retrofit2.Callback;

import static com.example.adefault.util.RestApi.BASE_URL;

public class UserHomeRepository implements HomeRepository{

    private RestApiUtil mRestApiUtil;
    private String mToken;

    public UserHomeRepository() {
        this.mRestApiUtil = new RestApiUtil();
    }

    public UserHomeRepository(String token) {
        this();
        this.mToken = token;
    }

    @Override
    public boolean isAvailable() {
        if(mToken.length() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public void getHomeFragmentData(Callback<HomeResponseDTO> callback) {
        mRestApiUtil.getApi().home(mToken)
                .enqueue(callback);
    }
}
