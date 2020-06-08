package com.example.adefault.model;

import com.google.gson.annotations.SerializedName;

public class ReplyDTO {

    @SerializedName("posting_id") // api 리스폰 시 들어올 age라는 json key
    private String posting_id;

    public ReplyDTO() { }

    public String getPosting_id() {
        return posting_id;
    }

    public void setPosting_id(String posting_id) {
        this.posting_id = posting_id;
    }
}