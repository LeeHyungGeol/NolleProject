package com.example.adefault.model;

import java.util.ArrayList;

public class Follow {
    private int idx;
    private String nickname;
    private String user_email;
    private String image;
    private ArrayList<FollwingList> follow_list;
    private ArrayList<FollowerList> follower_list;

    public ArrayList<FollowerList> getFollower_list() {
        return follower_list;
    }

    public void setFollower_list(ArrayList<FollowerList> follower_list) {
        this.follower_list = follower_list;
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

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<FollwingList> getFollow_list() {
        return follow_list;
    }

    public void setFollow_list(ArrayList<FollwingList> follow_list) {
        this.follow_list = follow_list;
    }
}
