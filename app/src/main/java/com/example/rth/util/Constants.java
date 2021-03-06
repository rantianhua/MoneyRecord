package com.example.rth.util;

/**
 * Created by rth on 15-9-16.
 */
public class Constants {
    public static final String SP_USER = "user";    //存储用户基本信息的文件名
    public static final String USER_NAME = "user_name"; //用户名的键值
    public static final String USER_ON = "user_on"; //用户是否已登录的键值
    public static final String USER_ID = "user_id"; //用户id的键值
    public static final String USER_PIC = "user_pic"; //用户头像的键值
    public static final String USER_PASS = "user_pass"; //用户密码的键值
    public static final int REQUEST_OPEN_GALLERY = 0;  //打开图库的请求代码
    public static final int REQUEST_IMAGE_CPTURE = 1;  //打开相机的请求代码
    public static final String USER_PHOTO_NAME = "user_photo.jpg";  //用户头像的名字
    public static final String REGISTER_API = "http://1.moneyrecord.sinaapp.com/api/register.php";  //用户注册接口
    public static final String SAVERECORD_API = "http://1.moneyrecord.sinaapp.com/api/saverecord.php";  //保存账单的接口
    public static final String LOGIN_API = "http://1.moneyrecord.sinaapp.com/api/login.php";   //登录接口
    public static final String UPDATE_API = "http://1.moneyrecord.sinaapp.com/api/updaterecord.php";    //更新和删除数据的接口

    public static String getMonthMoney(int uid,int year,int month) {
        return "http://1.moneyrecord.sinaapp.com/api/getmonthmoney.php?year="+year+"&month="+month+"&id="+uid;
    }

    public static String getWeekRecordApi(int uid,int year, int month, int startDate, int endDate) {
        return "http://1.moneyrecord.sinaapp.com/api/getrecordbydate.php?year="
            + year + "&month=" + month + "&start_date=" + startDate + "&end_date=" + endDate+"&id="+uid;
    }

}
