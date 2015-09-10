package com.example.rth.data;

/**
 * Created by rth on 15-9-10.
 */
public class MoneyRecord {
    public String title;    //账单标题
    public String date; //账单日期
    public String moneyOut;    //花销
    public String moneyIn;  //收入
    public String remark;   //备注
    public int type;    //账单类型

    public static final int TYPE_WEEK_OR_MONTH = 0; //显示周或月的记录
    public static final int TYPE_NOW_RECORD = 1; //刚生成的记录
    public static final int TYPE_RECORD_LIST = 2; //显示历史账单
}
