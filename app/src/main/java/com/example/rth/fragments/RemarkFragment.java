package com.example.rth.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rth.BaseHomeFragment;
import com.example.rth.moneyrecord.R;

/**
 * Created by rth on 15-9-12.
 */
public class RemarkFragment extends BaseHomeFragment {

    private String money;   //金额
    private String category;    //目录
    private String remark;  //之前已有的备注

    private TextView tvCategory,tvMoney;
    private EditText etRemark;  //输入框
    private ImageView imgBack,imgOk;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null) {
            money = bundle.getString("category");
            category = bundle.getString("money");
            try {
                remark = bundle.getString("remark");
            }catch (Exception e) {

            }
        }
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_remark,container,false);
        tvCategory = (TextView) view.findViewById(R.id.frag_remark_tv_category);
        tvMoney = (TextView) view.findViewById(R.id.frag_remark_tv_money);
        etRemark = (EditText) view.findViewById(R.id.frag_remark_et_remark);
        imgBack = (ImageView) view.findViewById(R.id.frag_remark_img_back);
        imgOk = (ImageView) view.findViewById(R.id.frag_remark_img_ok);
        tvCategory.setText(category);
        tvMoney.setText("金额:" + money);
        if(!TextUtils.isEmpty(remark)) {
            etRemark.setText(remark);
        }
        return view;
    }

    @Override
    protected void initEvent(View view) {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        imgOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtainRemark();
            }
        });
    }

    /**
     * 回退
     */
    private void back() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    /**
     * 获取备注信息
     */
    private void obtainRemark() {
        String remark = etRemark.getText().toString();
        if(!TextUtils.isEmpty(remark)) {
            if(getTargetFragment() != null) {
                Intent intent = new Intent();
                intent.putExtra("remark", remark);
                getTargetFragment().onActivityResult(CreateRecordFragment.REQUEST_REMARK, Activity.RESULT_OK,
                        intent);
            }
        }
        back();
    }

    public static RemarkFragment getInstance(String category,String money,String remark) {
        Bundle bundle = new Bundle();
        bundle.putString("category",category);
        bundle.putString("money", money);
        if(!TextUtils.isEmpty(remark)) {
            bundle.putString("remark", remark);
        }
        RemarkFragment fragment = new RemarkFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
