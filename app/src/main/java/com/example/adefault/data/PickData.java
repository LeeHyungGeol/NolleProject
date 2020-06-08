package com.example.adefault.data;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class PickData {
    private Bitmap image;
    private String placeName;
    private int rating;
    private LatLng location;

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }


    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    private float lat;
    private float lng;

    public PickData(Bitmap image, String placeName, int rating, LatLng location){
        this.image = image;
        this.placeName = placeName;
        this.rating = rating;
        this.location = location;

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

}