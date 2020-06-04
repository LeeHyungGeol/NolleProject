package com.example.adefault.model;

public class FollwingList {
    private int following_idx;
    private String follow_nickname;
    private String follow_image;
    private String follow_name;

    public String getFollow_name() {
        return follow_name;
    }

    public void setFollow_name(String follow_name) {
        this.follow_name = follow_name;
    }

    public int getFollowing_idx() {
        return following_idx;
    }

    public void setFollowing_idx(int following_idx) {
        this.following_idx = following_idx;
    }

    public String getFollow_nickname() {
        return follow_nickname;
    }

    public void setFollow_nickname(String follow_nickname) {
        this.follow_nickname = follow_nickname;
    }


    public String getFollow_image() {
        return follow_image;
    }

    public void setFollow_image(String follow_image) {
        this.follow_image = follow_image;
    }
}
