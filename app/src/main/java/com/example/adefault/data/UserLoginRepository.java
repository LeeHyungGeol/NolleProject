package com.example.adefault.data;

import com.example.adefault.model.LoginDTO;
import com.example.adefault.model.LoginResponseDTO;
import com.example.adefault.model.User;
import com.example.adefault.util.RestApiUtil;

import retrofit2.Callback;

public class UserLoginRepository implements LoginRepository{

    private RestApiUtil mRestApiUtil;
    private LoginDTO mLoginDTO;

    public UserLoginRepository() {
        mRestApiUtil = new RestApiUtil();
    }

    public UserLoginRepository(LoginDTO loginDTO) {
        this();
        this.mLoginDTO = loginDTO;
    }

    @Override
    public boolean isAvailable() {
        if(mLoginDTO.getUsername().toString().length() == 0 || mLoginDTO.getPassword().toString().length() == 0)
            return false;
        else
            return true;
    }

    @Override
    public void getLoginData(Callback<LoginResponseDTO> callback) {
        mRestApiUtil.getApi().login(mLoginDTO)
                .enqueue(callback);
    }
}
