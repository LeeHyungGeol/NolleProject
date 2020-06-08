package com.example.adefault.Decoration;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchResult {

    @SerializedName("recommendation")// api 리스폰 시 들어올 name이라는 json key
    private ArrayList<JsonObject> recommendationList;

    /*반드시 게터 세터 를 선언해줘야함*/

    public ArrayList<JsonObject> getRecommendationList() {
        return recommendationList;
    }

    public void setRecommendationList(ArrayList<JsonObject> recommendationList) {
        this.recommendationList = recommendationList;
    }



}