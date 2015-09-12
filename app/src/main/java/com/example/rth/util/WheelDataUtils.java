package com.example.rth.util;

import android.util.Log;

import com.example.rth.data.WheelData;
import com.example.rth.moneyrecord.R;

import java.util.ArrayList;
import java.util.Calendar;
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
    public static Map<Integer,List<WheelData>> mapSecondIn = new HashMap<>();
    static {
        firstCategoryIn.add(new WheelData("辛苦自赚"));
        List<WheelData> subInFirst = new ArrayList<>();
        subInFirst.add(new WheelData("兼职收入", R.mipmap.part_jod));
        subInFirst.add(new WheelData("奖学金", R.mipmap.jiangxuejin));
        subInFirst.add(new WheelData("投资收入", R.mipmap.touzi));
        mapSecondIn.put(0, subInFirst);
        firstCategoryIn.add(new WheelData("空手套白狼"));
        List<WheelData> subInSecond = new ArrayList<>();
        subInSecond.add(new WheelData("礼金收入", R.mipmap.lijin));
        subInSecond.add(new WheelData("中奖收入", R.mipmap.zhongjiang));
        subInSecond.add(new WheelData("意外收入", R.mipmap.jianqian));
        subInSecond.add(new WheelData("爸妈给钱", R.mipmap.shenshou));
        mapSecondIn.put(0, subInSecond);
    }

    //支出的一级目录
    public static List<WheelData> firstCategoryOut = new ArrayList<>();
    // 支出的二级
    public static Map<Integer,List<WheelData>> mapSecondOut = new HashMap<>();
    static {
        firstCategoryOut.add(new WheelData("衣服饰品"));
        List<WheelData> subOutOne = new ArrayList<>();
        subOutOne.add(new WheelData("衣服裤子",R.mipmap.clouse));
        subOutOne.add(new WheelData("鞋帽包包",R.mipmap.shoes));
        subOutOne.add(new WheelData("其他饰品", R.mipmap.shipin));
        mapSecondOut.put(0, subOutOne);
        firstCategoryOut.add(new WheelData("吃吃喝喝"));
        List<WheelData> subOutTwo = new ArrayList<>();
        subOutTwo.add(new WheelData("早午晚餐", R.mipmap.jianqian));
        subOutTwo.add(new WheelData("咖啡饮料", R.mipmap.jianqian));
        subOutTwo.add(new WheelData("水果零食", R.mipmap.jianqian));
        mapSecondOut.put(1, subOutTwo);
        firstCategoryOut.add(new WheelData("行车交通"));
        List<WheelData> subOutThree = new ArrayList<>();
        subOutThree.add(new WheelData("公共交通", R.mipmap.jianqian));
        subOutThree.add(new WheelData("打车租车", R.mipmap.jianqian));
        mapSecondOut.put(2, subOutThree);
        firstCategoryOut.add(new WheelData("交流通讯"));
        List<WheelData> subOutFour = new ArrayList<>();
        subOutFour.add(new WheelData("手机费",R.mipmap.jianqian));
        subOutFour.add(new WheelData("流量费", R.mipmap.jianqian));
        subOutFour.add(new WheelData("邮寄费", R.mipmap.jianqian));
        mapSecondOut.put(3, subOutFour);
        firstCategoryOut.add(new WheelData("休闲娱乐"));
        List<WheelData> subOutFive = new ArrayList<>();
        subOutFive.add(new WheelData("运动器材",R.mipmap.jianqian));
        subOutFive.add(new WheelData("观影游玩", R.mipmap.jianqian));
        subOutFive.add(new WheelData("宠物宝贝", R.mipmap.jianqian));
        mapSecondOut.put(4, subOutFive);
        firstCategoryOut.add(new WheelData("学习进修"));
        List<WheelData> subOutSix = new ArrayList<>();
        subOutSix.add(new WheelData("书报杂志",R.mipmap.jianqian));
        subOutSix.add(new WheelData("培训进修", R.mipmap.jianqian));
        subOutSix.add(new WheelData("电子设备", R.mipmap.jianqian));
        mapSecondOut.put(5, subOutSix);
        firstCategoryOut.add(new WheelData("医疗保健"));
        List<WheelData> subOutSeven = new ArrayList<>();
        subOutSeven.add(new WheelData("药品费",R.mipmap.jianqian));
        subOutSeven.add(new WheelData("运动健身", R.mipmap.jianqian));
        subOutSeven.add(new WheelData("治疗费", R.mipmap.jianqian));
        subOutSeven.add(new WheelData("美容费", R.mipmap.jianqian));
        mapSecondOut.put(6, subOutSeven);
        firstCategoryOut.add(new WheelData("意外事项"));
        List<WheelData> subOutEight = new ArrayList<>();
        subOutEight.add(new WheelData("生日礼物",R.mipmap.jianqian));
        subOutEight.add(new WheelData("换人钱财", R.mipmap.jianqian));
        subOutEight.add(new WheelData("慈善捐助", R.mipmap.jianqian));
        subOutEight.add(new WheelData("意外丢失", R.mipmap.jianqian));
        mapSecondOut.put(7, subOutEight);
    }

    //年份
    public static List<WheelData> YEAR = new ArrayList<>();
    public static void getYears() {
        YEAR.clear();
        int currentYear = Utils.getCurrentYear();
        for(int i = currentYear - 10;i <= currentYear;i++) {
            YEAR.add(new WheelData(i + "年"));
        }
        for(int i = currentYear + 1;i<= currentYear + 10;i++) {
            YEAR.add(new WheelData(i + "年"));
        }
    }

    //年份
    public static List<WheelData> MONTH = new ArrayList<>();
    static {
        for(int i = 1;i<= 12;i++) {
            MONTH.add(new WheelData(i + "月"));
        }
    }

    //日
    private static final int[] dayNums = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static List<WheelData> DAY = new ArrayList<>();
    public static void getDay(int year,int month) {
        if(year % 4 == 0||year%400==0) {
            //润年
            dayNums[1] = 29;
        }
        DAY.clear();
        for(int i = 1;i <= dayNums[month-1];i++) {
            DAY.add(new WheelData(i + "号"));
        }
    }

    /**
     * 小时
     */
    public static List<WheelData> HOURS = new ArrayList<>();
    static {
        for (int i = 0;i < 24;i++) {
            HOURS.add(new WheelData(i+""));
        }
    }

    /**
     * 分钟
     */
    public static List<WheelData> MINIITE = new ArrayList<>();
    static {
        for (int i = 0;i < 60;i++) {
            MINIITE.add(new WheelData(i+""));
        }
    }
}
