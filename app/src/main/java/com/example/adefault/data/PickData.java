package com.example.adefault.data;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class PickData {
    private String image;
    private String placeName;
    private int rating;
    private LatLng location;

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    private String place_id;

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

    public PickData(String image, String placeName, int rating, LatLng location,String place_id){
        this.image = image;
        this.placeName = placeName;
        this.rating = rating;
        this.location = location;
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

}