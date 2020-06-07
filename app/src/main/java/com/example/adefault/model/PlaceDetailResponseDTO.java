package com.example.adefault.model;

import java.util.ArrayList;

public class PlaceDetailResponseDTO {
    private ArrayList<PlaceData> place_data;
    private double rating;

    public ArrayList<PlaceData> getPlace_data() {
        return place_data;
    }

    public void setPlace_data(ArrayList<PlaceData> place_data) {
        this.place_data = place_data;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
