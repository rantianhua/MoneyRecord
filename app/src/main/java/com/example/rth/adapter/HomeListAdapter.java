package com.example.rth.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rth.data.MoneyRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rth on 15-9-10.
 */
public class HomeListAdapter extends BaseAdapter {

    private List<MoneyRecord> dataSets = new ArrayList<>();

    public HomeListAdapter() {

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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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
        return null;
    }

    static class ViewHolder {
        ImageView img;  //图标
        TextView tvTitle;   //显示账单标题
        TextView tvDate;    //显示账单日期
        TextView tvMoneyIn; //显示收入
        TextView tvMoneyOut; //显示支出
    }
}
