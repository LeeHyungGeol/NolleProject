package com.example.adefault.data;

import com.example.adefault.model.RegisterResponseDTO;
import com.example.adefault.model.User;
import com.example.adefault.util.RestApiUtil;

import retrofit2.Callback;

public class UserRegisterRepository implements RegisterRepository{

    private RestApiUtil mRestApiUtil;
    private User mUser;

    public UserRegisterRepository() {
        mRestApiUtil =  new RestApiUtil();
    }

    public UserRegisterRepository(User user) {
        this();
        this.mUser = user;
    }

    @Override
    public boolean isAvailable() {
        if(mUser.getUser_email().toString().length() == 0 || mUser.getPassword().toString().length() == 0 || mUser.getUser_nm().toString().length() == 0
         || mUser.getAge().toString().length() == 0 || mUser.getNickname().toString().length() == 0 || mUser.getSex().toString().length() == 0 )
            return false;

        else if(mUser.getUser_email().toString().length() < 6 || mUser.getPassword().toString().length() < 4)
            return false;

        else
            return true;
    }

    @Override
    public void getRegisterData(Callback<RegisterResponseDTO> callback) {
        mRestApiUtil.getApi().register(mUser)
                .enqueue(callback);
    }
}
