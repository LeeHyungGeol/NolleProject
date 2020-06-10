package com.example.adefault;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.adefault.manager.AppManager;

public class ServiceChooseActivity extends AppCompatActivity {

    private ImageView iv_food_service;
    private ImageView iv_play_service;
    private ImageView iv_clothes_service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getInstance().setContext(this); //singleTon pattern
        AppManager.getInstance().setResources(getResources());

        setContentView(R.layout.activity_service_choose);

        init();
        initListener();
    }

    public void init() {
        iv_food_service = findViewById(R.id.food_service);
        iv_play_service = findViewById(R.id.play_service);
        iv_clothes_service = findViewById(R.id.clothes_service);
    }

    public void initListener() {
        iv_food_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppManager.getInstance().getContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
