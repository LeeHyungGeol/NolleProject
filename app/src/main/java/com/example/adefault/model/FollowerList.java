package com.example.adefault.model;

public class FollowerList {
    private int user_idx;
    private String follower_nickname;
    private String follower_image;
    private String follower_name;

    public String getFollower_name() {
        return follower_name;
    }

    public void setFollower_name(String follower_name) {
        this.follower_name = follower_name;
    }

    public int getUser_idx() {
        return user_idx;
    }

    public void setUser_idx(int user_idx) {
        this.user_idx = user_idx;
    }

    public String getFollower_nickname() {
        return follower_nickname;
    }

    public void setFollower_nickname(String follower_nickname) {
        this.follower_nickname = follower_nickname;
    }


    public String getFollower_image() {
        return follower_image;
    }

    public void setFollower_image(String follower_image) {
        this.follower_image = follower_image;
    }
}
