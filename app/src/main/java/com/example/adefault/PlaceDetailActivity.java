package com.example.adefault;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

public class PlaceDetailActivity extends AppCompatActivity {
    private PlacesClient placesClient;
    private LayoutInflater inflater;
    private LinearLayout gallery;
    private String placeId;
    private TextView placeName;
    private TextView placeAddr;
    private Spinner openHour;
    private TextView phoneNumberTextView;
    private TextView webSiteTextView;
    private final String TAG = "PlaceDetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar();
        setContentView(R.layout.activity_place_detail);
        init();
        setTopPlaceImage();
        setPlaceDetail();
    }

    private void setActionBar() {
        CustomActionBar ca = new CustomActionBar(this, getSupportActionBar());
        ca.setActionBar();
    }

    public void init(){
        Places.initialize(this,getString(R.string.places_api_key)); //api key 셋팅
        placesClient = Places.createClient(this);
        gallery = findViewById(R.id.place_gallery);
        inflater=LayoutInflater.from(this); //동적 이미지 스크롤을 위한 inflater
        openHour = findViewById(R.id.placeOpenHour);
        phoneNumberTextView = findViewById(R.id.placePhoneNumber);
        webSiteTextView = findViewById(R.id.placeSite);
        placeName = findViewById(R.id.placeNameTextView);
        placeAddr = findViewById(R.id.placeAddrTextView);
        // Define a Place ID.
        placeId = "ChIJoXhD_eikfDURHVmg2okUC_w";

    }

    public void setTopPlaceImage(){
// Specify fields. Requests for photos must always have the PHOTO_METADATAS field.
        List<Place.Field> fields = Arrays.asList(Place.Field.PHOTO_METADATAS);

// Get a Place object (this example uses fetchPlace(), but you can also use findCurrentPlace())
        FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(placeId, fields);

        placesClient.fetchPlace(placeRequest).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            // Get the photo metadata.
            Log.d("sie", String.valueOf(place.getPhotoMetadatas().size()));
            int placePhotoSize = place.getPhotoMetadatas().size();



            if(placePhotoSize>5){ //place api에 있는 장소 사진 5개만 불러오기 위함
                for(int i=0;i<5;i++){
                    View view = inflater.inflate(R.layout.place_picture_item,gallery,false);
                    ImageView itemView = view.findViewById(R.id.placeItemView);
                    PhotoMetadata photoMetadata = place.getPhotoMetadatas().get(i);


                    FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                            .build();
                    placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                        Bitmap bitmap = fetchPhotoResponse.getBitmap();
                        itemView.setImageBitmap(bitmap);
                        gallery.addView(view);
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
                    View view = inflater.inflate(R.layout.place_picture_item,gallery,false);
                    ImageView itemView = view.findViewById(R.id.placeItemView);
                    PhotoMetadata photoMetadata = place.getPhotoMetadatas().get(i);


                    FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                            .build();
                    placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                        Bitmap bitmap = fetchPhotoResponse.getBitmap();
                        itemView.setImageBitmap(bitmap);
                        gallery.addView(view);
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


            PhotoMetadata photoMetadata = place.getPhotoMetadatas().get(1);

            // Get the attribution text.
            String attributions = photoMetadata.getAttributions();

            // Create a FetchPhotoRequest.
            FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                    .build();

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
            placeName.setText(place.getName());
            Log.i(TAG, "Place found: " + place.getName());
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

}
