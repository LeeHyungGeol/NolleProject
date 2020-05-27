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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
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
import com.example.adefault.adapter.PlaceAutoSuggestAdapter;
import com.example.adefault.model.BoardDTO;
import com.example.adefault.model.BoardResponseDTO;
import com.example.adefault.util.RestApi;
import com.example.adefault.util.RestApiUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AddBoardActivity extends FragmentActivity {

    private AutoCompleteTextView autoCompleteTextView;
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
        boardDTO = new BoardDTO();
        autoCompleteTextView = findViewById(R.id.autocomplete);
        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter(AddBoardActivity.this,R.layout.search_place_list_layout));
        imageView= findViewById(R.id.imageView);
        imagePicker = ImagePicker.create(this)
                .limit(5);
        gallery = findViewById(R.id.gallery);
        inflater=LayoutInflater.from(this); //동적 이미지 스크롤을 위한 inflater
        ratingBar = findViewById(R.id.ratingBar);
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
//            ClipData.Item clipItem = clipData.getItemAt(i);
//            Uri uri = clipItem.getUri();
//            fileUris.add(uri);
        }

    }

    private void addListener(){

        autoCompleteTextView.setOnClickListener(new View.OnClickListener(){ //검색 장소 리스너 구현

            @Override
            public void onClick(View v) {
                autoCompleteTextView.getText().clear();
                autoCompleteTextView.setFocusableInTouchMode(true);
                autoCompleteTextView.setFocusable(true);
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //검색 장소 리스너 구현
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoCompleteTextView.setFocusable(false);
                autoCompleteTextView.setClickable(false);

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
                tagDivide();
                setBoard();
                upLoad();
//                Intent intent = new Intent(AddBoardActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });

        hashTagText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String startChar = null;

                try{
                    startChar = Character.toString(s.charAt(start));
                    Log.i(getClass().getSimpleName(), "CHARACTER OF NEW WORD: " + startChar);
                }
                catch(Exception ex){
                    startChar = "";
                }

                if (startChar.equals("#")) {
                    changeTheColor(s.toString().substring(start), start, start + count);
                    hashTagIsComing++;
                }

                if(startChar.equals(" ")){
                    hashTagIsComing = 0;
                }

                if(hashTagIsComing != 0) {
                    changeTheColor(s.toString().substring(start), start, start + count);
                    hashTagIsComing++;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void changeTheColor(String s, int start, int end) {
        mspanable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.quantum_purple)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void setBoard(){
        boardDTO.setPlace_id(autoCompleteTextView.getText().toString());
        boardDTO.setImages(fileUris);
        boardDTO.setRating(Float.toString(ratingBar.getRating()));
        boardDTO.setContext(placeReviewText.getText().toString());
        boardDTO.setTag(tagList);

    }

    private void tagDivide(){
        String tagContext = hashTagText.getText().toString();
        String[] tags = tagContext.split("#");
        Log.d("String",tagContext);

        for(int i=1;i<tags.length;i++){
            tagList.add(tags[i]);
        }
    }


    private void upLoad() { //서버에 게시글 보내기
        String token = "Token cb74383e39311b489f5491bf90bdb1893fa17aeb6f6370fc9f982e3a2d6dff86";
        Retrofit mRetrofit = RestApiUtil.getRetrofitClient(this);
        RestApi restApi = mRetrofit.create(RestApi.class);

        Map<String, RequestBody> boardMaps = new HashMap<>();

        RequestBody requestPlaceID = RequestBody.create(MediaType.parse("text/plain"), boardDTO.getPlace_id());
        RequestBody requestRating = RequestBody.create(MediaType.parse("text/plain"), boardDTO.getRating());
        RequestBody requestContext = RequestBody.create(MediaType.parse("text/plain"), boardDTO.getContext());


//        System.out.println("1 : " + boardDTO.getPlace_id());
//        System.out.println("1 : " + boardDTO.getRating());
//        System.out.println("1 : " + boardDTO.getContext());
        //입력 데이터:  place_id, rating, context, img_url_1 ~ img_url_5, tag_1 ~ tag_5

        boardMaps.put("place_id", requestPlaceID);
        boardMaps.put("rating", requestRating);
        boardMaps.put("context", requestContext);

//        System.out.println("2 : " + boardMaps.get("place_id"));
//        System.out.println("2 : " + boardMaps.get("rating"));
//        System.out.println("2 : " + boardMaps.get("context"));

        ArrayList<RequestBody> RequestBodyTags = new ArrayList<>();
        ArrayList<RequestBody> RequestBodyImages = new ArrayList<>();

//        System.out.println("3 : " + tagList.size());
//        System.out.println("3 : " + files.size());


        for (int i = 0; i < tagList.size(); i++) {
            RequestBodyTags.add(RequestBody.create(MediaType.parse("text/plain"), boardDTO.getTag().get(i)));
//            System.out.println("4 : " + boardDTO.getTag().get(i));

            boardMaps.put("tag_" + (i + 1), RequestBodyTags.get(i));

        }


//        params.put("newProfilePicture" + "\"; filename=\"" + FilenameUtils.getName(file.getAbsolutePath())
//                , RequestBody.create(MediaType.parse("image/jpg"), file));

        for (int i = 0; i < files.size(); i++) {
            RequestBodyImages.add(RequestBody.create(MediaType.parse("image/*"), files.get(i)));
//            boardMaps.put("file\"; filename=\"" + files.get(i).getName() + "\"", RequestBodyImages.get(i));
            boardMaps.put( "img_" + (i+1) + "\"; filename=\"" + files.get(i).getName() , RequestBodyImages.get(i));
//            "url_upload\"; filename=\"photo.png\""

            //System.out.println( "img_url_" + (i+1) + "\"; filename=\"" + files.get(i).getName() + "\"" );
            System.out.println( "img_" + (i+1) + "\"; filename=\"" + files.get(i).getName());
        }

        System.out.println("게시글 업로드 시작");


        Call<BoardResponseDTO> call = restApi.uploadBoard(token, boardMaps);
        call.enqueue(new Callback<BoardResponseDTO>() {
            @Override
            public void onResponse(Call<BoardResponseDTO> call, Response<BoardResponseDTO> response) {
                //if(response.isSuccessful()) {
                    System.out.println(response.isSuccessful());
                    BoardResponseDTO boardResponseDTO = response.body();
                    System.out.println(boardResponseDTO.getCheck());
                    System.out.println("게시글 업로드 성공");
                    Toast.makeText(AddBoardActivity.this, "게시글 업로드 성공!!", Toast.LENGTH_LONG).show();
                //}
            }

            @Override
            public void onFailure(Call<BoardResponseDTO> call, Throwable t) {
                System.out.println(t.getMessage());
                System.out.println("게시글 업로드 실패");
                Toast.makeText(AddBoardActivity.this, "게시글 업로드 실패!!", Toast.LENGTH_LONG).show();
            }
        });

    }


}
