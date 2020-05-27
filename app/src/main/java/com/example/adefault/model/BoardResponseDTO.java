package com.example.adefault.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoardResponseDTO {
    @SerializedName("check")
    @Expose
    private String check;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}
