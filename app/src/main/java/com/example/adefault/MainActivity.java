package com.example.adefault;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.adefault.Decoration.OnBackPressedListener;
import com.example.adefault.FeedPost.FollowFeedFragment;
import com.example.adefault.manager.AppManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private CustomActionBar ca;
    private HomeFragment homefragment;
    private FollowFeedFragment menufragment;
    private MyPageFragment mypagefragment;
    private SearchFragment searchfragment;
    private Menu mMenu;
    private OnBackPressedListener listener;

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
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    ca.getLogo().setVisibility(View.VISIBLE);
                    setFrag(1);
                    break;
                case R.id.action_add:
                    setFrag(2);
                    break;
                case R.id.action_search:
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    ca.getLogo().setVisibility(View.GONE);
                    setFrag(3);
                    break;
                case R.id.action_person:
                    setFrag(4);
                    break;


            }
            return true;
        });

        homefragment = new HomeFragment();
        menufragment = new FollowFeedFragment();
        searchfragment = new SearchFragment();
        mypagefragment = new MyPageFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.Main_Frame, homefragment).commit();
        //setFrag(0); // 첫 프래그먼트 화면 지정

        AppManager.getInstance().setMainActivity(this);

    }

    private void setActionBar() {
        ca = new CustomActionBar(this, getSupportActionBar());
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
                ft.remove(searchfragment);
                searchfragment = new SearchFragment();
                ft.replace(R.id.Main_Frame,searchfragment);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.Main_Frame, mypagefragment);
                ft.commit();
                break;
        }
    }

    public void showOptionMenu(boolean isShow) {
        if (mMenu == null) {
            return;
        }
        mMenu.setGroupVisible(R.id.search_menu_group, isShow);
    }

    public void setOnBackPressedListener(OnBackPressedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBackPressed() {
        if (listener != null) {
            listener.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}