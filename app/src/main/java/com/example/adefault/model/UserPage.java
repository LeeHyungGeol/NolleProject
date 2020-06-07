package com.example.adefault.model;

import java.util.ArrayList;

public class UserPage {
    private int idx;
    private String nickname;
    private String user_nm;
    private String image;
    private String sex;
    private String age;
    private String description;
    private int posting_cnt;
    private int following_cnt;
    private int follower_cnt;
    private ArrayList<LikeHistory> like_history;

    public String getUser_nm() {
        return user_nm;
    }

    public void setUser_nm(String user_nm) {
        this.user_nm = user_nm;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPosting_cnt() {
        return posting_cnt;
    }

    public void setPosting_cnt(int posting_cnt) {
        this.posting_cnt = posting_cnt;
    }

    public int getFollowing_cnt() {
        return following_cnt;
    }

    public void setFollowing_cnt(int following_cnt) {
        this.following_cnt = following_cnt;
    }

    public int getFollower_cnt() {
        return follower_cnt;
    }

    public void setFollower_cnt(int follower_cnt) {
        this.follower_cnt = follower_cnt;
    }

    public ArrayList<LikeHistory> getLike_history() {
        return like_history;
    }

    public void setLike_history(ArrayList<LikeHistory> like_history) {
        this.like_history = like_history;
    }
}
