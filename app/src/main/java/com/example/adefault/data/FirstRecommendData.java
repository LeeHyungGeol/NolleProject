package com.example.adefault.data;

import android.graphics.Bitmap;

public class FirstRecommendData {
    private Bitmap image;

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    private String place_id;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public FirstRecommendData(Bitmap image , String place_id){
        this.image= image;
        this.place_id  = place_id;
    }
}
