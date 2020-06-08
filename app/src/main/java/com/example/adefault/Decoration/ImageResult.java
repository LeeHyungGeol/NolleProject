package com.example.adefault.Decoration;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImageResult {
    @SerializedName("recommendation") // api 리스폰 시 들어올 age라는 json key
    private JsonObject recommendation;


    public JsonObject getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(JsonObject recommendation) {
        this.recommendation = recommendation;
    }



}