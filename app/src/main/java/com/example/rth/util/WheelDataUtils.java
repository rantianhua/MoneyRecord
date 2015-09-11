package com.example.rth.util;

import com.example.rth.data.WheelData;
import com.example.rth.moneyrecord.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rth on 15-9-11.
 */
public class WheelDataUtils {

    //收入的一级目录
    public static List<WheelData> firstCategoryIn = new ArrayList<>();
    // 收入的二级
    public static Map<Integer,List<WheelData>> mapFirstIn = new HashMap<>();
    static {
        firstCategoryIn.add(new WheelData("辛苦自赚"));
        List<WheelData> subInFirst = new ArrayList<>();
        subInFirst.add(new WheelData("兼职收入", R.mipmap.part_jod));
        subInFirst.add(new WheelData("奖学金", R.mipmap.jiangxuejin));
        subInFirst.add(new WheelData("投资收入", R.mipmap.touzi));
        mapFirstIn.put(0, subInFirst);
        firstCategoryIn.add(new WheelData("空手套白狼"));
        List<WheelData> subInSecond = new ArrayList<>();
        subInSecond.add(new WheelData("礼金收入", R.mipmap.lijin));
        subInSecond.add(new WheelData("中奖收入", R.mipmap.zhongjiang));
        subInSecond.add(new WheelData("意外收入", R.mipmap.jianqian));
        subInSecond.add(new WheelData("爸妈给钱", R.mipmap.shenshou));
        mapFirstIn.put(0, subInSecond);
    }

    //支出的一级目录
    public static List<WheelData> firstCategoryOut = new ArrayList<>();
    // 支出的二级
    public static Map<Integer,List<WheelData>> mapSecondOut = new HashMap<>();
    static {
        firstCategoryOut.add(new WheelData("衣服饰品"));
        firstCategoryOut.add(new WheelData("吃吃喝喝"));
        firstCategoryOut.add(new WheelData("行车交通"));
        firstCategoryOut.add(new WheelData("吃吃喝喝"));
    }


}
