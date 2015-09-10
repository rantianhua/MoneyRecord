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
import com.example.rth.adapter.HomeListAdapter;
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
    private List<MoneyRecord> records = new ArrayList<>();  //ListView的数据源
    private HomeListAdapter adapter;    //listview的内容适配器

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addDefDatas();
    }

    /**
     * 先将默认的“本周”和“本月”两项数据加入数据源
     */
    private void addDefDatas() {
        MoneyRecord recordM = new MoneyRecord();
        recordM.title = "本月";
        recordM.date = "9月1日-9月7号";
        recordM.moneyIn = "3434.65";
        recordM.moneyOut = "67.33";
        recordM.type = MoneyRecord.TYPE_WEEK_OR_MONTH;
        records.add(0,recordM);
        MoneyRecord recordW = new MoneyRecord();
        recordW.title = "本周";
        recordW.date = "9月1号-9月7号";
        recordW.moneyIn = "3.32";
        recordW.moneyOut = "67.33";
        recordW.remark = "地方地方";
        recordW.type = MoneyRecord.TYPE_WEEK_OR_MONTH;
        records.add(recordW);

        MoneyRecord recordN = new MoneyRecord();
        recordN.title = "吃和玩乐";
        recordN.date = "2015年9月7号";
        recordN.moneyOut = "167.33";
        recordN.remark = "请同学吃饭";
        recordN.type = MoneyRecord.TYPE_NOW_RECORD;
        records.add(0,recordN);

        MoneyRecord recordH = new MoneyRecord();
        recordH.title = "吃和玩乐";
        recordH.date = "2015年9月7号";
        recordH.moneyIn = "17.33";
        recordH.remark = "请同学吃饭";
        recordH.type = MoneyRecord.TYPE_RECORD_LIST;
        records.add(0,recordH);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        listView = (ListView) view.findViewById(R.id.frag_home_listview_record);
        adapter = new HomeListAdapter(getActivity());
        adapter.updateDatas(records);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    protected void initEvent(View view) {
    }

}
