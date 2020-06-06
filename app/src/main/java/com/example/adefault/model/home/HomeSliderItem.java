package com.example.adefault.model.home;

import android.graphics.Bitmap;

public class HomeSliderItem {

    private String description;
    private String imageUrl;
    private Bitmap bitmap;

    public HomeSliderItem() {
    }

    public HomeSliderItem(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public HomeSliderItem(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
