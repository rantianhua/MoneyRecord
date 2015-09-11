package com.example.rth.data;

/**
 * Created by rth on 15-9-11.
 * 滚轮选择器里的数据
 */
public class WheelData {

    public String text; //文本信息
    public int imgId = -1;  //图片资源id

    public WheelData(String text) {
        this.text = text;
    }

    public WheelData(String text,int id) {
        this(text);
        this.imgId = id;
    }
}
