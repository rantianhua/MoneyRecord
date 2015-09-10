package com.example.rth.moneyrecord;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.rth.fragments.HomeFragment;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        Fragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_frame_container,fragment)
                .commit();
    }


}
