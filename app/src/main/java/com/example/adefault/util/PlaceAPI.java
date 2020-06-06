package com.example.adefault.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.adefault.R;
import com.example.adefault.adapter.home.HomeSliderAdapter;
import com.example.adefault.model.home.HomeSliderItem;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class PlaceAPI {

    private PlacesClient mPlacesClient;
    private Bitmap mBitmap;

    public PlaceAPI() {
    }

    public PlaceAPI(PlacesClient placesClient) {
        this.mPlacesClient = placesClient;
    }

    public ArrayList<String> autoComplete(String input){
        ArrayList<String> arrayList = new ArrayList();
        HttpURLConnection connection =null;
        StringBuilder jsonResult = new StringBuilder();
        try{
            StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/autocomplete/json?");
            sb.append("input="+input);
            sb.append("&key=AIzaSyBLf4XF6AC1wJONoWVE6rtwcpQZLl8pXnU&language=ko&components=country:kr");
            URL url = new URL(sb.toString());
            connection = (HttpURLConnection)url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());

            int read;

            char[] buff = new char[1024];
            while((read = inputStreamReader.read(buff))!=-1){
                jsonResult.append(buff,0,read);
            }
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if(connection!=null){
                connection.disconnect();
            }

        }

        try{
            JSONObject jsonObject = new JSONObject(jsonResult.toString());
            JSONArray prediction = jsonObject.getJSONArray("predictions");
            for(int i=0;i<prediction.length();i++){
                String searchResult = prediction.getJSONObject(i).getString("description");
                arrayList.add(searchResult.replace("대한민국",""));
            }

        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return arrayList;

    }

    public void setBitmapByPlaceId(HomeSliderAdapter adapter, String googlePlaceId) {
        // Define a Place ID.
        //String placeId = "INSERT_PLACE_ID_HERE";
        String placeId = googlePlaceId;
        System.out.println("googlePlaceId : " + googlePlaceId);

        // Specify fields. Requests for photos must always have the PHOTO_METADATAS field.
        List<Place.Field> fields = Arrays.asList(Place.Field.PHOTO_METADATAS);

        // Get a Place object (this example uses fetchPlace(), but you can also use findCurrentPlace())
        FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(placeId, fields);

        mPlacesClient.fetchPlace(placeRequest).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            System.out.println("response.getPlace() : " + response.getPlace());
            try {
                // Get the photo metadata.
                PhotoMetadata photoMetadata = place.getPhotoMetadatas().get(0);


                // Get the attribution text.
                String attributions = photoMetadata.getAttributions();

                // Create a FetchPhotoRequest.
                FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                        .build();

                mPlacesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                    mBitmap = fetchPhotoResponse.getBitmap();
                    System.out.println("mBitmap : " + mBitmap);
                    adapter.addItem(new HomeSliderItem(mBitmap));
                    //imageView.setImageBitmap(bitmap);
                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        int statusCode = apiException.getStatusCode();
                        // Handle error with given status code.
                        Log.e(TAG, "Place not found: " + exception.getMessage());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nolle_login_logo);

            }
        });


    }

}
