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
        requestQueue = Volley.newRequestQueue(this);
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
    private RequestQueue requestQueue;
    private void createRcord() {
        Intent intent = new Intent(MainActivity.this,CreateRecordActivity.class);
        startActivity(intent);



        ///////////////测试后台接口
//        String url = "http://1.moneyrecord.sinaapp.com/api/saverecord.php";
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                if(s != null) {
//                    Log.e("onResponse",s);
//                }else {
//                    Log.e("onResponse","null data");
//                }
//            }
//        },new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Log.e("onErrorResponse",volleyError.getMessage());
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("uid", 1+"");
//                params.put("main_title", "行车交通");
//                params.put("sub_title", "打车租车");
//                params.put("icon_id", R.mipmap.jiangxuejin+"");
//                params.put("money", "100.34");
//                params.put("remark", "是的是的的是的时间的还是");
//                params.put("year",2015+"");
//                params.put("month", 9+"");
//                params.put("date", 11+"");
//                params.put("time", "12:34");
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
//        Map<String, String> params = new HashMap<>();
//        params.put("uid", 1+"");
//        params.put("main_title", "行车交通");
//        params.put("sub_title", "打车租车");
//        params.put("icon_id", R.mipmap.jiangxuejin+"");
//        params.put("money", "100.34");
//        params.put("remark", "是的是的的是的时间的还是");
//        params.put("year",2015+"");
//        params.put("month", 9+"");
//        params.put("date", 11+"");
//        params.put("time", "12:34");
//        JSONObject object = new JSONObject(params);
//        Log.e("jsonObject", object.toString());
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        if(jsonObject != null) {
//                            Log.e("onResponse",jsonObject.toString());
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Log.e("onErrorResponse",volleyError.getMessage());
//            }
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                return super.getHeaders();
//            }
//        };
//        requestQueue.add(jsonObjectRequest);
    }
}
