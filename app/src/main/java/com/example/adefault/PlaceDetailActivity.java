package com.example.adefault;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adefault.manager.AppManager;
import com.example.adefault.model.PlaceDetailDTO;
import com.example.adefault.model.PlaceDetailResponseDTO;
import com.example.adefault.model.PlacePickResponseDTO;
import com.example.adefault.util.RestApi;
import com.example.adefault.util.RestApiUtil;
import com.example.adefault.util.UserToken;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceDetailActivity extends AppCompatActivity {
    private RestApiUtil mRestApiUtil;
    private PlacesClient placesClient;
    private LayoutInflater inflater;
    private LinearLayout placeGallery;
    private LinearLayout reviewGallery;
    private String placeId;
    private TextView placeTextView;
    private TextView placeAddr;
    private Spinner openHour;
    private RatingBar ratingBar;
    private TextView phoneNumberTextView;
    private TextView webSiteTextView;
    private TextView ratingTextView;
    private String placeName;
    private LatLng placeLatLng;
    private final String TAG = "PlaceDetailActivity";
    private List<String> tags;
    private ImageView pickBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserToken.setToken("fb51dd8d1a49a13b47bd50b8bc251ddf68d92978ff2466bd212699b59cf54d1a");
        setActionBar();
        setContentView(R.layout.activity_place_detail);
        init();
        setTopPlaceImage();
        setPlaceDetail();
        if (savedInstanceState == null) {
            setLatLng();
        }
        setUserReview();
        setPickBtn();
        addListener();
    }


    private void setActionBar() {
        CustomActionBar ca = new CustomActionBar(this, getSupportActionBar());
        ca.setActionBar();
    }

    public void init(){
        Places.initialize(this,getString(R.string.places_api_key)); //api key 셋팅
        placesClient = Places.createClient(this);
        placeGallery = findViewById(R.id.place_gallery);
        inflater=LayoutInflater.from(this); //동적 이미지 스크롤을 위한 inflater
        reviewGallery = findViewById(R.id.review_gallery);
        openHour = findViewById(R.id.placeOpenHour);
        phoneNumberTextView = findViewById(R.id.placePhoneNumber);
        webSiteTextView = findViewById(R.id.placeSite);
        placeTextView = findViewById(R.id.placeNameTextView);
        placeAddr = findViewById(R.id.placeAddrTextView);
        mRestApiUtil = new RestApiUtil();
        ratingTextView = findViewById(R.id.ratingText);
        ratingBar = findViewById(R.id.ratingBar);
        tags = new ArrayList<>();
        pickBtn= findViewById(R.id.pickButton);
        // Define a Place ID.
        placeId = "ChIJoXhD_eikfDURHVmg2okUC_w";
    }

    private void addListener() {
        pickBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlaceDetailDTO placeDetailDTO = new PlaceDetailDTO();
                placeDetailDTO.setPlace_id(placeId);
                mRestApiUtil.getApi().place_pick("Token "+ UserToken.getToken(),placeDetailDTO).enqueue(new Callback<PlacePickResponseDTO>() {
                    @Override
                    public void onResponse(Call<PlacePickResponseDTO> call, Response<PlacePickResponseDTO> response) {
                        if(response.isSuccessful()){
                            PlacePickResponseDTO placePickResponseDTO = response.body();
                            if(placePickResponseDTO.getValid()){
                                pickBtn.setImageResource(R.drawable.heart_fill);
                                Toast.makeText(PlaceDetailActivity.this,"장소를 PICK 했습니다",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                pickBtn.setImageResource(R.drawable.heart);
                                Toast.makeText(PlaceDetailActivity.this,"장소 PICK을 취소합니다",Toast.LENGTH_SHORT).show();
                            }
                            Log.d("픽버튼","통신성공");

                        }else{
                            Log.d(TAG,"pickbtn response 실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<PlacePickResponseDTO> call, Throwable t) {
                        Log.d(TAG,"통신 실패");
                    }
                });
            }

        });
    }


    public void setTopPlaceImage(){
// Specify fields. Requests for photos must always have the PHOTO_METADATAS field.
        List<Place.Field> fields = Arrays.asList(Place.Field.PHOTO_METADATAS);

// Get a Place object (this example uses fetchPlace(), but you can also use findCurrentPlace())
        FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(placeId, fields);

        placesClient.fetchPlace(placeRequest).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            // Get the photo metadata.
            int placePhotoSize = place.getPhotoMetadatas().size();


            if(placePhotoSize>5){ //place api에 있는 장소 사진 5개만 불러오기 위함
                for(int i=0;i<5;i++){
                    View view = inflater.inflate(R.layout.place_picture_item,placeGallery,false);
                    ImageView itemView = view.findViewById(R.id.placeItemView);
                    PhotoMetadata photoMetadata = place.getPhotoMetadatas().get(i);


                    FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                            .build();
                    placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                        Bitmap bitmap = fetchPhotoResponse.getBitmap();
                        itemView.setImageBitmap(bitmap);
                        placeGallery.addView(view);
                    }).addOnFailureListener((exception) -> {
                        if (exception instanceof ApiException) {
                            ApiException apiException = (ApiException) exception;
                            int statusCode = apiException.getStatusCode();
                            // Handle error with given status code.
                            Log.e(TAG, "Place not found: " + exception.getMessage());
                        }
                    });
                }

            }else{
                for(int i=0;i<placePhotoSize;i++){
                    View view = inflater.inflate(R.layout.place_picture_item,placeGallery,false);
                    ImageView itemView = view.findViewById(R.id.placeItemView);
                    PhotoMetadata photoMetadata = place.getPhotoMetadatas().get(i);


                    FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                            .build();
                    placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                        Bitmap bitmap = fetchPhotoResponse.getBitmap();
                        itemView.setImageBitmap(bitmap);
                        placeGallery.addView(view);
                    }).addOnFailureListener((exception) -> {
                        if (exception instanceof ApiException) {
                            ApiException apiException = (ApiException) exception;
                            int statusCode = apiException.getStatusCode();
                            // Handle error with given status code.
                            Log.e(TAG, "Place not found: " + exception.getMessage());
                        }
                    });

                }

            }

        });

    }

    public void setPlaceDetail(){
        setPlaceName();
        setPlaceAddr();
        setPlaceHour();
        setPhoneNumber();
        setWebsite();
    }


    public void setPlaceName(){
// Specify the fields to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

// Construct a request object, passing the place ID and fields array.
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            placeTextView.setText(place.getName());
            placeName=place.getName();
            Log.d("가게 이름",placeName);
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                int statusCode = apiException.getStatusCode();
                // Handle error with given status code.
                Log.e(TAG, "Place not found: " + exception.getMessage());
            }
        });
    }

    public void setPlaceAddr(){
        // Specify the fields to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.ADDRESS);

// Construct a request object, passing the place ID and fields array.
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            placeAddr.setText(place.getAddress());
            Log.i(TAG, "Place found: " + place.getAddress());
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                int statusCode = apiException.getStatusCode();
                // Handle error with given status code.
                Log.e(TAG, "Place not found: " + exception.getMessage());
            }
        });

    }

    public void setPlaceHour(){
        // Specify the fields to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.OPENING_HOURS);

// Construct a request object, passing the place ID and fields array.
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_layout,place.getOpeningHours().getWeekdayText());
            openHour.setAdapter(arrayAdapter);

            for(int i=0;i<place.getOpeningHours().getWeekdayText().size();i++){
                Log.i(TAG, "Place found: " + place.getOpeningHours().getWeekdayText().get(i));
            }
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                int statusCode = apiException.getStatusCode();
                // Handle error with given status code.
                Log.e(TAG, "Place not found: " + exception.getMessage());
            }
        });
    }

    public void setPhoneNumber(){
        // Specify the fields to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.PHONE_NUMBER);

// Construct a request object, passing the place ID and fields array.
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            phoneNumberTextView.setText("전화번호: "+place.getPhoneNumber());
            Log.d("전화번호",place.getPhoneNumber());

        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                int statusCode = apiException.getStatusCode();
                // Handle error with given status code.
                Log.e(TAG, "Place not found: " + exception.getMessage());
            }
        });

    }

    public void setWebsite(){
        // Specify the fields to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.WEBSITE_URI);

// Construct a request object, passing the place ID and fields array.
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();

            try{
                webSiteTextView.setText("사이트주소: "+place.getWebsiteUri().toString());
            }catch (Exception e){
                e.printStackTrace();
            }

        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                int statusCode = apiException.getStatusCode();
                // Handle error with given status code.
                Log.e(TAG, "Place not found: " + exception.getMessage());
            }
        });


    }

    public void setLatLng() {
        // Specify the fields to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG);

// Construct a request object, passing the place ID and fields array.
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            placeLatLng = place.getLatLng();
            Log.d("placename",placeName);
            MapPlaceFragment mainFragment = new MapPlaceFragment(placeLatLng,placeName);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mapFragment, mainFragment, "main")
                    .commit();
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                int statusCode = apiException.getStatusCode();
                // Handle error with given status code.
                Log.e(TAG, "Place not found: " + exception.getMessage());
            }
        });
    }

    public void setUserReview() {

        PlaceDetailDTO placeDetailDTO = new PlaceDetailDTO();
        placeDetailDTO.setPlace_id(placeId);
        mRestApiUtil.getApi().place_detail("Token "+UserToken.getToken(),placeDetailDTO).enqueue(new Callback<PlaceDetailResponseDTO>() {
            @Override
            public void onResponse(Call<PlaceDetailResponseDTO> call, Response<PlaceDetailResponseDTO> response) {
                if(response.isSuccessful()){
                    Log.d(TAG,"통신 성공");
                    PlaceDetailResponseDTO placeDetailResponseDTO = response.body();
                    for(int i=0 ;i<placeDetailResponseDTO.getPlace_data().size();i++){
                        Log.d("123",placeDetailResponseDTO.getPlace_data().get(i).getContext());
                        View view = inflater.inflate(R.layout.user_place_review_item,reviewGallery,false);
                        ImageView itemView = view.findViewById(R.id.reviewPictureImageView);
                        Glide.with(PlaceDetailActivity.this)
                                .load(UserToken.getUrl()+placeDetailResponseDTO.getPlace_data().get(i).getImg_url_1())
                                .into(itemView);
                        TextView nickName = view.findViewById(R.id.reviewIdTextView);
                        TextView content = view.findViewById(R.id.reviewContentTextView);
                        nickName.setText(placeDetailResponseDTO.getPlace_data().get(i).getNickname());
                        content.setText(placeDetailResponseDTO.getPlace_data().get(i).getContext());
                        ratingTextView.setText(Double.toString(placeDetailResponseDTO.getRating()));
                        ratingBar.setRating((float) placeDetailResponseDTO.getRating());
                        reviewGallery.addView(view);
                    }

                    for(int i=placeDetailResponseDTO.getPlace_data().size()-1;i>=0;i--){
                        try{
                            if(!placeDetailResponseDTO.getPlace_data().get(i).getTag_1().equals("")){
                                tags.add(placeDetailResponseDTO.getPlace_data().get(i).getTag_1());
                            }
                            if(!placeDetailResponseDTO.getPlace_data().get(i).getTag_2().equals("")){
                                tags.add(placeDetailResponseDTO.getPlace_data().get(i).getTag_2());
                            }
                            if(!placeDetailResponseDTO.getPlace_data().get(i).getTag_3().equals("")){
                                tags.add(placeDetailResponseDTO.getPlace_data().get(i).getTag_3());
                            }
                            if(!placeDetailResponseDTO.getPlace_data().get(i).getTag_4().equals("")){
                                tags.add(placeDetailResponseDTO.getPlace_data().get(i).getTag_4());
                            }
                            if(!placeDetailResponseDTO.getPlace_data().get(i).getTag_5().equals("")){
                                tags.add(placeDetailResponseDTO.getPlace_data().get(i).getTag_5());
                            }
                            if(!placeDetailResponseDTO.getPlace_data().get(i).getTag_6().equals("")){
                                tags.add(placeDetailResponseDTO.getPlace_data().get(i).getTag_6());
                            }
                        }catch (Exception e){

                        }

                    }
                    TagContainerLayout mTagContainerLayout = findViewById(R.id.tagcontainerLayout);
                    mTagContainerLayout.setTags(tags);
                }
                else{
                    Log.d(TAG,"response 실패");
                }

            }

            @Override
            public void onFailure(Call<PlaceDetailResponseDTO> call, Throwable t) {
                Log.d(TAG,"통신 실패");
            }
        });
    }

    public void setPickBtn(){
        PlaceDetailDTO placeDetailDTO = new PlaceDetailDTO();
        placeDetailDTO.setPlace_id(placeId);
        mRestApiUtil.getApi().place_pick("Token "+UserToken.getToken(),placeDetailDTO).enqueue(new Callback<PlacePickResponseDTO>() {
            @Override
            public void onResponse(Call<PlacePickResponseDTO> call, Response<PlacePickResponseDTO> response) {
                if(response.isSuccessful()){
                    PlacePickResponseDTO placePickResponseDTO = response.body();
                    if(placePickResponseDTO.getValid()){
                        pickBtn.setImageResource(R.drawable.heart_fill);
                    }
                    else{
                        pickBtn.setImageResource(R.drawable.heart);
                    }
                    Log.d("픽버튼","통신성공");

                }else{
                    Log.d(TAG,"pickbtn response 실패");
                }
            }

            @Override
            public void onFailure(Call<PlacePickResponseDTO> call, Throwable t) {
                Log.d(TAG,"통신 실패");
            }
        });

    }

}
