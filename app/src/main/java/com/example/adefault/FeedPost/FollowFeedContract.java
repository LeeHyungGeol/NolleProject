package com.example.adefault.FeedPost;

import com.example.adefault.model.FollowFeedResponseDTO;

public interface FollowFeedContract { // View와 Presenter를 각각 정의하여 이해를 돕기 위해 사용
    public interface View { // 화면 갱신, 기타 등등
        void showPostData(FollowFeedResponseDTO followFeedResponseDTO);//, View view); // 포스팅프로필(사진, 이름)/날짜/사진/내용/가게이름/하트/댓글
        void showLike(boolean valid);
    }

    // 유저 액션에 대한 리스너
    // 이 프래그먼트에서 유저가 하는 것은 무엇인가?
    public interface UserActionListener {
        void clickFollowList(); // 팔로우 클릭하면 해당 사용자 정보?로 넘어감
        void clickPostTitle(); // 포스트 타이틀 누를 시 장소 디테일 화면으로 넘어감
        void clickReplyButton(); // 댓글 달 수 있는 상세?페이지로 넘어감
        void clickLikeButton(); // 하트 수 증가
    }

    public interface Presenter {
        FollowFeedResponseDTO callPostData(); // 피드 화면에 띄울 포스터들
//        void getLike(int posting_id); //그럼 그 전에 포스팅 아이디를 보내야 합니
//        boolean callLike(); // like 햇나 안햇나

    }

}
