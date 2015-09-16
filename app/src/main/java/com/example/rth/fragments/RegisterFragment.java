package com.example.rth.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rth.BaseHomeFragment;
import com.example.rth.moneyrecord.R;

/**
 * Created by rth on 15-9-16.
 */
public class RegisterFragment extends BaseHomeFragment {



    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        return view;
    }

    @Override
    protected void initEvent(View view) {

    }
}
