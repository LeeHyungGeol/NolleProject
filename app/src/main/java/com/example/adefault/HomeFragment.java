package com.example.adefault;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adefault.adapter.home.HomeHotReviewAdapter;
import com.example.adefault.adapter.home.HomeRealTimeAdapter;
import com.example.adefault.adapter.home.HomeSliderAdapter;
import com.example.adefault.data.UserHomeRepository;
import com.example.adefault.manager.AppManager;
import com.example.adefault.manager.ImageManager;
import com.example.adefault.model.home.CategoryM;
import com.example.adefault.model.home.CategoryS;
import com.example.adefault.model.home.HomeHotReviewItem;
import com.example.adefault.model.home.HomeRealTimeItem;
import com.example.adefault.model.home.HomeRecommendation;
import com.example.adefault.model.home.HomeResponseDTO;
import com.example.adefault.model.home.HomeSliderItem;
import com.example.adefault.model.home.HotReview;
import com.example.adefault.model.home.RealTime;
import com.example.adefault.model.home.RecommendPlace;
import com.example.adefault.util.PlaceAPI;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.volley.VolleyLog.TAG;


public class HomeFragment extends Fragment implements HomeHotReviewAdapter.HomeMyPickClickListener, HomeRealTimeAdapter.HomeRealTimeClickListener {

    private PlaceAPI mPlaceApi;
    private PlacesClient mPlacesClient;

    private HomeResponseDTO mHomeResponseDTO;
    private UserHomeRepository mUserHomeRepository;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConfirmDialog mConfirmDialog;

    private TagContainerLayout mTagContainerLayout;
    private List<String> mTags;
    private TextView tv_anotherUserName;
    private ImageView iv_anotherUserImage;
    private SliderView mSliderView;
    private HomeSliderAdapter adapter;
    private RecyclerView mHotReviewRecyclerView;
    private RecyclerView mRealTimeRecyclerView;
    private ArrayList<RealTime> realTime;
    private ArrayList<HotReview> hotReview;

    private Intent activityIntent;

    private final static int ITEM = 1;
    private final static int USER = 2;
    private final static int PLACE = 3;
    private final static int HOT = 4;
    private final static int REAL = 5;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        System.out.println("onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override  // onCreateView <-> onSaveInstanceState
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        initView(view);
        initListener();
        if(AppManager.getInstance().getmHomeResponseDTO() != null) {
            //HomeResponseDTO home = (HomeResponseDTO) savedInstanceState.getSerializable("homeResponseDTO");
            HomeResponseDTO home = AppManager.getInstance().getmHomeResponseDTO();
            setHomeRecommendation(home);
            setHomeTags(home);
            setHotReview(home);
            setRealTimeReview(home);
            return view;
        }
        getHomeData();
        return view;
    }

    public void initView(View view) {
        mConfirmDialog = new ConfirmDialog(AppManager.getInstance().getContext());

        tv_anotherUserName = view.findViewById(R.id.tv_homeFragment_anotherUserName);
        iv_anotherUserImage = view.findViewById(R.id.iv_homeFragment_anotherUserImage);

        mTagContainerLayout = (TagContainerLayout) view.findViewById(R.id.tagContainerLayout_homeFragment);
        mTags = new ArrayList<>();

        mSliderView = view.findViewById(R.id.imageSlider_homeFragment);
        adapter = new HomeSliderAdapter(AppManager.getInstance().getContext());
        mSliderView.setSliderAdapter(adapter);

        mSliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        mSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        mSliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        mSliderView.setScrollTimeInSec(6000);
        mSliderView.setAutoCycle(true);
        mSliderView.startAutoCycle();

        Places.initialize(AppManager.getInstance().getContext(), "AIzaSyBLf4XF6AC1wJONoWVE6rtwcpQZLl8pXnU"); //place API //나중에 발급 받기
        mPlacesClient = Places.createClient(AppManager.getInstance().getContext());
        mPlaceApi = new PlaceAPI(mPlacesClient);

//        RecyclerView.LayoutManager myPickLayoutManager = new LinearLayoutManager(AppManager.getInstance().getContext());
        mHotReviewRecyclerView = view.findViewById(R.id.homeFragment_recyclerview_hot_review);
        LinearLayoutManager hotReviewLayoutManager = new LinearLayoutManager(AppManager.getInstance().getContext());
        hotReviewLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHotReviewRecyclerView.setLayoutManager(hotReviewLayoutManager);

        mRealTimeRecyclerView = view.findViewById(R.id.homeFragment_recyclerview_realTimeReview);
        LinearLayoutManager realTimeLayoutManager = new LinearLayoutManager(AppManager.getInstance().getContext());
        realTimeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRealTimeRecyclerView.setLayoutManager(realTimeLayoutManager);
    }

