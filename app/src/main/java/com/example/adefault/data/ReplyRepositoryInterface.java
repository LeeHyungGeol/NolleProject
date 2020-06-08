package com.example.adefault.data;

import com.example.adefault.model.ReplyResponseDTO;
import com.example.adefault.model.WriteReplyResponseDTO;

import retrofit2.Callback;

public interface ReplyRepositoryInterface {

    boolean isAvailable();
    void getReply(Callback<ReplyResponseDTO> callback);
    void sendReply(Callback<WriteReplyResponseDTO> callback);
}
