package com.example.adefault.model.home;

public class HomeRealTimeItem {

    private String homeRealPlaceName;
    private String homeRealPlaceReview;
    private float homeRealRating;
    private String homeRealPlaceImg_url;
    private String homeRealUserName;
    private String homeRealUserImg_url;

    public HomeRealTimeItem() {
    }

    public HomeRealTimeItem(String homeRealPlaceName, String homeRealPlaceReview, float homeRealRating, String homeRealPlaceImg_url, String homeRealUserName, String homeRealUserImg_url) {
        this.homeRealPlaceName = homeRealPlaceName;
        this.homeRealPlaceReview = homeRealPlaceReview;
        this.homeRealRating = homeRealRating;
        this.homeRealPlaceImg_url = homeRealPlaceImg_url;
        this.homeRealUserName = homeRealUserName;
        this.homeRealUserImg_url = homeRealUserImg_url;
    }

    public String getHomeRealPlaceName() {
        return homeRealPlaceName;
    }

    public void setHomeRealPlaceName(String homeRealPlaceName) {
        this.homeRealPlaceName = homeRealPlaceName;
    }

    public String getHomeRealPlaceReview() {
        return homeRealPlaceReview;
    }

    public void setHomeRealPlaceReview(String homeRealPlaceReview) {
        this.homeRealPlaceReview = homeRealPlaceReview;
    }

    public float getHomeRealRating() {
        return homeRealRating;
    }

    public void setHomeRealRating(float homeRealRating) {
        this.homeRealRating = homeRealRating;
    }

    public String getHomeRealPlaceImg_url() {
        return homeRealPlaceImg_url;
    }

    public void setHomeRealPlaceImg_url(String homeRealPlaceImg_url) {
        this.homeRealPlaceImg_url = homeRealPlaceImg_url;
    }

    public String getHomeRealUserName() {
        return homeRealUserName;
    }

    public void setHomeRealUserName(String homeRealUserName) {
        this.homeRealUserName = homeRealUserName;
    }

    public String getHomeRealUserImg_url() {
        return homeRealUserImg_url;
    }

    public void setHomeRealUserImg_url(String homeRealUserImg_url) {
        this.homeRealUserImg_url = homeRealUserImg_url;
    }
}
