package com.example.adefault;

import android.content.ContentValues;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adefault.Decoration.Recommend;
import com.example.adefault.Decoration.SearchResult;
import com.example.adefault.Decoration.SearchResultDecoration;
import com.example.adefault.adapter.SearchResultAdapter;
import com.example.adefault.data.ResultData;
import com.example.adefault.util.RetrofitClient;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    private View view;
    private RecyclerView result_listview;
    private SearchResultAdapter searchResultAdapter;

    private String mUserId;
    private String mUri;

    private Button tagBtn1;
    private Button tagBtn2;
    private Button tagBtn3;
    private Button tagBtn4;
    private Button tagBtn5;

    RetrofitClient retrofitClient = new RetrofitClient();

    public SearchResultFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * //@param param1 Parameter 1.
     * //@param param2 Parameter 2.
     *
     * @return A new instance of fragment SearchResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(String userId, String uri) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        args.putString("uri", uri);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mUserId = getArguments().getString("userId"); // 전달한 key 값
            mUri = getArguments().getString("uri"); // 전달한 key 값


        }

        gpsLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {

                String provider = location.getProvider();
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                altitude = location.getAltitude();


            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


    }//OnCreate()


    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search_result, container, false);
        getActivity().setTitle(mUserId);
        init();

        if (mUserId != "") {


            Recommend recommend = new Recommend();
            recommend.setRecommend("매콤한 떡볶이 추천해줘");
            Call<SearchResult> call = retrofitClient.apiService.getretrofitdata_search_result(recommend,"Token "+ UserToken.getToken());
            call.enqueue(new Callback<SearchResult>() {
                @Override
                public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                    if (response.isSuccessful()) {
                        result = response.body();
                        Log.d("response", response.toString());

                        //태그 랜덤 설정
                        //tagBtn1.setText(result.getCategory_m().get(1).get("ctgr_name").getAsString());
                        //tagBtn2.setText(result.getCategory_s().get(1).get("ctgr_name").getAsString());
                        //tagBtn3.setText(result.getCategory_m().get(2).get("ctgr_name").getAsString());
                        //tagBtn4.setText(result.getCategory_s().get(2).get("ctgr_name").getAsString());
                        //tagBtn5.setText(result.getCategory_m().get(3).get("ctgr_name").getAsString());
//                    if ( Build.VERSION.SDK_INT >= 23 &&
//                            ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
//                        ActivityCompat.requestPermissions( getActivity(), new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
//                                0 );
//                        Log.d("aadf","adfa");
//                    }
//                    else{
//                        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//                        longitude = location.getLongitude();//경도
//                        latitude = location.getLatitude();//위도
//                        altitude = location.getAltitude();//고도
//                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,gpsLocationListener);
//                        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,1,gpsLocationListener);
//
//                        String provider = location.getProvider();
//
//                        Log.d("long",Double.toString(longitude));
//                        Log.d("lati",Double.toString(latitude));
//
//                        new Thread() {
//                            public void run() {
//                                placeArrayList = PlacesService.search("치킨",latitude,longitude,1000);
//                                Log.d("aadf","adfa");
//                                Log.d("size",Integer.toString(placeArrayList.size()));
//                                for(int i=0;i<placeArrayList.size();i++){
//                                    Log.d("tlqkf","tlqkf");
//                                    Log.d("rorkxdmsrj",placeArrayList.get(i).getName());
//                                    Log.d("data:11",placeArrayList.get(i).getName());
//                                    Log.d("data:22",placeArrayList.get(i).getId());
//                                    Log.d("data22",placeArrayList.get(i).getReference());
//                                }
//                            }
//                        }.start();
//
//                    }


                        //place_detail를 통한 장소가져오기
                        NetworkTask networkTask = new NetworkTask(getActivity(), result);
                        networkTask.execute();

                    } else {
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
        else if(mUri!=""){
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
                        if(result2!=null){
                            NetworkTask networkTask = new NetworkTask(getActivity(), result2);
                            networkTask.execute();
                        }
                        else{
                            Toast.makeText(getActivity(),"인식실패",Toast.LENGTH_SHORT).show();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            SearchFragment searchfragment = new SearchFragment();
                            fragmentTransaction.replace(R.id.Main_Frame,searchfragment);
                            fragmentTransaction.commit();
                        }

                    } else {
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
        return view;
    }

    private void insert_first_item(ResultData resultData) {
        ImageView first_image = view.findViewById(R.id.first_image);
        TextView place_name = view.findViewById(R.id.place_name);
        RatingBar first_ratingbar = view.findViewById(R.id.first_ratingbar);
        TextView first_rating = view.findViewById(R.id.first_rating);
        TextView first_rcm_person = view.findViewById(R.id.first_rcm_person);

        first_image.setImageBitmap(resultData.getImage());
        place_name.setText(resultData.getPlaceName());
        first_ratingbar.setRating(resultData.getRating());
        first_rating.setText(Integer.toString(resultData.getRating()));
        first_rcm_person.setText(resultData.getRcm_person() + "님이 추천한 장소");
    }

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

    private void init() {
        //btn init()
        tagBtn1 = view.findViewById(R.id.tag1);
        tagBtn2 = view.findViewById(R.id.tag2);
        tagBtn3 = view.findViewById(R.id.tag3);
        tagBtn4 = view.findViewById(R.id.tag4);
        tagBtn5 = view.findViewById(R.id.tag5);

        //list view init()
        result_listview = view.findViewById(R.id.result_listview);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);


        result_listview.setLayoutManager(mLayoutManager);

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
                                    , (int) place.getRating(), nickname);
                        } else {
                            result_itemList.add(new ResultData(PlacesService.Photo(photo_reference), place.getName()
                                    , (int) place.getRating(), nickname));
                        }
                    } else {
                        Drawable drawable = getResources().getDrawable(R.drawable.movieposter1);

                        // drawable 타입을 bitmap으로 변경
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                        if (j == 0 && i == 0) {
                            first_result = new ResultData(bitmap, place.getName()
                                    , (int) place.getRating(), nickname);
                        } else {
                            result_itemList.add(new ResultData(bitmap, place.getName()
                                    , (int) place.getRating(), nickname));
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
                insert_first_item(first_result);
                searchResultAdapter = new SearchResultAdapter(getActivity(), result_itemList, onClickItem);
                result_listview.setAdapter(searchResultAdapter);

                SearchResultDecoration searchResultDecoration = new SearchResultDecoration();
                result_listview.addItemDecoration(searchResultDecoration);
            } else {
                Toast.makeText(getActivity(), "반환데이터 없음.", Toast.LENGTH_SHORT).show();
            }
        }
    } //NetWorkTask Class


}//Fragment Class
