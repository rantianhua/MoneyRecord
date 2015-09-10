package com.example.rth.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.rth.BaseHomeFragment;
import com.example.rth.data.MoneyRecord;
import com.example.rth.moneyrecord.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rth on 15-9-9.
 * 主页面内容
 */

public class HomeFragment extends BaseHomeFragment{

    private ListView listView;  //展示今天的账单，以及本周、本月的总账单
    private List<MoneyRecord> records = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addDefDatas();
    }

    /**
     * 先将默认的“本周”和“本月”两项数据加入数据源
     */
    private void addDefDatas() {
        MoneyRecord recordW = new MoneyRecord();
        recordW.title = "本周";
        recordW.date = "2015年9月1日-2015年9月7号";
        recordW.moneyIn = "3.32";
        recordW.moneyOut = "67.33";
        records.add(0,recordW);
        MoneyRecord recordM = new MoneyRecord();
        recordM.title = "本月";
        recordM.date = "2015年9月1日-2015年9月7号";
        recordM.moneyIn = "3434.65";
        recordM.moneyOut = "67.33";
        records.add(0,recordM);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        listView = (ListView) view.findViewById(R.id.frag_home_listview_record);
        return view;
    }

    @Override
    protected void initEvent(View view) {
    }

}
