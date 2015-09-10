package com.example.rth.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rth.data.MoneyRecord;
import com.example.rth.moneyrecord.R;

import java.nio.InvalidMarkException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rth on 15-9-10.
 */
public class HomeListAdapter extends BaseAdapter {

    private List<MoneyRecord> dataSets = new ArrayList<>(); //数据源
    private LayoutInflater inflater;    //加载布局文件
    private Resources resources;    //获取xml资源
    private StringBuilder builder = new StringBuilder();    //拼凑字符串

    public HomeListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        resources = context.getResources();
    }

    /**
     * 更新数据源
     * @param datas 新的数据源
     */
    public void updateDatas(List<MoneyRecord> datas) {
        dataSets.clear();
        dataSets.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataSets.size();
    }

    @Override
    public Object getItem(int i) {
        return dataSets.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.record_item_left,null);
            holder.img = (ImageView) view.findViewById(R.id.record_item_img_icon);
            holder.tvDate = (TextView) view.findViewById(R.id.record_item_tv_date);
            holder.tvTitle = (TextView) view.findViewById(R.id.record_item_tv_title);
            holder.tvMoneyIn = (TextView) view.findViewById(R.id.record_item_tv_money_in);
            holder.tvMoneyOut = (TextView) view.findViewById(R.id.record_item_tv_money_out);
            holder.imgRight = (ImageView) view.findViewById(R.id.record_item_img_right);
            holder.tvRemark = (TextView) view.findViewById(R.id.record_item_tv_remark);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        MoneyRecord record = dataSets.get(i);
        holder.tvTitle.setText(record.title);
        switch (record.type) {
            case MoneyRecord.TYPE_NOW_RECORD:
                holder.tvMoneyIn.setVisibility(View.GONE);
                builder.append(record.date);
                if(record.remark != null) {
                    builder.append(" ");
                    builder.append(record.remark);
                }
                holder.tvDate.setText(builder.toString());
                builder.delete(0, builder.length());
                if(record.moneyIn != null) {
                    holder.tvMoneyOut.setTextColor(resources.getColor(R.color.red));
                    holder.tvMoneyOut.setText(record.moneyIn);
                }else if(record.moneyOut != null) {
                    holder.tvMoneyOut.setText(record.moneyOut);
                }
                break;
            case MoneyRecord.TYPE_RECORD_LIST:
                holder.tvDate.setTextSize(12f);
                holder.tvRemark.setVisibility(View.VISIBLE);
                if(record.remark != null) {
                    holder.tvRemark.setText(record.remark);
                }
                holder.tvMoneyIn.setVisibility(View.GONE);
                holder.imgRight.setVisibility(View.INVISIBLE);
                holder.tvDate.setText(record.date);
                if(record.moneyIn != null) {
                    holder.tvMoneyOut.setTextColor(resources.getColor(R.color.red));
                    holder.tvMoneyOut.setText(record.moneyIn);
                }else if(record.moneyOut != null) {
                    holder.tvMoneyOut.setText(record.moneyOut);
                }
                break;
            case MoneyRecord.TYPE_WEEK_OR_MONTH:
                holder.tvDate.setText(record.date);
                holder.tvMoneyIn.setText(record.moneyIn);
                holder.tvMoneyOut.setText(record.moneyOut);
                break;
        }
        return view;
    }

    static class ViewHolder {
        ImageView img;  //图标
        TextView tvTitle;   //显示账单标题
        TextView tvDate;    //显示账单日期
        TextView tvMoneyIn; //显示收入
        TextView tvMoneyOut; //显示支出
        ImageView imgRight; //向右箭头
        TextView tvRemark;  //显示备注
    }
}
