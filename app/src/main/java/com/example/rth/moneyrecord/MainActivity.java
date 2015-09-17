package com.example.rth.moneyrecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rth.fragments.HomeFragment;
import com.example.rth.util.Utils;
import com.example.rth.widgets.MyPopupMenus;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends FragmentActivity {

    private MyPopupMenus myPopupMenus;  //弹出菜单
    //菜单事件监听器
    private final MyPopupMenus.PopupMenuListener popupMenuListener = new MyPopupMenus.PopupMenuListener(){
        @Override
        public void onItemClick(int position) {
            switch (position) {
                case 0: //记一笔
                    createRcord();
                    break;
                case 1: //查看图表
                    break;
                case 2: //用户中心
                    break;
            }
        }
    };


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
        myPopupMenus = (MyPopupMenus) findViewById(R.id.activity_main_mypopupmenu);
        myPopupMenus.setListener(popupMenuListener);
    }

    /**
     * 创建一条记录
     */
    private void createRcord() {
        Intent intent = new Intent(MainActivity.this,CreateRecordActivity.class);
        startActivity(intent);
    }
}
