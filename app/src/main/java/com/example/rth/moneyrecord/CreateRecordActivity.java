package com.example.rth.moneyrecord;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.rth.fragments.CreateRecordFragment;

/**
 * Created by rth on 15-9-11.
 */
public class CreateRecordActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        Fragment fragment = new CreateRecordFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_create_rl_container,fragment)
                .commit();
    }
}
