package com.example.adefault.data;
public class ReviewData {
    private String image;
    private String placeName;
    private int rating;
    private String reviewText;

    public ReviewData(String image, String placeName,int rating, String reviewText){
        this.image = image;
        this.placeName = placeName;
        this.rating = rating;
        this.reviewText = reviewText;
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

    public String getReviewText()
    {
        return this.reviewText;
    }
}