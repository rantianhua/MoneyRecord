package com.example.rth.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rth on 15-9-10.
 */
public class MoneyRecord implements Parcelable{

    public String mainTitle;    //主分类
    public String subTitle; //子分类
    public String year; //年份
    public String month; //月份
    public String date; //账单日期
    public String time; //时间
    public String moneyOut;    //花销
    public String moneyIn;  //收入
    public String remark;   //备注
    public String picId;   //图片id
    public String showDateTime; //供显示的具体时间
    public int type;    //账单类型
    public int recordId;    //账单id

    public MoneyRecord(String mainTitle,String subTitle,String year,String month,String date,
                       String time,String moneyOut,String moneyIn,String remark,String picId,int type) {
        this.mainTitle = mainTitle;
        this.subTitle =subTitle;
        this.year = year;
        this.month = month;
        this.date = date;
        this.time = time;
        this.moneyOut = moneyOut;
        this.moneyIn = moneyIn;
        this.remark = remark;
        this.picId = picId;
        this.type = type;
        this.showDateTime = year + "-" + month + "-" +date + " " + time;
    }

    public MoneyRecord(Parcel parcel) {
        mainTitle = parcel.readString();
        subTitle = parcel.readString();
        year = parcel.readString();
        month = parcel.readString();
        date = parcel.readString();
        time = parcel.readString();
        moneyOut = parcel.readString();
        moneyIn = parcel.readString();
        remark = parcel.readString();
        picId = parcel.readString();
        showDateTime = parcel.readString();
        type = parcel.readInt();
        recordId = parcel.readInt();
    }

    public static final Creator<MoneyRecord> CREATOR = new Creator<MoneyRecord>() {
        @Override
        public MoneyRecord createFromParcel(Parcel parcel) {
            return new MoneyRecord(parcel);
        }

        @Override
        public MoneyRecord[] newArray(int i) {
            return new MoneyRecord[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mainTitle);
        parcel.writeString(this.subTitle);
        parcel.writeString(this.year);
        parcel.writeString(this.month);
        parcel.writeString(this.date);
        parcel.writeString(this.time);
        parcel.writeString(this.moneyOut);
        parcel.writeString(this.moneyIn);
        parcel.writeString(this.remark);
        parcel.writeString(this.picId);
        parcel.writeString(showDateTime);
        parcel.writeInt(this.type);
        parcel.writeInt(this.recordId);
    }

    public void setShowDateTime(String showDateTime) {
        this.showDateTime = showDateTime;
    }

    public static final int TYPE_WEEK_OR_MONTH = 0; //显示周或月的记录
    public static final int TYPE_NOW_RECORD = 1; //刚生成的记录
    public static final int TYPE_RECORD_LIST = 2; //显示历史账单
}
