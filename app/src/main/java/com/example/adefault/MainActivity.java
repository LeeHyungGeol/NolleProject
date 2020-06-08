package com.example.adefault;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.adefault.manager.AppManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment homefragment;
    private MenuFragment menufragment;
    private MyPageFragment mypagefragment;
    private SearchFragment searchfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getInstance().setContext(this);
        AppManager.getInstance().setResources(getResources());

        setContentView(R.layout.activity_main);

        setActionBar();

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId())
            {
                case R.id.action_home:
                    setFrag(0);
                    break;
                case R.id.action_menu:
                    setFrag(1);
                    break;
                case R.id.action_add:
                    setFrag(2);
                    break;
                case R.id.action_search:
                    setFrag(3);
                    break;
                case R.id.action_person:
                    setFrag(4);
                    break;


            }
            return true;
        });

        homefragment = new HomeFragment();
        menufragment = new MenuFragment();
        searchfragment = new SearchFragment();
        mypagefragment = new MyPageFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.Main_Frame, homefragment).commit();
        //setFrag(0); // 첫 프래그먼트 화면 지정

        AppManager.getInstance().setMainActivity(this);

    }

    private void setActionBar() {
        CustomActionBar ca = new CustomActionBar(this, getSupportActionBar());
        ca.setActionBar();
    }

    private void setFrag(int n)
    {
        fm = getSupportFragmentManager();
        ft= fm.beginTransaction();

        switch (n)
        {
            case 0:
                ft.replace(R.id.Main_Frame, HomeFragment.newInstance()).commit();
                break;

            case 1:
                ft.replace(R.id.Main_Frame, menufragment);
                ft.commit();
                break;

            case 2:
                Intent intent = new Intent(MainActivity.this, AddBoardActivity.class);
                startActivity(intent);
                break;
            case 3:
                ft.replace(R.id.Main_Frame, searchfragment);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.Main_Frame, mypagefragment);
                ft.commit();
                break;
        }
    }
}