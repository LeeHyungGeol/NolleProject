package com.example.adefault;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.adefault.data.RegisterRepository;
import com.example.adefault.data.UserRegisterRepository;
import com.example.adefault.manager.AppManager;
import com.example.adefault.manager.ImageManager;
import com.example.adefault.model.LoginResponseDTO;
import com.example.adefault.model.RegisterResponseDTO;
import com.example.adefault.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends DialogFragment implements View.OnClickListener, View.OnFocusChangeListener{

    User mUser;
    private UserRegisterRepository mUserRegisterRepository;
//    private final RegisterRepository mRegisterRepository;

    private ScrollView scrollView;

    private ImageView iv_profileImage;

    private EditText et_email;
    private EditText et_password;
    private EditText et_rePassword;
    private EditText et_nickName;
    private EditText et_name;
    private EditText et_age;
    private EditText et_sex;

    private Button btn_register;
    private Button btn_imageSet;

    private ConfirmDialog confirmDialog;

    public RegisterFragment() {} //기본 생성자 반드시 필요함


    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    @Nullable
    @Override  //layout 을 inflate 하는 곳, View 객체를 얻어서 초기화
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

//        imageSetting = false;

        initView(view);
        initListener();

        return view;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new Dialog(AppManager.getInstance().getContext(), getTheme()) { //singleton pattern
            @Override
            public void onBackPressed() {
                dismiss();
            }
        };
    }

    @Override
    public void onStart() { //Fragment 화면에 표시될 때 호출
        super.onStart();

        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void initView(View view) {
        scrollView = (ScrollView) view.findViewById(R.id.sv_root);

        iv_profileImage = view.findViewById(R.id.imageView_profileImage);

        et_email = view.findViewById(R.id.editText_register_email);
        et_password = view.findViewById(R.id.editText_register_password);
        et_rePassword = view.findViewById(R.id.editText_register_password_again);
        et_name = view.findViewById(R.id.editText_register_name);
        et_nickName = view.findViewById(R.id.editText_register_nickName);
        et_sex = view.findViewById(R.id.editText_register_sex);
        et_age = view.findViewById(R.id.editText_register_age);

        btn_imageSet = view.findViewById(R.id.button_profileImageSet);
        btn_register = view.findViewById(R.id.button_register_signUp);

    }

    public void initListener() {
        et_email.setOnClickListener(this);
        et_password.setOnClickListener(this);
        et_rePassword.setOnClickListener(this);
        et_nickName.setOnClickListener(this);
        et_age.setOnClickListener(this);
        et_sex.setOnClickListener(this);

        et_email.setOnFocusChangeListener(this);
        et_password.setOnFocusChangeListener(this);
        et_rePassword.setOnFocusChangeListener(this);
        et_nickName.setOnFocusChangeListener(this);
        et_age.setOnFocusChangeListener(this);
        et_sex.setOnFocusChangeListener(this);

        btn_imageSet.setOnClickListener(this);
        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_profileImageSet:
                //doTakeAlbumAction();
                break;
            case R.id.button_register_signUp:
                progressON("아이디 생성 중...");

                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                String rePassword = et_rePassword.getText().toString();
                String nickName = et_nickName.getText().toString();
                String age = et_age.getText().toString();
                String name = et_name.getText().toString();
                String sex = et_sex.getText().toString();

                mUser = new User();
                mUser.setUser_email(email);
                mUser.setPassword(password);
                mUser.setNickname(nickName);
                mUser.setUser_nm(name);
                mUser.setAge(age);
                mUser.setSex(sex);

                register(mUser);
                break;

            case R.id.editText_email:
            case R.id.editText_password:
            case R.id.editText_register_password_again:
            case R.id.editText_register_nickName:
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.scrollTo(0, scrollView.getBottom());
                    }
                }, 500);
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()) {
            case R.id.editText_email:
            case R.id.editText_password:
            case R.id.editText_register_password_again:
            case R.id.editText_register_nickName:
                if (hasFocus) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.scrollTo(0, scrollView.getBottom());
                        }
                    }, 500);
                }
        }
    }

    public void register(User user) {
        confirmDialog = new ConfirmDialog(AppManager.getInstance().getContext());
        mUserRegisterRepository = new UserRegisterRepository(user);

        if(mUserRegisterRepository.isAvailable()) {
            mUserRegisterRepository.getRegisterData(new Callback<RegisterResponseDTO>() {
                @Override
                public void onResponse(Call<RegisterResponseDTO> call, Response<RegisterResponseDTO> response) {
                    if(response.isSuccessful()) {
                        progressOFF();
                        confirmDialog.setMessage("회원가입 완료!");
                        confirmDialog.show();
                        dismiss();
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponseDTO> call, Throwable t) {
                    System.out.println(t.getMessage());
                    progressOFF();
                    confirmDialog.setMessage("아이디는 6글자 이상, 비밀번호는 4글자 이상이여야합니다.");
                    confirmDialog.show();
                }
            });
        }
    }


    public void progressON(String message) {
        ImageManager.getInstance().progressON((Activity)AppManager.getInstance().getContext(), message);
    }
    public void progressOFF() {
        ImageManager.getInstance().progressOFF();
    }


}
