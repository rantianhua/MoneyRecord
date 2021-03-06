package com.example.rth.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rth.data.MoneyRecord;
import com.example.rth.data.WheelData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    /**
     * 获取当前是周几
     * @return
     */
    public static int getCurrentWeekDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    /**
     * 将String形式的日期转换为timestamp
     * @param date
     * @return
     */
    public static long dateToTimestamp(String date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.CHINA);
        try {
            Date d = sf.parse(date);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    //建立的账单
    public static List<MoneyRecord> RECORDS = new ArrayList<>();

    /**
     *  显示toast信息
     * @param message 要显示的信息
     * @param con 上下文环境
     */
    public static void showToast(String message, Context con) {
        Toast.makeText(con,message,Toast.LENGTH_SHORT).show();
    }


    /**
     * 得到用户基本信息
     * @param flag  0表示返回用户名
     *              1表示返回用户是否已经登录
     *              2返回用户id
     * @return
     */
    public static Object getUserInfo(int flag,Context con) {
        SharedPreferences sp = con.getSharedPreferences(Constants.SP_USER,0);
        Object oj = null;
        switch (flag) {
            case 0: //返回用户名
                oj = sp.getString(Constants.USER_NAME,null);
                break;
            case 1:
                oj = sp.getBoolean(Constants.USER_ON,false);
                break;
            case 2:
                oj = sp.getInt(Constants.USER_ID,0);
                break;
        }
        return oj;
    }

    /**
     * 更新用户信息
     * @param id 用户id
     * @param name  用户名
     * @param picPhoto  图片url
     * @param pass  密码
     * @param on    是否在线
     */
    public static void updateUserInfo(Context con,int id,String name,String picPhoto,String pass,boolean on) {
        SharedPreferences sp = con.getSharedPreferences(Constants.SP_USER, 0);
        SharedPreferences.Editor edit = sp.edit();
        if(id != -1) {
            edit.putInt(Constants.USER_ID, id);
        }
        if(name != null) {
            edit.putString(Constants.USER_NAME, name);
        }
        if(picPhoto != null) {
            edit.putString(Constants.USER_PIC,picPhoto);
        }
        if(pass != null) {
            edit.putString(Constants.USER_PASS,pass);
        }
        edit.putBoolean(Constants.USER_ON,on);
        edit.apply();
    }

    /**
     * 从json数组中获取账单
     * @param json  //json数组的字符串格式
     * @return
     */
    public static List<MoneyRecord> getRecordsFromJson(String json) {
        List<MoneyRecord> records = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0;i < jsonArray.length();i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String money = object.getString("_money");
                String moneyIn = "0.00";
                String moneyOut = "0.00";
                if(Float.valueOf(money) > 0) {
                    moneyIn = money;
                }else {
                    moneyOut = money;
                }
                MoneyRecord record = new MoneyRecord(object.getString("_main_title"),object.getString("_sub_title"),
                        object.getString("_year"),object.getString("_month"),object.getString("_date"),
                        object.getString("_time"),moneyOut,moneyIn,object.getString("_remark"),
                        object.getString("_icon_id"),MoneyRecord.TYPE_RECORD_LIST);
                records.add(record);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }
}
