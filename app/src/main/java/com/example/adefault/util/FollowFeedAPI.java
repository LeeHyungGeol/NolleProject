package com.example.adefault.util;

import com.example.adefault.model.FollowFeedResponseDTO;
import com.example.adefault.model.LikeDTO;
import com.example.adefault.model.LikeResponseDTO;
import com.example.adefault.model.ReplyDTO;
import com.example.adefault.model.ReplyResponseDTO;
import com.example.adefault.model.WriteReplyDTO;
import com.example.adefault.model.WriteReplyResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FollowFeedAPI {
    static String BASE_URL = "http://bce63ab4332f.ngrok.io/";

    //@Header("Authorization") String token

    // 포스트 정보 다 가져오기(팔로워, 게시글, 타이틀 ...)
    @POST("follow_feed/1/")
    Call<FollowFeedResponseDTO> getPostData(@Header("Authorization") String token);

    //좋아요 버튼 response
    @POST("follow_feed/2/")
    Call<LikeResponseDTO> getLike(@Body LikeDTO likeDTO,@Header("Authorization") String token);


    //response
    @POST("follow_feed/3/")
    Call<ReplyResponseDTO> getReplyData(@Body ReplyDTO replyDTO,@Header("Authorization") String token);


    // 댓글 달기
    @POST("commentApp/comment/{index}/")
    Call<WriteReplyResponseDTO> postReply (@Body WriteReplyDTO writeReplyDTO, @Path("index") String index,@Header("Authorization") String token);



}
