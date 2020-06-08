package com.example.adefault.model;

public class LikeDTO {

    private String posting_id;

    public LikeDTO() {
    }

    public LikeDTO(String posting_id) {
        this.posting_id = posting_id;
    }

    public String getPosting_id() {
        return posting_id;
    }

    public void setPosting_id(String posting_id) {
        this.posting_id = posting_id;
    }
}
