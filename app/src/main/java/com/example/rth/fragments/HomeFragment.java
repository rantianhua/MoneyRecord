package com.example.rth.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rth.BaseHomeFragment;
import com.example.rth.adapter.HomeListAdapter;
import com.example.rth.data.MoneyRecord;
import com.example.rth.data.WheelData;
import com.example.rth.moneyrecord.CreateRecordActivity;
import com.example.rth.moneyrecord.R;
import com.example.rth.util.Constants;
import com.example.rth.util.Utils;
import com.example.rth.util.WheelDataUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rth on 15-9-9.
 * 主页面内容
 */

public class HomeFragment extends BaseHomeFragment{

    private ListView listView;  //展示今天的账单，以及本周、本月的总账单
    private List<MoneyRecord> records = new ArrayList<>();  //本页面ListView的数据源
    private HomeListAdapter adapter;    //listview的内容适配器
    private List<MoneyRecord> recordsDetail;  //详情页的ListView的数据源

    private String urlMonthMoney;   //得到一个月内之处与收入的接口
    private String urlWeekMonthMon; //得到一个周内的账单
    private String mIn,mOut,wIn,wOut;    //周、月的收入和支出
    private RequestQueue requestQueue;  //请求队列

    private TextView tvMoneyIn; //收入
    private TextView tvMoneyOut; //支出

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getActivity());
        addDefDatas();
    }

    /**
     * 先将默认的“本周”和“本月”两项数据加入数据源
     */
    private void addDefDatas() {
        int weekDay = Utils.getCurrentWeekDay();
        StringBuilder builder = new StringBuilder();
        int month = Utils.getCurrentMonth()+1;
        int year = Utils.getCurrentYear();
        int uid = (int)Utils.getUserInfo(2,getActivity());
        urlMonthMoney = Constants.getMonthMoney(uid,year,month);
        builder.append(month).append("月").append("1号").append("-").append(month).append("月")
                .append(WheelDataUtils.dayNums[month - 1])
                .append("号");
        MoneyRecord recordM = new MoneyRecord(null,"本月","","","",
                "","0.00","0.00","",R.mipmap.month+"",MoneyRecord.TYPE_WEEK_OR_MONTH);
        recordM.setShowDateTime(builder.toString());
        builder.delete(0, builder.length());
        records.add(0, recordM);
        int date = Utils.getCurrentDay();
        int startDate=1,endDate=1;
        switch (weekDay) {
            case 0: //周一
                startDate = date;
                endDate = date+6;
                break;
            case 1:
                startDate = date-1;
                endDate = date+5;
                break;
            case 2:
                startDate = date-2;
                endDate = date+4;
                break;
            case 3:
                startDate = date-3;
                endDate = date+3;
                break;
            case 4:
                startDate = date-4;
                endDate = date+2;
                break;
            case 5:
                startDate = date-5;
                endDate = date+1;
                break;
            case 6: //周天
                startDate = date-6;
                endDate = date;
                break;
        }
        urlWeekMonthMon = Constants.getWeekRecordApi(uid,year,month,startDate,endDate);
        builder.append(month).append("月").append(startDate).append("号").append("-")
                .append(month).append("月").append(endDate).append("号");
        MoneyRecord recordW = new MoneyRecord(null,"本周","","","",
                "","0.00","0.00","",R.mipmap.week+"",MoneyRecord.TYPE_WEEK_OR_MONTH);
        recordW.setShowDateTime(builder.toString());
        builder.delete(0,builder.length());
        records.add(recordW);

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        listView = (ListView) view.findViewById(R.id.frag_home_listview_record);
        adapter = new HomeListAdapter(getActivity());
        adapter.updateDatas(records);
        listView.setAdapter(adapter);
        tvMoneyIn = (TextView) view.findViewById(R.id.frag_home_tv_money_in);
        tvMoneyOut = (TextView) view.findViewById(R.id.frag_home_tv_money_out);
        return view;
    }

    @Override
    protected void initEvent(View view) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == records.size() - 1 || i == records.size() - 2) {

                } else {
                    MoneyRecord record = (MoneyRecord) adapter.getItem(i);
                    Intent intent = new Intent(getActivity(), CreateRecordActivity.class);
                    intent.putExtra("record",record);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        updateCacheData();
        updateData();
    }

    /**
     * 将新增数据更新在界面上
     */
    private void updateCacheData() {
        if(Utils.RECORDS.size() > 0) {
            records.addAll(0,Utils.RECORDS);
            adapter.updateDatas(records);
            Utils.RECORDS.clear();
        }else {
            adapter.updateDatas(records);
        }
    }

    /**
     * 从后台抓取数据
     */
    private void updateData() {
        //加载一个月的金额数和一个周的账单
        Log.e("url" ,urlMonthMoney);
        StringRequest requestMonthMoney = new StringRequest(Request.Method.GET, urlMonthMoney, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s != null) {
                    getMonthMoney(s);
                }else {
                    Utils.showToast("未获取到本月记录",getActivity());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Utils.showToast("出错了，请检查网络",getActivity());
            }
        });
        //加载一个周的账单
        StringRequest requestWeekRecords = new StringRequest(Request.Method.GET, urlWeekMonthMon, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s != null) {
                    getWeekRecords(s);
                }else {
                    Utils.showToast("获取本周数据失败",getActivity());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Utils.showToast("出错了，请检查网络",getActivity());
            }
        });
        requestQueue.add(requestMonthMoney);
        requestQueue.add(requestWeekRecords);
    }

    /**
     * 获取后台返回的本周数据
     * @param s
     */
    private void getWeekRecords(String s) {
        recordsDetail = Utils.getRecordsFromJson(s);
        //从记录中获取收入和之处
        float in = 0.00f,out = 0.00f;
        for(int i = 0;i < recordsDetail.size();i++) {
            in += Float.valueOf(recordsDetail.get(i).moneyIn);
            out += Float.valueOf(recordsDetail.get(i).moneyOut);
        }

        wIn = in == 0 ? "0.00" : String.valueOf(in);
        wOut = out == 0 ? "0.00" : String.valueOf(out);
        wIn.replaceAll("\\+", "");
        wOut.replaceAll("-","");
        MoneyRecord recordW = records.get(records.size()-1);
        recordW.moneyIn = wIn;
        recordW.moneyOut = wOut;
        adapter.updateDatas(records);
    }

    /**
     * 得到后台返回的一个月的收入与支出
     * @param s
     */
    private void getMonthMoney(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            String msg = jsonObject.getString("msg");
            if(msg.equals("ok")) {
                //获取成功
                mIn = jsonObject.getString("money_in");
                mOut = jsonObject.getString("money_out");
                mIn.replaceAll("\\+", "");
                mOut.replaceAll("-","");
                MoneyRecord recordM =  records.get(records.size()-2);
                recordM.moneyOut = mOut;
                recordM.moneyIn = mIn;
                adapter.updateDatas(records);
                tvMoneyIn.setText("总收入: " + mIn);
                tvMoneyOut.setText("总支出: " + mOut);
            }else {
                //Utils.showToast("获取本月金额信息失败",getActivity());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
