package com.example.adefault.Decoration;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Result {

    @SerializedName("home_recommendation")
    private ArrayList<JsonObject> home_recommendation;
    @SerializedName("realtime_posting")// api 리스폰 시 들어올 name이라는 json key
    private ArrayList<JsonObject> realtime_posting;

    public ArrayList<JsonObject> getHome_recommendation() {
        return home_recommendation;
    }

    public void setHome_recommendation(ArrayList<JsonObject> home_recommendation) {
        this.home_recommendation = home_recommendation;
    }

    @SerializedName("hot_posting") // api 리스폰 시 들어올 age라는 json key
    private ArrayList<JsonObject> hot_posting;
    @SerializedName("category_m") // api 리스폰 시 들어올 age라는 json key
    private ArrayList<JsonObject> category_m;
    @SerializedName("category_s") // api 리스폰 시 들어올 age라는 json key
    private ArrayList<JsonObject> category_s;
    @SerializedName("search_history") // api 리스폰 시 들어올 age라는 json key
    private ArrayList<JsonObject> search_history;

    /*반드시 게터 세터 를 선언해줘야함*/

    public ArrayList<JsonObject> getHot_posting() {
        return hot_posting;
    }

    public void setHot_posting(ArrayList<JsonObject> hot_posting) {
        this.hot_posting = hot_posting;
    }

    public ArrayList<JsonObject> getCategory_m() {
        return category_m;
    }

    public void setCategory_m(ArrayList<JsonObject> category_m) {
        this.category_m = category_m;
    }

    public ArrayList<JsonObject> getCategory_s() {
        return category_s;
    }

    public void setCategory_s(ArrayList<JsonObject> category_s) {
        this.category_s = category_s;
    }

    public ArrayList<JsonObject> getRealtime_posting() {
        return realtime_posting;
    }

    public void setRealtime_posting(ArrayList<JsonObject> realtime_posting) {
        this.realtime_posting = realtime_posting;
    }
    public ArrayList<JsonObject> getSearch_history() {
        return search_history;
    }

    public void setSearch_history(ArrayList<JsonObject> search_history) {
        this.search_history = search_history;
    }


}