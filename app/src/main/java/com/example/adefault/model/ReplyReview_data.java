package com.example.adefault.model;

import java.sql.Date;

public class ReplyReview_data {
    private String nickname;
    private String image;
    private String context;
    private String date;

    public ReplyReview_data() {}

    public ReplyReview_data(String nickname, String image, String context, String date) {
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
