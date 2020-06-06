package com.example.adefault.model.home;

public class HomeHotReviewItem {

    private String homeHotUserName;
    private String homeHotUserImg_url;
    private String homeHotPlaceName;
    private float homeHotRating;
    private String homeHotPlaceImg_url;

    public HomeHotReviewItem() {
    }

    public HomeHotReviewItem(String homeHotUserName, String homeHotUserImg_url, String homeHotPlaceName, float homeHotRating, String homeHotPlaceImg_url) {
        this.homeHotUserName = homeHotUserName;
        this.homeHotUserImg_url = homeHotUserImg_url;
        this.homeHotPlaceName = homeHotPlaceName;
        this.homeHotRating = homeHotRating;
        this.homeHotPlaceImg_url = homeHotPlaceImg_url;
    }

    public String getHomeHotUserName() {
        return homeHotUserName;
    }

    public void setHomeHotUserName(String homeHotUserName) {
        this.homeHotUserName = homeHotUserName;
    }

    public String getHomeHotUserImg_url() {
        return homeHotUserImg_url;
    }

    public void setHomeHotUserImg_url(String homeHotUserImg_url) {
        this.homeHotUserImg_url = homeHotUserImg_url;
    }

    public String getHomeHotPlaceName() {
        return homeHotPlaceName;
    }

    public void setHomeHotPlaceName(String homeHotPlaceName) {
        this.homeHotPlaceName = homeHotPlaceName;
    }

    public float getHomeHotRating() {
        return homeHotRating;
    }

    public void setHomeHotRating(float homeHotRating) {
        this.homeHotRating = homeHotRating;
    }

    public String getHomeHotPlaceImg_url() {
        return homeHotPlaceImg_url;
    }

    public void setHomeHotPlaceImg_url(String homeHotPlaceImg_url) {
        this.homeHotPlaceImg_url = homeHotPlaceImg_url;
    }
}
