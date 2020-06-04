package com.example.adefault;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.adefault.model.MyPageEditResponseDTO;
import com.example.adefault.util.RestApiUtil;
import com.example.adefault.util.UserToken;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopUpProfileEdit extends Activity {

    private RestApiUtil mRestApiUtil;
    private CircleImageView editImage;
    private ImagePicker imagePicker;
    public File imgFile;
    public Image image;
    private EditText editNikcName;
    private Button editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up_profile_edit);
        init();
        addListener();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            image = ImagePicker.getFirstImageOrNull(data);
            imagePick(image);
        }
    }

    private void init() {
        mRestApiUtil = new RestApiUtil();
        editImage = findViewById(R.id.EditImage);
        editBtn = findViewById(R.id.editBtn);
        editNikcName = findViewById(R.id.editNickNameText);
        editImage.setImageResource(R.drawable.photography);
        imagePicker = ImagePicker.create(this)
                .single();
    }


    private void addListener() {
        editImage.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker.start();
            }
        });

        editBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, RequestBody> editMap = new HashMap<>();

                if(image!=null &&!editNikcName.getText().toString().equals("")){
                    RequestBody requestImage = RequestBody.create(MediaType.parse("image/*"), imgFile);
                    editMap.put("image\"; filename=\"" + imgFile.getName(), requestImage);
                    RequestBody requestNickName = RequestBody.create(MediaType.parse("text/plain"), editNikcName.getText().toString());
                    editMap.put("nickname",requestNickName);
                }
                else if(image!=null){
                    RequestBody requestImage = RequestBody.create(MediaType.parse("image/*"), imgFile);
                    editMap.put("image\"; filename=\"" + imgFile.getName(), requestImage);
                }
                else if(!editNikcName.getText().toString().equals("")){
                    RequestBody requestNickName = RequestBody.create(MediaType.parse("text/plain"), editNikcName.getText().toString());
                    editMap.put("nickname",requestNickName);
                }
                mRestApiUtil.getApi().mypage_edit("Token "+ UserToken.getToken(),editMap).enqueue(new Callback<MyPageEditResponseDTO>() {
                    @Override
                    public void onResponse(Call<MyPageEditResponseDTO> call, Response<MyPageEditResponseDTO> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"수정되었습니다",Toast.LENGTH_SHORT).show();
                            finish();

                        }else{
                            Toast.makeText(getApplicationContext(),"수정 실패",Toast.LENGTH_SHORT).show();
                            Log.d("PopUpProfileEdit","response실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<MyPageEditResponseDTO> call, Throwable t) {
                        Log.d("PopUpProfileEdit","통신 실패");
                    }
                });


            }
        });


    }

    public void imagePick(Image image) {
        imgFile = new File(image.getPath());
        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        editImage.setImageBitmap(bitmap);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}
