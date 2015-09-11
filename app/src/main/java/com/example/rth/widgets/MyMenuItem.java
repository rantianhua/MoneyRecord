package com.example.rth.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rth.moneyrecord.R;

import org.w3c.dom.Text;

/**
 * Created by rth on 15-9-11.
 */
public class MyMenuItem extends RelativeLayout {

    private TextView tvTitle;   //菜单标题
    private ImageView imgIcon;  //菜单图标

    public MyMenuItem(Context context) {
        super(context);
    }

    public MyMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    /**
     * 初始化视图
     */
    private void initView(Context context,AttributeSet attributeSet) {
        tvTitle = new TextView(context);
        imgIcon = new ImageView(context);
        int tvPadding = (int)(5 * context.getResources().getDisplayMetrics().density + 0.5f);
        tvTitle.setPadding(tvPadding,tvPadding,tvPadding,tvPadding);
        TypedArray ta = context.obtainStyledAttributes(attributeSet, R.styleable.MyMenuItem);
        int num = ta.getIndexCount();
        int resourceId = 0;
        for(int i = 0;i < num;i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.MyMenuItem_textSize:
                    resourceId = ta.getResourceId(attr,0);
                    float size = resourceId > 0 ? ta.getResources().getDimension(resourceId)
                            : 100 * getResources().getDisplayMetrics().density + 0.5f;
                    tvTitle.setTextSize(size);
                    break;
                case R.styleable.MyMenuItem_tv_background:
                    resourceId = ta.getResourceId(attr,0);
                    tvTitle.setBackgroundResource(resourceId);
                    break;
                case R.styleable.MyMenuItem_img_height:


                    break;
            }
        }



    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed) {

        }
    }
}
