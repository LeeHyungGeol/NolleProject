package com.example.adefault.data;

import android.graphics.Bitmap;

public class FirstRecommendData {
    private Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public FirstRecommendData(Bitmap image){
        this.image= image;
    }
}