    public void initListener() {

        mSliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                mSliderView.setCurrentPagePosition(position);
            }
        });

        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {   // Tag 가 클릭되었을때 실행

            }

            @Override public void onTagLongClick(int position, String text) {

            } @Override public void onSelectedTagDrag(int position, String text) {

            } @Override public void onTagCrossClick(int position) {

            }

        });

    }

    @Override // onCreateView <-> onSaveInstanceState
    public void onSaveInstanceState(@NonNull Bundle outState) {
       // outState.putSerializable("homeResponseDTO", mHomeResponseDTO);
        System.out.println("onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }


    public void setGooglePlaceImage(String placeId) { //google place id를 통해 이미지를 가져옴
        mPlaceApi.setBitmapByPlaceId(adapter, placeId);
    }

    public void setHomeRecommendation(HomeResponseDTO homeResponseDTO) {
        ArrayList<HomeRecommendation> homeRecommendation = homeResponseDTO.getHome_recommendation();
        int random = (int)(Math.random()*homeRecommendation.size());  //homeRecommendation 에 있는 사람 중 한명 랜덤으로 뽑기
        // homeRecommendation size 는 최대 5에서 최소 2로 어떻게 올지 모르기 때문에 random 으로 하나의 숫자를 뽑아야 함
        ArrayList<RecommendPlace> recommendPlace = homeRecommendation.get(random).getRecommend_place();
        tv_anotherUserName.setText(homeRecommendation.get(random).getNickname()); // ~~~님이 추천한 장소, ~~~ 님의 프로필 이미지
        ImageManager.getInstance().GlideWithContext(AppManager.getInstance().getContext(), iv_anotherUserImage, ImageManager.getInstance().getFullImageString(homeRecommendation.get(random).getImage()));

        for(int i = 0; i < recommendPlace.size(); i++) {
            setGooglePlaceImage(recommendPlace.get(i).getPlace_id());
        }

    }

    public void setHomeTags(HomeResponseDTO homeResponseDTO) {
        ArrayList<CategoryM> categoryM = homeResponseDTO.getCategory_m();
        ArrayList<CategoryS> categoryS = homeResponseDTO.getCategory_s();
        for(int i = 0; i < categoryM.size(); i++) {
            mTags.add("#" + categoryM.get(i).getCtgr_name());
        }
        for(int i = 0; i < categoryS.size(); i++) {
            mTags.add("#" + categoryS.get(i).getCtgr_name());
        }
        mTagContainerLayout.setTags(mTags);
    }

    public void setHotReview(HomeResponseDTO homeResponseDTO) {
        List<HomeHotReviewItem> dataList = new ArrayList<>();
        hotReview = homeResponseDTO.getHot();
        for(int i = 0; i < hotReview.size(); i++) {
            dataList.add(new HomeHotReviewItem(hotReview.get(i).getNickname(), ImageManager.getInstance().getFullImageString(hotReview.get(i).getImage()), hotReview.get(i).getPlace_name()
                    , hotReview.get(i).getRating(), ImageManager.getInstance().getFullImageString(hotReview.get(i).getImg_url_1())));
        }
        HomeHotReviewAdapter homeHotReviewAdapter = new HomeHotReviewAdapter(dataList);
        mHotReviewRecyclerView.setAdapter(homeHotReviewAdapter);

        homeHotReviewAdapter.setOnClickListener(this);

    }

    public void setRealTimeReview(HomeResponseDTO homeResponseDTO) {
        List<HomeRealTimeItem> dataList = new ArrayList<>();
        realTime = homeResponseDTO.getReal_time();

        for(int i = 0; i < realTime.size(); i++) {  //name, review, rating, url
            dataList.add(new HomeRealTimeItem(realTime.get(i).getPlace_name(), realTime.get(i).getContext(), realTime.get(i).getRating()
                    , ImageManager.getInstance().getFullImageString(realTime.get(i).getImg_url_1()), realTime.get(i).getNickname(), ImageManager.getInstance().getFullImageString(realTime.get(i).getImage())));
        }
        HomeRealTimeAdapter homeRealTimeAdapter = new HomeRealTimeAdapter(dataList);
        mRealTimeRecyclerView.setAdapter(homeRealTimeAdapter);

        homeRealTimeAdapter.setOnClickListener(this);

    }


    public void getHomeData() {
        progressON("로딩 중입니다...");
        AppManager.getInstance().createHomeResponse(); // singleTonPattern
        String token = "Token " + AppManager.getInstance().getUser().getToken();
        System.out.println("getHomeData");
        System.out.println("token : " + token);
        mUserHomeRepository = new UserHomeRepository(token);

        if(mUserHomeRepository.isAvailable()) {

            mUserHomeRepository.getHomeFragmentData(new Callback<HomeResponseDTO>() {
                @Override
                public void onResponse(Call<HomeResponseDTO> call, Response<HomeResponseDTO> response) {
                    System.out.println("response.isSuccessful : " + response.isSuccessful());
                    if(response.isSuccessful()) {
                        mHomeResponseDTO = response.body();
                        AppManager.getInstance().setmHomeResponseDTO(mHomeResponseDTO);
                        setHomeRecommendation(mHomeResponseDTO);
                        setHomeTags(mHomeResponseDTO);
                        setHotReview(mHomeResponseDTO);
                        setRealTimeReview(mHomeResponseDTO);
                        progressOFF();
                    }
                    else {
                        progressOFF();
                        mConfirmDialog.setMessage("onResponse : 서버와 통신이 원활하지 않습니다.");
                        mConfirmDialog.show();
                    }
                }

                @Override
                public void onFailure(Call<HomeResponseDTO> call, Throwable t) {
                    System.out.println(t.getMessage());
                    progressOFF();
                    mConfirmDialog.setMessage("onFailure : 서버와 통신이 원활하지 않습니다.");
                    mConfirmDialog.show();
                }
            });
        }

    }

    public void progressON(String message) {
        ImageManager.getInstance().progressON((Activity)AppManager.getInstance().getContext(), message);
    }

    public void progressOFF() {
        ImageManager.getInstance().progressOFF();
    }

    public void checkCase(int Case, int pos, int itemCase) {

        if(itemCase == HOT) {
            switch (Case) {
                case ITEM:
                case PLACE:
                    activityIntent = new Intent(AppManager.getInstance().getContext(), FeedDetailActivity.class);
                    activityIntent.putExtra("posting_idx", hotReview.get(pos).getIdx());
                    startActivity(activityIntent);
                    break;
                case USER:
                    activityIntent = new Intent(AppManager.getInstance().getContext(), OtherUserPageActivity.class);
                    activityIntent.putExtra("user_nickname", hotReview.get(pos).getNickname());
                    startActivity(activityIntent);
                    break;
            }
            return;
        }
        else if(itemCase == REAL) {
            switch (Case) {
                case ITEM:
                case PLACE:
                    activityIntent = new Intent(AppManager.getInstance().getContext(), FeedDetailActivity.class);
                    activityIntent.putExtra("posting_idx", realTime.get(pos).getIdx());
                    startActivity(activityIntent);
                    break;
            }
            return;
        }
        return;
    }

    @Override
    public void onRealTimeItemClicked(int position) { //나중에 다른 화면으로 이동하는 부분 여기에 구현  //해당 리뷰에 대한 게시글 화면으로 이동 // 아마도 게시글 리스트 화면이 될 것
        //Toast.makeText(AppManager.getInstance().getContext(), "RealTimeItem" + position, Toast.LENGTH_SHORT).show();
       checkCase(ITEM, position, REAL);
    }

    @Override
    public void onRealTimePlaceNameClicked(int position) { //실시간 리뷰의 장소 이름을 클릭할 경우 //해당 리뷰에 대한 게시글 화면으로 이동
        //Toast.makeText(AppManager.getInstance().getContext(), "RealTimePlaceName" + position, Toast.LENGTH_SHORT).show();
        checkCase(PLACE, position, REAL);

    }

    @Override
    public void onRealTimePlaceImageClicked(int position) { //실시간 리뷰의 장소 이미지를 클릭할 경우 //해당 리뷰에 대한 게시글 화면으로 이동
        //Toast.makeText(AppManager.getInstance().getContext(), "RealTimePlaceImage" + position, Toast.LENGTH_SHORT).show();
        checkCase(PLACE, position, REAL);
    }

    @Override
    public void onHotReviewItemClicked(int position) { //핫 리뷰의 카드뷰를 클릭할 경우 //해당 리뷰에 대한 게시글 화면으로 이동
        //Toast.makeText(AppManager.getInstance().getContext(), "HotReviewItem" + position, Toast.LENGTH_SHORT).show();
        checkCase(ITEM, position, HOT);
    }

    @Override
    public void onHotReviewUserImageClicked(int position) { //핫 리뷰의 사용자 이미지를 클릭할 경우 //다른 사용자의 페이지로 이동 구현할 것
        //Toast.makeText(AppManager.getInstance().getContext(), "HotReviewUserImage" + position, Toast.LENGTH_SHORT).show();
        checkCase(USER, position, HOT);
    }

    @Override
    public void onHotReviewUserNameClicked(int position) { //핫 리뷰의 사용자 이름을 클릭할 경우 //다른 사용자의 페이지로 이동 구현할 것
        //Toast.makeText(AppManager.getInstance().getContext(), "HotReviewUserName" + position, Toast.LENGTH_SHORT).show();
        checkCase(USER, position, HOT);
    }

    @Override
    public void onHotReviewPlaceImageClicked(int position) { //핫 리뷰의 장소 이미지를 클릭할 경우 //해당 리뷰에 대한 게시글 화면으로 이동
        //Toast.makeText(AppManager.getInstance().getContext(), "HotReviewPlaceImage" + position, Toast.LENGTH_SHORT).show();
        checkCase(PLACE, position, HOT);
    }
}
