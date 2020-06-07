package com.example.adefault;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.adefault.model.BoardDTO;
import com.example.adefault.model.BoardResponseDTO;
import com.example.adefault.util.RestApi;
import com.example.adefault.util.RestApiUtil;
import com.example.adefault.util.UserToken;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.lujun.androidtagview.TagContainerLayout;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AddBoardActivity extends FragmentActivity {

    private BoardDTO boardDTO; ////게시판 내용이 들어가있는 객체
    private ImageView imageView;
    private ImagePicker imagePicker;
    private List<Image> images;
    private LinearLayout gallery; //이미지 보여주기 위한 좌우스크롤 레이아웃
    private LayoutInflater inflater;
    private RatingBar ratingBar;
    private EditText placeReviewText;
    private TextView placeReviewLimit;
    private Button boardBtn;
    private EditText hashTagText; //해시태그 색 바꾸기
    private Spannable mspanable;
    private int hashTagIsComing; //해시태그 색 바꾸기위한 변수
    private int imageCnt;
    private ArrayList<Uri> fileUris;
    private ArrayList<String> tagList;
    private ArrayList<File> files;
    private AutocompleteSupportFragment autocompleteFragment;
    private String place_id;
    private String TAG="AddBoardActivity";
    private TagContainerLayout reviewTagContainerLayout;
    private Button tagAddBtn;
    private String placeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //타이틀바 없애기
        setContentView(R.layout.activity_add_board);
        init();
        addListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images

            images = ImagePicker.getImages(data);
            imageCnt+= images.size();
            if(imageCnt<=5) //이미지 최대 5개 등록 제한을 위함
            {
                imagePick(images, data.getClipData());
            }
            else
            {
                Toast.makeText(this, "이미지는 최대 5개까지 등록할 수 있습니다.", Toast.LENGTH_SHORT).show();
                imageCnt-= images.size();
            }
        }
    }


    private void init(){
        Places.initialize(this,getString(R.string.places_api_key));
        boardDTO = new BoardDTO();
        imageView= findViewById(R.id.imageView);
        imagePicker = ImagePicker.create(this)
                .limit(5);
        gallery = findViewById(R.id.gallery);
        inflater=LayoutInflater.from(this); //동적 이미지 스크롤을 위한 inflater
        ratingBar = findViewById(R.id.reviewRatingBar);
        placeReviewText = findViewById(R.id.placeReviewText);
        placeReviewLimit=findViewById(R.id.placeReviewLimit);
        boardBtn=findViewById(R.id.boardBtn);
        hashTagText=findViewById(R.id.placeTagText);
        mspanable=hashTagText.getText();
        hashTagIsComing = 0;
        imageCnt=0;
        fileUris = new ArrayList<Uri>();
        tagList = new ArrayList<String>();
        files = new ArrayList<File>();
        // Initialize the AutocompleteSupportFragment.
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
// Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteFragment.setCountry("kr");
        autocompleteFragment.setHint("장소를 입력해주세요");
        reviewTagContainerLayout = findViewById(R.id.reviewTagContainerLayout);
        tagAddBtn = findViewById(R.id.tagAddBtn);
    }



    private void imagePick(List<Image> images, ClipData clipData){ //이미지 선택 메소드

        for(int i=0;i<images.size();i++){
            View view = inflater.inflate(R.layout.gallery_item,gallery,false);
            ImageView itemView = view.findViewById(R.id.itemImageView);
            File imgFile = new File(images.get(i).getPath());
            files.add(imgFile);
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            itemView.setImageBitmap(bitmap);
            gallery.addView(view); //이미지 레이아웃에 동적 추가
        }

    }

    private void addListener(){

        tagAddBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tagList.size()<5){
                    tagList.add(hashTagText.getText().toString());
                    hashTagText.setText("");
                    reviewTagContainerLayout.setTags(tagList);
                }
                else{
                    Toast.makeText(getApplicationContext(),"태그는 최대 5개 까지 등록할 수 있습니다",Toast.LENGTH_SHORT).show();
                }

            }
        });


        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                imagePicker.start();

            }
        });

        placeReviewText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = placeReviewText.getText().toString();
                placeReviewLimit.setText(input.length()+" /50 글자 수");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        boardBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                setBoard();
                if(!boardDTO.getContext().equals("") && !boardDTO.getPlace_name().equals("")&&!boardDTO.getRating().equals("0.0") && files.get(0).exists()){
                    Log.d("rating",boardDTO.getRating());
                    upLoad();
                }
                else{
                    Toast.makeText(AddBoardActivity.this, "내용이나 사진을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
//                Intent intent = new Intent(AddBoardActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });



// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                place_id = place.getId();
                placeName = place.getName();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
            }
        });
    }

    private void setBoard(){
        boardDTO.setPlace_id(place_id);
        boardDTO.setImages(fileUris);
        boardDTO.setRating(Float.toString(ratingBar.getRating()));
        boardDTO.setContext(placeReviewText.getText().toString());
        boardDTO.setTag(tagList);
        boardDTO.setPlace_name(placeName);
    }

    private void upLoad() { //서버에 게시글 보내기
        String token = "Token "+ UserToken.getToken();
        Retrofit mRetrofit = RestApiUtil.getRetrofitClient(this);
        RestApi restApi = mRetrofit.create(RestApi.class);

        Map<String, RequestBody> boardMaps = new HashMap<>();

        RequestBody requestPlaceID = RequestBody.create(MediaType.parse("text/plain"), boardDTO.getPlace_id());
        RequestBody requestRating = RequestBody.create(MediaType.parse("text/plain"), boardDTO.getRating());
        RequestBody requestContext = RequestBody.create(MediaType.parse("text/plain"), boardDTO.getContext());
        RequestBody requestPlaceName = RequestBody.create(MediaType.parse("text/plain"), boardDTO.getPlace_name());



        boardMaps.put("place_id", requestPlaceID);
        boardMaps.put("rating", requestRating);
        boardMaps.put("context", requestContext);
        boardMaps.put("place_name",requestPlaceName);

        ArrayList<RequestBody> RequestBodyTags = new ArrayList<>();
        ArrayList<RequestBody> RequestBodyImages = new ArrayList<>();


        for (int i = 0; i < tagList.size(); i++) {
            RequestBodyTags.add(RequestBody.create(MediaType.parse("text/plain"), boardDTO.getTag().get(i)));
            boardMaps.put("tag_" + (i + 1), RequestBodyTags.get(i));
        }

        for (int i = 0; i < files.size(); i++) {
            RequestBodyImages.add(RequestBody.create(MediaType.parse("image/*"), files.get(i)));
            boardMaps.put( "img_" + (i+1) + "\"; filename=\"" + files.get(i).getName() , RequestBodyImages.get(i));
        }



        Call<BoardResponseDTO> call = restApi.uploadBoard(token, boardMaps);
        call.enqueue(new Callback<BoardResponseDTO>() {
            @Override
            public void onResponse(Call<BoardResponseDTO> call, Response<BoardResponseDTO> response) {
                BoardResponseDTO boardResponseDTO = response.body();
                if(response.isSuccessful()) {
                    if(boardResponseDTO.getCheck().equals("True"))
                    {
                        Toast.makeText(AddBoardActivity.this, "게시글 업로드 성공", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else{
                        Log.d("게시글","실패");

                    }
                }
                else{
                    Log.d("resopnse","실패");
                }
            }

            @Override
            public void onFailure(Call<BoardResponseDTO> call, Throwable t) {
                System.out.println(t.getMessage());
                System.out.println("게시글 업로드 실패");
                Toast.makeText(AddBoardActivity.this, "게시글 업로드 실패", Toast.LENGTH_LONG).show();
            }
        });

    }


}