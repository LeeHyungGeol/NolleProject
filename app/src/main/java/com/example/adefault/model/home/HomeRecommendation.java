package com.example.adefault.model.home;

import java.util.ArrayList;

public class HomeRecommendation {

    private int idx;
    private String nickname;
    private String image;
    private ArrayList<RecommendPlace> recommend_place;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
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

    public ArrayList<RecommendPlace> getRecommend_place() {
        return recommend_place;
    }

    public void setRecommend_place(ArrayList<RecommendPlace> recommend_place) {
        this.recommend_place = recommend_place;
    }
}
