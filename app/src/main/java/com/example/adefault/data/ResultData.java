package com.example.adefault.data;

import android.graphics.Bitmap;

public class ResultData {
    private Bitmap image;
    private String placeName;
    private int rating;
    private String rcm_person;

    public ResultData(Bitmap image, String placeName, int rating, String rcm_person){
        this.image = image;
        this.placeName = placeName;
        this.rating = rating;
        this.rcm_person = rcm_person;
    }

    public Bitmap getImage()
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