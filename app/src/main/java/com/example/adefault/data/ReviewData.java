package com.example.adefault.data;
public class ReviewData {
    private String image;
    private String placeName;
    private int rating;
    private String reviewText;

    public int getPosting_id() {
        return posting_id;
    }

    public void setPosting_id(int posting_id) {
        this.posting_id = posting_id;
    }

    private int posting_id;

    public ReviewData(String image, String placeName,int rating, String reviewText,int posting_id){
        this.image = image;
        this.placeName = placeName;
        this.rating = rating;
        this.reviewText = reviewText;
        this.posting_id = posting_id;
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