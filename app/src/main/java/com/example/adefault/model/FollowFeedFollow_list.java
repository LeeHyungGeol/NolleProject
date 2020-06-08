package com.example.adefault.model;

public class FollowFeedFollow_list {
    private int idx; // 내가 팔로 한 사람의 아이디
    private String nickname; // 내가 팔로한 사람의 닉넴
    private String image; // 내가 팔로한 사람의 프로필사진

    public FollowFeedFollow_list() {
    }

    public FollowFeedFollow_list(int idx, String nickname, String image) {
        this.idx = idx;
        this.nickname = nickname;
        this.image = image;
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

    @Override
    public String toString() {
        return "Follow_list{" +
                "idx=" + idx +
                ", nickname='" + nickname + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
