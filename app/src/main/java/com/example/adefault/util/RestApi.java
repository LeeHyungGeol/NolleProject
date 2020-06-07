package com.example.adefault.util;

import com.example.adefault.model.BoardResponseDTO;
import com.example.adefault.model.DeleteUserResponseDTO;
import com.example.adefault.model.LoginDTO;
import com.example.adefault.model.LoginResponseDTO;
import com.example.adefault.model.LogoutResponseDTO;
import com.example.adefault.model.MyFollowResponseDTO;
import com.example.adefault.model.MyPageEditResponseDTO;
import com.example.adefault.model.MyPageResponseDTO;
import com.example.adefault.model.MyPostingResponseDTO;
import com.example.adefault.model.PlaceDetailDTO;
import com.example.adefault.model.PlaceDetailResponseDTO;
import com.example.adefault.model.PlacePickResponseDTO;
import com.example.adefault.model.RegisterResponseDTO;
import com.example.adefault.model.User;
import com.example.adefault.model.home.HomeResponseDTO;
import com.example.adefault.model.UserFollowResponseDTO;
import com.example.adefault.model.UserPageResponseDTO;


import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface RestApi {

    String BASE_URL = "http://8f47d9d27903.ngrok.io/";

    //String BASE_URL = "http://172.30.1.42:8000/";

    @POST("loginApp/auth/register/")
    Call<RegisterResponseDTO> register(@Body User user);

    @POST("loginApp/auth/login/")
    Call<LoginResponseDTO> login(@Body LoginDTO loginDTO);

    @Multipart
    @POST("posting/1/")
    Call<BoardResponseDTO> uploadBoard(@Header("Authorization") String token, @PartMap Map<String, RequestBody> boardMaps);

    @GET("homeApp/home/")
    Call<HomeResponseDTO> home(@Header("Authorization") String token);


    @POST("place_detail/1/")
    Call<PlaceDetailResponseDTO> place_detail(@Header("Authorization") String token, @Body PlaceDetailDTO placeDetailDTO);

    @POST("place_detail/2/")
    Call<PlacePickResponseDTO> place_pick(@Header("Authorization") String token, @Body PlaceDetailDTO placeDetailDTO);

    @GET("mypageApp/mypage/")
    Call<MyPageResponseDTO> mypage(@Header("Authorization") String token);

    @Multipart
    @PUT("mypageApp/mypage/")
    Call<MyPageEditResponseDTO> mypage_edit(@Header("Authorization") String token, @PartMap Map<String, RequestBody> editMap);

    @POST("loginApp/auth/logout/")
    Call<LogoutResponseDTO> logout(@Header("Authorization") String token);

    @GET("mypageApp/myfollow/")
    Call<MyFollowResponseDTO> myfollow(@Header("Authorization") String token);

    @DELETE("mypageApp/mypage/")
    Call<DeleteUserResponseDTO> delete_user(@Header("Authorization") String token);

    @GET("mypageApp/userpage/{user_id}/")
    Call<UserPageResponseDTO> user_page(@Header("Authorization") String token, @Path("user_id")String id);

    @GET("mypageApp/userfollow/{user_nickname}") //다른 사용자 팔로우 팔로워 리스트 출력
    Call<UserFollowResponseDTO> user_follow(@Header("Authorization") String token, @Path("user_nickname")String nickname);

    @GET("mypageApp/myposting/") //자신의 게시글 불러오기
    Call<MyPostingResponseDTO> my_posting(@Header("Authorization") String token);

}
