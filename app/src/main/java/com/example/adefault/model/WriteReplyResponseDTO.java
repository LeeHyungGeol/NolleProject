package com.example.adefault.model;

public class WriteReplyResponseDTO {
    String idx;
    String nickname;
    String context;
    String date;

    public WriteReplyResponseDTO() {
    }

    public WriteReplyResponseDTO(String idx, String nickname, String context, String date) {
        this.idx = idx;
        this.nickname = nickname;
        this.context = context;
        this.date = date;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
