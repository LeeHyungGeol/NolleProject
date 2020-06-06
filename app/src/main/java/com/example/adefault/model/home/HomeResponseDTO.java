package com.example.adefault.model.home;

import java.util.ArrayList;

public class HomeResponseDTO {

    private ArrayList<HomeRecommendation> home_recommendation;
    private ArrayList<RealTime> real_time;
    private ArrayList<HotReview> hot;
    private ArrayList<CategoryM> category_m;
    private ArrayList<CategoryS> category_s;

    public ArrayList<HomeRecommendation> getHome_recommendation() {
        return home_recommendation;
    }

    public void setHome_recommendation(ArrayList<HomeRecommendation> home_recommendation) {
        this.home_recommendation = home_recommendation;
    }

    public ArrayList<RealTime> getReal_time() {
        return real_time;
    }

    public void setReal_time(ArrayList<RealTime> real_time) {
        this.real_time = real_time;
    }

    public ArrayList<HotReview> getHot() {
        return hot;
    }

    public void setHot(ArrayList<HotReview> hot) {
        this.hot = hot;
    }

    public ArrayList<CategoryM> getCategory_m() {
        return category_m;
    }

    public void setCategory_m(ArrayList<CategoryM> category_m) {
        this.category_m = category_m;
    }

    public ArrayList<CategoryS> getCategory_s() {
        return category_s;
    }

    public void setCategory_s(ArrayList<CategoryS> category_s) {
        this.category_s = category_s;
    }
}
