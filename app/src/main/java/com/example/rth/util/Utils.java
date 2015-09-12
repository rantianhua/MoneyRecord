package com.example.rth.util;

import android.content.Context;
import android.util.Log;

import com.example.rth.data.MoneyRecord;
import com.example.rth.data.WheelData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by rth on 15-9-11.
 */
public class Utils {

    private static int currentHours;

    /**
     * 像素转换为dp
     * @param context
     */
    public static float pixelToDp(Context context, int val) {
        float density = context.getResources().getDisplayMetrics().density;
        return val * density;
    }

    /**
     * 得到当前的年份
     * @return
     */
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 得到当前的月份
     * @return
     */
    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 得到当前的日期
     * @return
     */
    public static int getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前的分钟
     * @return
     */
    public static int getCurrentMinite() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取当期的时间
     * @return
     */
    public static int getCurrentHours() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    //建立的账单
    public static List<MoneyRecord> RECORDS = new ArrayList<>();
}
