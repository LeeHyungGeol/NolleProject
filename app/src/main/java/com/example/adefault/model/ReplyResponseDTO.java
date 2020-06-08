package com.example.adefault.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.util.ArrayList;


public class ReplyResponseDTO {

    @SerializedName("review_data") // api 리스폰 시 들어올 age라는 json key
    private ArrayList<ReplyReview_data> review_data;

    public ReplyResponseDTO() { }

    public ReplyResponseDTO(ArrayList<ReplyReview_data> review_data) {
        this.review_data = review_data;
    }

    public ArrayList<ReplyReview_data> getReview_data() {
        return review_data;
    }

    public void setReview_data(ArrayList<ReplyReview_data> review_data) {
        this.review_data = review_data;
    }



    private String nickname;
    private String image;
    private String context;
    private String date;

//    public ReplyResponseDTO() {}

    public ReplyResponseDTO(String nickname, String image, String context, String date) {
        this.nickname = nickname;
        this.image = image;
        this.context = context;
        this.date = date;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String reply) {
        this.context = context;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
