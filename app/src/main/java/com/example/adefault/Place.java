package com.example.adefault;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;

import org.json.JSONArray;

class Place {
    private String reference;
    private String name;
    private String icon;
    private String formatted_address;
    private String formatted_phone_number;
    private String id;
    private JSONArray photos;
    private JSONArray PHOTO_METADATAS;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLocation(LatLng latLng) {
        this.latLng = latLng;
    }

    private LatLng latLng;
    public JSONArray getGeometry() {
        return geometry;
    }

    public void setGeometry(JSONArray geometry) {
        this.geometry = geometry;
    }

    private JSONArray geometry;

    public JSONArray getPHOTO_METADATAS() {
        return PHOTO_METADATAS;
    }

    public void setPHOTO_METADATAS(JSONArray PHOTO_METADATAS) {
        this.PHOTO_METADATAS = PHOTO_METADATAS;
    }

    public JSONArray getPhotos() {
        return photos;
    }

    public void setPhotos(JSONArray photos) {
        this.photos = photos;
    }



    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    private String reviews;


    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    private double rating;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getFormatted_phone_number() {
        return formatted_phone_number;
    }

    public void setFormatted_phone_number(String formatted_phone_number) {
        this.formatted_phone_number = formatted_phone_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
