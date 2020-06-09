package com.example.adefault.data;

import android.graphics.Bitmap;

public class ResultData {
    private String image;
    private String placeName;
    private int rating;
    private String rcm_person;

    public int getPosting_id() {
        return posting_id;
    }

    public void setPosting_id(int posting_id) {
        this.posting_id = posting_id;
    }

    private int posting_id;

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    private String place_id;

    public ResultData(String image, String placeName, int rating, String rcm_personm,String place_id){
        this.image = image;
        this.placeName = placeName;
        this.rating = rating;
        this.rcm_person = rcm_person;
        this.posting_id = posting_id;
        this.place_id = place_id;
    }

    public String getImage()
    {
        return this.image;
    }

    public String getPlaceName()
    {
        return this.placeName;
    }
    public int getRating()
    {
        return this.rating;
    }

    public String getRcm_person()
    {
        return this.rcm_person;
    }
}