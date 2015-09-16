package com.example.rth.moneyrecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.rth.fragments.LoginFragment;
import com.example.rth.util.Utils;

/**
 * Created by rth on 15-9-16.
 */
public class StartActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initView();
    }

    private void initView() {
        //判断用户有没有登录
        boolean isLogin = (boolean)Utils.getUserInfo(1,this);
        if(isLogin) {
            //直接到主界面
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            //登录界面
            Fragment fragment = new LoginFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_start_container,fragment)
                    .commit();
        }
    }
}
