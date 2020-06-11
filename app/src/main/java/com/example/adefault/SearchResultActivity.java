package com.example.adefault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adefault.Decoration.Recommend;
import com.example.adefault.Decoration.SearchResult;
import com.example.adefault.Decoration.SearchResultDecoration;
import com.example.adefault.adapter.SearchResultAdapter;
import com.example.adefault.data.ResultData;
import com.example.adefault.manager.AppManager;
import com.example.adefault.manager.ImageManager;
import com.example.adefault.util.RetrofitClient;
import com.example.adefault.util.TaskServer;
import com.example.adefault.util.UserToken;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {

    //GPS variable
    private double longitude;
    private double latitude;
    private double altitude;
    private LocationManager lm;
    private LocationListener gpsLocationListener;
    private ArrayList<ResultData> result_itemList;
    //PlacesService placesService = new PlacesService();
    private SearchResult result;
    private SearchResult result2;
    private ResultData first_result;
    //component
    private RecyclerView result_listview;
    private SearchResultAdapter searchResultAdapter;
    private CustomActionBar ca;

    private String mSearchSentence;
    private String mUri;

    private Button tagBtn1;
    private Button tagBtn2;
    private Button tagBtn3;
    private Button tagBtn4;
    private Button tagBtn5;

    private View first_view;
    private Intent intent;

    RetrofitClient retrofitClient = new RetrofitClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getInstance().setContext(this);
        AppManager.getInstance().setResources(getResources());

        setContentView(R.layout.activity_search_result);

        init();
        initListener();
        setActionBar();

        if(mSearchSentence != "") {
            searchSentenceResponse();
        }
        else if(mUri != "") {
            searchImageResponse();
        }

    }

    public void init() {
        intent = getIntent();
        mSearchSentence = intent.getStringExtra("searchSentence");
        mUri = intent.getStringExtra("uri");

        //btn init()
        tagBtn1 = findViewById(R.id.tag1);
        tagBtn2 = findViewById(R.id.tag2);
        tagBtn3 = findViewById(R.id.tag3);
        tagBtn4 = findViewById(R.id.tag4);
        tagBtn5 = findViewById(R.id.tag5);

        //list view init()
        result_listview = findViewById(R.id.result_listview);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(AppManager.getInstance().getContext(), 2);

        result_listview.setLayoutManager(mLayoutManager);

    }

    public void setActionBar() {
        ca = new CustomActionBar(this, getSupportActionBar());
        ca.setActionBar();
        //ca.getLogo().setVisibility(View.GONE);
        if(mSearchSentence!=""){
            ca.getActionBar().setTitle("\""+mSearchSentence+"\""+" 검색 결과");
        }
        else if(mUri!=""){
            ca.getActionBar().setTitle("이미지 검색 결과");

        }
    }


    public void initListener() {

    }

    public void searchSentenceResponse() {
        progressON("검색 중입니다...");
        Recommend recommend = new Recommend();
        recommend.setRecommend("매콤한 떡볶이 추천해줘");
        Call<SearchResult> call = retrofitClient.apiService.getretrofitdata_search_result(recommend,"Token "+ UserToken.getToken());
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (response.isSuccessful()) {
                    result = response.body();
                    Log.d("response", response.toString());

                    NetworkTask networkTask = new NetworkTask(AppManager.getInstance().getContext(), result);
                    networkTask.execute();

                } else {
                    progressOFF();
                    Log.d("data", response.toString());
                }

            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                progressOFF();
                Log.d("response", "앙실패띠");
                t.printStackTrace();
            }
        });
    }

    public void searchImageResponse() {
        progressON("검색 중입니다...");
        File file = new File(mUri);
        Log.d("path: ",mUri);
        RequestBody imgFileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        final MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), imgFileReqBody);

        Call<SearchResult> call = retrofitClient.apiService.getretrofitdata_search_result_image(image,"Token "+ UserToken.getToken());
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (response.isSuccessful()) {
                    result2 = response.body();
                    Log.d("response", response.toString());
                    //place_detail를 통한 장소가져오기
                    if(result2 != null){
                        NetworkTask networkTask = new NetworkTask(AppManager.getInstance().getContext(), result2);
                        networkTask.execute();
                    }
                    else{
                        progressOFF();
                        Toast.makeText(AppManager.getInstance().getContext(), "인식실패", Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        SearchFragment searchfragment = new SearchFragment();
                        fragmentTransaction.replace(R.id.Main_Frame,searchfragment);
                        fragmentTransaction.commit();
                    }

                } else {
                    progressOFF();
                    Log.d("data", response.toString());
                }

            }
            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Log.d("response", "앙실패띠");
                t.printStackTrace();
            }
        });
    }

    private void insert_first_item(ResultData resultData) {

        ImageView first_image = findViewById(R.id.first_image);
        TextView place_name = findViewById(R.id.place_name);
        RatingBar first_ratingbar = findViewById(R.id.first_ratingbar);
        TextView first_rating = findViewById(R.id.first_rating);
        TextView first_rcm_person = findViewById(R.id.first_rcm_person);

        first_image.setOnClickListener(new View.OnClickListener(){

               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(AppManager.getInstance().getContext(), PlaceDetailActivity.class);
                   intent.putExtra("place_id", resultData.getPlace_id());
                   startActivity(intent);
               }
           }
        );
        if(resultData.getImage()!=""){
            Glide.with(this)
                    .load(resultData.getImage())
                    .into(first_image);
        }
        else{
            first_image.setImageResource(R.drawable.movieposter1);
        }

        place_name.setText(resultData.getPlaceName());
        first_ratingbar.setRating(resultData.getRating());
        first_rating.setText(Integer.toString(resultData.getRating()));
        first_rcm_person.setText(resultData.getRcm_person());
    }

    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            Toast.makeText(AppManager.getInstance().getContext(), str, Toast.LENGTH_SHORT).show();
        }
    };

    private class DrawUrlImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView ivSample;

        public DrawUrlImageTask(ImageView ivSample) {
            this.ivSample = ivSample;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            InputStream in = null;

            try {
                in = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return bitmap;
        }

        protected void onPostExecute(Bitmap bitmap) {
            ivSample.setImageBitmap(bitmap);
        }
    }

    //----------------------------
    /*  게시글 뿌려주는 클래스     */
    //----------------------------
    public class NetworkTask extends AsyncTask<Void, Void, SearchResult> {

        SearchResult value;
        ContentValues values;
        Context mcontext;

        NetworkTask(Context mcontext, ContentValues values) {
            this.mcontext = mcontext;
            this.values = values;
        } // 생성자

        NetworkTask(Context mcontext, SearchResult value) {
            this.mcontext = mcontext;
            this.value = value;
        }//생성자

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
        }  //실행 이전에 작업되는 것들을 정의하는 함수

        @Override
        protected SearchResult doInBackground(Void... params) {
            SearchResult results;

            results = value;

            //작업 수정해야함 ui작업은 밑에서
            result_itemList = new ArrayList<>();
            Log.d("Size: ", Integer.toString(result.getRecommendationList().size()));

            for (int i = 0; i < result.getRecommendationList().size(); i++) {

                String nickname = result.getRecommendationList().get(i).get("nickname").getAsString() + "님이 추천한 장소";
                JsonArray place_id_list = result.getRecommendationList().get(i).get("place").getAsJsonArray();
                for (int j = 0; j < place_id_list.size(); j++) {
                    String photo_reference = "";
//                        placeArrayList.add(PlacesService.details(place_id_list.get(j).toString()));
                    JsonObject JsonObj = place_id_list.get(j).getAsJsonObject();
                    Place place = (PlacesService.details(JsonObj.get("place_id").getAsString()));
                    Log.d("tlqkffus", place_id_list.get(j).toString());
                    Log.d("싸발적", place.toString());
                    if (place.getPhotos() != null) {
                        Log.d("포토", place.getPhotos().toString());
                        try {
                            JSONObject tmp = (JSONObject) place.getPhotos().get(0);//인덱스 번호로 접근해서 가져온다.
                            photo_reference = (String) tmp.get("photo_reference");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (j == 0 && i == 0) {
                            first_result = new ResultData(PlacesService.Photo(photo_reference), place.getName()
                                    , (int) place.getRating(), nickname,JsonObj.get("place_id").getAsString());
                        } else {
                            result_itemList.add(new ResultData(PlacesService.Photo(photo_reference), place.getName()
                                    , (int) place.getRating(), nickname,JsonObj.get("place_id").getAsString()));
                        }
                    } else {
                        Drawable drawable = getResources().getDrawable(R.drawable.movieposter1);

                        // drawable 타입을 bitmap으로 변경
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                        if (j == 0 && i == 0) {
                            first_result = new ResultData("", place.getName()
                                    , (int) place.getRating(), nickname,JsonObj.get("place_id").getAsString());
                        } else {
                            result_itemList.add(new ResultData("", place.getName()
                                    , (int) place.getRating(), nickname,JsonObj.get("place_id").getAsString()));
                        }
                    }
                }
            }

            return results; // 결과가 여기에 담깁니다. 아래 onPostExecute()의 파라미터로 전달됩니다.
        } //백그라운드 작업 함수

        //------------------------------------
        /* 서버로 부터 받아온 게시글로 UI 작업  */
        //------------------------------------
        @Override
        protected void onPostExecute(SearchResult result) {
            // 통신이 완료되면 호출됩니다.
            // 결과에 따른 UI 수정 등은 여기서 합니다.

            if (result != null) {
                progressOFF();
                insert_first_item(first_result);
                searchResultAdapter = new SearchResultAdapter(AppManager.getInstance().getContext(), result_itemList, onClickItem);
                result_listview.setAdapter(searchResultAdapter);

                SearchResultDecoration searchResultDecoration = new SearchResultDecoration();
                result_listview.addItemDecoration(searchResultDecoration);
            } else {
                progressOFF();
                Toast.makeText(AppManager.getInstance().getContext(), "반환데이터 없음.", Toast.LENGTH_SHORT).show();
            }
        }
    } //NetWorkTask Class

    public void progressON(String message) {
        ImageManager.getInstance().progressON((Activity)AppManager.getInstance().getContext(), message);
    }

    public void progressOFF() {
        ImageManager.getInstance().progressOFF();
    }
}
