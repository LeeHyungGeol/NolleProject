package com.example.adefault;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adefault.Decoration.PickResult;
import com.example.adefault.adapter.PickPlaceAdapter;
import com.example.adefault.data.PickData;
import com.example.adefault.manager.AppManager;
import com.example.adefault.util.RetrofitClient;
import com.example.adefault.util.UserToken;
import com.github.islamkhsh.CardSliderViewPager;
import com.github.islamkhsh.viewpager2.ViewPager2;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PickFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //구글맵참조변수
    GoogleMap mMap;
    private MapView mapView = null;

    RetrofitClient retrofitClient = new RetrofitClient();
    private PickResult result;
    private ArrayList<PickData> result_itemList;
    private CardSliderViewPager cardSliderViewPager;
    private PickPlaceAdapter pickPlaceAdapter;
    private TextView nameView;
    private TextView ratingView;
    private RatingBar ratingbarView;
    private View view;
    private Float lat;
    private Float lng;

    public PickFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).showOptionMenu(false);
    }


    public static PickFragment newInstance(String param1, String param2) {
        PickFragment fragment = new PickFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pick, container, false);

        /*mapView = (MapView)view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this); // 비동기적 방식으로 구글 맵 실행*/
        // SupportMapFragment을 통해 레이아웃에 만든 fragment의 ID를 참조하고 구글맵을 호출한다.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); //getMapAsync must be called on the main thread.


        // PICk PLACE
        nameView = view.findViewById(R.id.place_name);
        ratingbarView = view.findViewById(R.id.ratingbar);
        ratingView = view.findViewById(R.id.rating);
        cardSliderViewPager = (CardSliderViewPager) view.findViewById(R.id.viewPager);
        cardSliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(result_itemList!=null){
                    nameView.setText(result_itemList.get(position).getPlaceName());
                    ratingbarView.setRating(result_itemList.get(position).getRating());
                    ratingView.setText(Float.toString(result_itemList.get(position).getRating()));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(result_itemList.get(position).getLocation())); //해당위치로 카메라 이동시키기.
                }
                Log.e("Selected_Page", String.valueOf(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        //cardSliderViewPager.setAdapter(new PickPlaceAdapter(movies));
        Call<PickResult> call = retrofitClient.apiService.getretrofitdata_pick("Token "+ UserToken.getToken());
        call.enqueue(new Callback<PickResult>() {
            @Override
            public void onResponse(Call<PickResult> call, Response<PickResult> response) {
                if (response.isSuccessful()) {
                    result = response.body();
                    Log.d("response", response.toString());

                    //place_detail를 통한 장소가져오기
                    PickFragment.NetworkTask networkTask = new PickFragment.NetworkTask(getActivity(), result);
                    networkTask.execute();

                } else {
                    Log.d("data", response.toString());
                }
            }


            @Override
            public void onFailure(Call<PickResult> call, Throwable t) {
                Log.d("response", "앙실패띠");
                t.printStackTrace();
            }
        });

        return view;
    }

    @Override //구글맵을 띄울준비가 됬으면 자동호출된다.
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //지도타입 - 일반
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        oneMarker();
        // manyMarker();
    }

    //마커하나찍는 기본 예제
    public void oneMarker() {
        // 서울 여의도에 대한 위치 설정
        LatLng seoul = new LatLng(37.52487, 126.92723);

        // 구글 맵에 표시할 마커에 대한 옵션 설정  (알파는 좌표의 투명도이다.)
        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions
                .position(seoul)
                .title("원하는 위치(위도, 경도)에 마커를 표시했습니다.")
                .snippet("여기는 여의도인거같네여!!")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .alpha(0.5f);

        // 마커를 생성한다. showInfoWindow를 쓰면 처음부터 마커에 상세정보가 뜨게한다. (안쓰면 마커눌러야뜸)
        mMap.addMarker(makerOptions); //.showInfoWindow();

        //정보창 클릭 리스너
        mMap.setOnInfoWindowClickListener(infoWindowClickListener);

        //마커 클릭 리스너
        mMap.setOnMarkerClickListener(markerClickListener);

        //카메라를 여의도 위치로 옮긴다.
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));
        //처음 줌 레벨 설정 (해당좌표=>서울, 줌레벨(16)을 매개변수로 넣으면 된다.) (위에 코드대신 사용가능)(중첩되면 이걸 우선시하는듯)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 16));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getActivity(), "해위", Toast.LENGTH_LONG);
                return false;
            }
        });




    }

    ////////////////////////  구글맵 마커 여러개생성 및 띄우기 //////////////////////////
    public void manyMarker() {
        // for loop를 통한 n개의 마커 생성
        for (int idx = 0; idx < 10; idx++) {
            // 1. 마커 옵션 설정 (만드는 과정)
            MarkerOptions makerOptions = new MarkerOptions();
            makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                    .position(new LatLng(37.52487 + idx, 126.92723))
                    .title("마커" + idx); // 타이틀.

            // 2. 마커 생성 (마커를 나타냄)
            mMap.addMarker(makerOptions);
        }
        //정보창 클릭 리스너
        mMap.setOnInfoWindowClickListener(infoWindowClickListener);

        //마커 클릭 리스너
        mMap.setOnMarkerClickListener(markerClickListener);

        // 카메라를 위치로 옮긴다.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.52487, 126.92723)));
    }

    //마커정보창 클릭리스너는 다작동하나, 마커클릭리스너는 snippet정보가 있으면 중복되어 이벤트처리가 안되는거같다.
    // oneMarker(); 는 동작하지않으나 manyMarker(); 는 snippet정보가 없어 동작이가능하다.

    //정보창 클릭 리스너
    GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {

            String markerId = marker.getId();
            String restr = markerId.replaceAll("[^0-9]","");
            Log.d("datadafa",markerId);

            Toast.makeText(getActivity(), "정보창 클릭 Marker ID : "+markerId, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AppManager.getInstance().getContext(), PlaceDetailActivity.class);
            intent.putExtra("place_id", result_itemList.get(Integer.parseInt(restr)-1).getPlace_id());
            startActivity(intent);
        }
    };

    //마커 클릭 리스너
    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            String markerId = marker.getId();
            //선택한 타겟위치
            LatLng location = marker.getPosition();
            Toast.makeText(getActivity(), "마커 클릭 Marker ID : "+markerId+"("+location.latitude+" "+location.longitude+")", Toast.LENGTH_SHORT).show();
            return false;
        }
    };


    //----------------------------
    /*  게시글 뿌려주는 클래스     */
    //----------------------------
    public class NetworkTask extends AsyncTask<Void, Void, PickResult> {

        PickResult value;
        ContentValues values;
        Context mcontext;

        NetworkTask(Context mcontext, ContentValues values) {
            this.mcontext = mcontext;
            this.values = values;
        } // 생성자

        NetworkTask(Context mcontext, PickResult value) {
            this.mcontext = mcontext;
            this.value = value;
        }//생성자

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
        }  //실행 이전에 작업되는 것들을 정의하는 함수

        @Override
        protected PickResult doInBackground(Void... params) {
            PickResult results;

            results = value;

            //작업 수정해야함 ui작업은 밑에서
            result_itemList = new ArrayList<>();
            Log.d("Size: ", Integer.toString(result.getPick().size()));

            for (int i = 0; i < result.getPick().size(); i++) {
                String photo_reference = "";
                JsonObject JsonObj = result.getPick().get(i).getAsJsonObject();
                Place place = (PlacesService.details(JsonObj.get("place_id").getAsString()));
                Log.d("tlqkffus", result.getPick().get(i).toString());
                Log.d("싸발적", place.toString());
                if (place.getPhotos() != null) {
                    Log.d("포토", place.getPhotos().toString());
                    try {
                        JSONObject tmp = (JSONObject) place.getPhotos().get(0);//인덱스 번호로 접근해서 가져온다.
                        photo_reference = (String) tmp.get("photo_reference");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    result_itemList.add(new PickData(PlacesService.Photo(photo_reference), place.getName()
                            , (int) place.getRating(),place.getLatLng(),JsonObj.get("place_id").getAsString()));

                } else {
                    Drawable drawable = getResources().getDrawable(R.drawable.movieposter1);

                    // drawable 타입을 bitmap으로 변경
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();


                    result_itemList.add(new PickData(bitmap,place.getName()
                            , (int) place.getRating(),place.getLatLng(),JsonObj.get("place_id").getAsString()));

                }


            }

            return results; // 결과가 여기에 담깁니다. 아래 onPostExecute()의 파라미터로 전달됩니다.
        } //백그라운드 작업 함수
        private View.OnClickListener onClickItem = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = (String) v.getTag();
                Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
            }
        };
        //------------------------------------
        /* 서버로 부터 받아온 게시글로 UI 작업  */
        //------------------------------------
        @Override
        protected void onPostExecute(PickResult result) {
            // 통신이 완료되면 호출됩니다.
            // 결과에 따른 UI 수정 등은 여기서 합니다.

            if (result != null) {
                MarkerOptions makerOptions;
                makerOptions = new MarkerOptions();
                for(int i=0;i<result_itemList.size();i++){
                    Log.d("data",result_itemList.get(i).getLocation().toString());
                    makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                            .position(result_itemList.get(i).getLocation())
                            .title(result_itemList.get(i).getPlaceName()); // 타이틀.

                    // 2. 마커 생성 (마커를 나타냄)
                    mMap.addMarker(makerOptions);

                }

                pickPlaceAdapter = new PickPlaceAdapter(getActivity(), result_itemList, onClickItem);
                cardSliderViewPager.setAdapter(pickPlaceAdapter);

            } else {
                Toast.makeText(getActivity(), "반환데이터 없음.", Toast.LENGTH_SHORT).show();
            }
        }
    } //NetWorkTask Class

}
