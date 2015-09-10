package com.example.rth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rth on 15-9-9.
 * 该类是一个抽象类，描述所有Fragment的共有属性
 */
public abstract class BaseHomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView(inflater,container);
        initEvent(view);
        return view;
    }

    /**
     *
     * @param inflater  加载布局文件
     * @param container 父视图
     * @return 该Fragment的视图
     */
    public abstract View initView(LayoutInflater inflater,ViewGroup container);

    /**
     *
     * @param view  该fragment的视图
     *              为该视图内的控件添加事件监听
     */
    protected abstract void initEvent(View view);

}
