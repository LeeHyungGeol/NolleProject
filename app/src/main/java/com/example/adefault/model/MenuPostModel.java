//package com.example.adefault.model;
//
//import android.view.Menu;
//import android.view.View;
//
//import com.example.adefault.MenuPost.MenuPostContract;
//
//public class MenuPostModel {
//
//    ////////////////////////////
//    MenuPostData menuPostData = new MenuPostData();
//    ////////////////////////////
//
//
//    void showFollowList(MenuPostData menuPostData) {
//        this.menuPostData = menuPostData;
//    }
//
////    // userpic, userName, date
////    void showPostUserData() {
////
////    }
////
//    // contentPic, content// 여기다 다 넣어버릴까.....
//    void showPost() {
//
//    }
////
////    //title
////    void showTitle() {
////
////    }
////
////   // score
////    void showScore() {
////
////    }
////
////    //하트 버튼... 이건 여기서 안 해도 되나?
////    int showHeart() { return 0; }
//
//    //댓글 버튼 얘도.... 흠
//    boolean showReply(MenuPostContract.UserActionListener on) {
//        if(on != null) return true;
//        return false;
//    } // 댓글 클릭
//
//    public interface UserActionListener {
//        void clickFollowList(); // 팔로우 클릭하면 해당 사용자 정보?로 넘어감
//        void clickPostTitle(); // 포스트 타이틀 누를 시 장소 디테일 화면으로 넘어감
//        void clickReplyButton(); // 댓글 달 수 있는 상세?페이지로 넘어감
//        void clickHeartButton(); // 하트 수 증가
//    }
//
//}
