package com.example.adefault.model;

import java.util.ArrayList;

public class FollowFeedReview_data {


    //Post(Main)
    private String posting_id;
    private String nickname;
    private String image; // user_profile
    private String place_id;
    private String context;
    private String place_name;
    private String img_1, img_2, img_3, img_4, img_5; // context pic
    private String like_cnt;
    private String date;
    private String tag_1, tag_2, tag_3, tag_4, tag_5;
    private float rating;
    private String like_valid;
    private String pick_valid;

    public FollowFeedReview_data() { }

    //menu post
    public FollowFeedReview_data(
            String posting_id, String nickname, String image, String place_name, String place_id, String context,
//            String img_1, String img_2, String img_3, String img_4, String img_5, int like_cnt, //String date,
            String tag_1, String tag_2, String tag_3, String tag_4, String tag_5, float rating,
            String like_valid, String pick_valid) {

        this.posting_id = posting_id;
        this.nickname = nickname;
        this.image = image;
        this.place_name = place_name;
        this.place_id = place_id;
        this.context = context;
        this.img_1 = img_1;
        this.img_2 = img_2;
        this.img_3 = img_3;
        this.img_4 = img_4;
        this.img_5 = img_5;
//        this.like_cnt = like_cnt;
//        this.date = date;
        this.tag_1 = tag_1;
        this.tag_2 = tag_2;
        this.tag_3 = tag_3;
        this.tag_4 = tag_4;
        this.tag_5 = tag_5;
        this.rating = rating;
        this.like_valid = like_valid;
        this.pick_valid = pick_valid;
    }

    //post detail
//    public FeedPostResponseDTO(int posting_id, String nickname, String image, String date, double raiting) {
//        this.posting_id = posting_id;
//        this.nickname = nickname;
//        this.image = image;
//        this.date = date;
//        this.raiting = raiting;
//    }
//
//    // 좋아요 여부
//    public FeedPostResponseDTO(boolean valid) {
//        this.valid = valid;
//    }
//
//    public FeedPostResponseDTO(ArrayList<Follow_list> followLists){//String detailNickname, String detailContext, String detilaDate) {
//        this.followLists = followLists;
//        //this.detailNickname = detailNickname;
//        //this.detailContext = detailContext;
//        //this.detilaDate = detilaDate;
//    }


    public String getPosting_id() {
        return posting_id;
    }

    public void setPosting_id(String posting_id) {
        this.posting_id = posting_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getImg_1() {
        return img_1;
    }

    public void setImg_1(String img_1) {
        this.img_1 = img_1;
    }

    public String getImg_2() {
        return img_2;
    }

    public void setImg_2(String img_2) {
        this.img_2 = img_2;
    }

    public String getImg_3() {
        return img_3;
    }

    public void setImg_3(String img_3) {
        this.img_3 = img_3;
    }

    public String getImg_4() {
        return img_4;
    }

    public void setImg_4(String img_4) {
        this.img_4 = img_4;
    }

    public String getImg_5() {
        return img_5;
    }

    public void setImg_5(String img_5) {
        this.img_5 = img_5;
    }

    public String getLike_cnt() {
        return like_cnt;
    }

    public void setLike_cnt(String like_cnt) {
        this.like_cnt = like_cnt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTag_1() {
        return tag_1;
    }

    public void setTag_1(String tag_1) {
        this.tag_1 = tag_1;
    }

    public String getTag_2() {
        return tag_2;
    }

    public void setTag_2(String tag_2) {
        this.tag_2 = tag_2;
    }

    public String getTag_3() {
        return tag_3;
    }

    public void setTag_3(String tag_3) {
        this.tag_3 = tag_3;
    }

    public String getTag_4() {
        return tag_4;
    }

    public void setTag_4(String tag_4) {
        this.tag_4 = tag_4;
    }

    public String getTag_5() {
        return tag_5;
    }

    public void setTag_5(String tag_5) {
        this.tag_5 = tag_5;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getLike_valid() {
        return like_valid;
    }

    public void setLike_valid(String like_valid) {
        this.like_valid = like_valid;
    }

    public String getPick_valid() {
        return pick_valid;
    }

    public void setPick_valid(String pick_valid) {
        this.pick_valid = pick_valid;
    }

    @Override
    public String toString() {
        return "FeedReview_data{" +
                "posting_id=" + posting_id +
                ", nickname='" + nickname + '\'' +
                ", image='" + image + '\'' +
                ", place_id='" + place_id + '\'' +
                ", context='" + context + '\'' +
                ", img_1='" + img_1 + '\'' +
                ", img_2='" + img_2 + '\'' +
                ", img_3='" + img_3 + '\'' +
                ", img_4='" + img_4 + '\'' +
                ", img_5='" + img_5 + '\'' +
                ", like_cnt=" + like_cnt +
                ", date='" + date + '\'' +
                ", tag_1='" + tag_1 + '\'' +
                ", tag_2='" + tag_2 + '\'' +
                ", tag_3='" + tag_3 + '\'' +
                ", tag_4='" + tag_4 + '\'' +
                ", tag_5='" + tag_5 + '\'' +
                ", rating=" + rating +
                '}';
    }
}

