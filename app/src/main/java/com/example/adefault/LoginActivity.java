package com.example.adefault;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adefault.data.LoginRepository;
import com.example.adefault.data.UserLoginRepository;
import com.example.adefault.manager.AppManager;
import com.example.adefault.manager.ImageManager;
import com.example.adefault.model.LoginDTO;
import com.example.adefault.model.LoginResponseDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private UserLoginRepository mUserLoginRepository;
    //private final LoginRepository mLoginRepository;  //interface //presenter 파트에 있어야 할 부분
    RegisterFragment registerFragment;
    LoginDTO loginDTO;

    EditText et_email;
    EditText et_password;
    Button btn_Login;
    TextView tv_Register;

    ConfirmDialog confirmDialog;

    private final static int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getInstance().setContext(this); //singleTon pattern
        AppManager.getInstance().setResources(getResources());
        setContentView(R.layout.activity_login);

        initView();
        initListener();

        checkPermission(); // 갤러리 접근 권한
    }

    void initView() { //view part
        btn_Login = findViewById(R.id.button_Login);
        tv_Register = findViewById(R.id.textView_register);
        et_email = findViewById(R.id.editText_email);
        et_password = findViewById(R.id.editText_password);

        confirmDialog = new ConfirmDialog(AppManager.getInstance().getContext());
    }

    void initListener() { //view part
        btn_Login.setOnClickListener(this);
        tv_Register.setOnClickListener(this);
    }

    public void onClick(View view) { //view part
        switch (view.getId()) {
            case R.id.button_Login: //아이디 비밀번호를 받아와서 서버와 통신한다
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                loginDTO = new LoginDTO();
                loginDTO.setUsername(email);
                loginDTO.setPassword(password);

                progressON("로그인 중...");
                login(loginDTO);

                break;

            case R.id.textView_register:  //회원가입 화면 띄우기
                registerFragment = RegisterFragment.newInstance();
                registerFragment.show(getSupportFragmentManager(), null);
                break;
        }
    }

    public void login(LoginDTO loginDTO) { //presenter part //LoginRepository 로 수행했어야 함
        final ConfirmDialog confirmDialog = new ConfirmDialog(AppManager.getInstance().getContext());

        mUserLoginRepository = new UserLoginRepository(loginDTO);

        if(mUserLoginRepository.isAvailable()) {
            mUserLoginRepository.getLoginData(new Callback<LoginResponseDTO>() {
                @Override
                public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                    //progressOFF();
                    LoginResponseDTO loginResponseDTO = response.body();
                    setUser(loginResponseDTO.getUser(), loginResponseDTO.getToken());
                    Intent intent = new Intent(AppManager.getInstance().getContext(),MainActivity.class);
                    startActivity(intent);
                    progressOFF();
                }

                @Override
                public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                    progressOFF();
                    confirmDialog.setMessage("로그인 정보가 잘못되었습니다.");
                    confirmDialog.show();
                    //showLoadError(t.getLocalizedMessage());  //mView.showLoadError(t.getLocalizedMessage());
                }
            });
        }
        else {
            progressOFF();
            confirmDialog.setMessage("이메일 혹은 비밀번호를 확인해주세요");
            confirmDialog.show();
        }
    }

//    private void showLoadError(String message) {
//        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//    }

//    private void showLoadError(String message) {
//        Toast.makeText(AppManager().getInstance().getContext(), message, Toast.LENGTH_SHORT).show();
//    }

    public void setUser(String user, String token) {
        AppManager.getInstance().getLoginResponseDTO().setUser(user);
        AppManager.getInstance().getLoginResponseDTO().setToken(token);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AppManager.getInstance().onBackPressed();
    }

    public void progressON(String message) {
        ImageManager.getInstance().progressON((Activity)AppManager.getInstance().getContext(), message);
    }

    public void progressOFF() {
        ImageManager.getInstance().progressOFF();
    }

    //갤러리 접근 권한 설정
    private void checkPermission() {

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_READ_EXTERNAL_STORAGE);

            Log.v("갤러리 권한", "갤러리 사용을 위해 권한이 필요합니다.");
        }

    }


}
