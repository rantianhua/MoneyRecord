package com.example.rth.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rth.data.WheelData;
import com.example.rth.moneyrecord.R;
import com.example.rth.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rth on 15-9-11.
 */

public class WheelTextAdapter extends BaseAdapter {
    List<WheelData> mData = null;
    int mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
    Context mContext = null;

    public WheelTextAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<WheelData> data) {
        mData = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (null != mData) ? mData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        WheelData data = mData.get(position);
        if (null == convertView) {
            holder = new ViewHolder();
            if(data.imgId == -1) {
                //没有图标
                convertView = LayoutInflater.from(mContext).inflate(R.layout.wheel_item_without_icon,null);
                holder.tvText = (TextView) convertView;
            }else {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.wheel_item_with_icon,null);
                holder.tvText = (TextView) convertView.findViewById(R.id.wheel_item_tv_text);
                holder.imgIcon = (ImageView) convertView.findViewById(R.id.wheel_item_img_icon);
            }
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvText.setText(data.text);
        if(data.imgId != -1) {
            holder.imgIcon.setBackgroundResource(data.imgId);
        }
        return convertView;
    }

    static class ViewHolder{
        TextView tvText;
        ImageView imgIcon;
    }

}