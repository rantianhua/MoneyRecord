package com.example.rth.fragments;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rth.BaseHomeFragment;
import com.example.rth.adapter.WheelTextAdapter;
import com.example.rth.moneyrecord.R;
import com.example.rth.util.WheelDataUtils;
import com.example.rth.widgets.wheel.TosGallery;
import com.example.rth.widgets.wheel.WheelView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rth on 15-9-11.
 */
public class CreateRecordFragment extends BaseHomeFragment implements TosGallery.OnEndFlingListener,View.OnClickListener{

    private int action = ACTION_OUT;    //记录当前动作
    public static final int ACTION_IN = 0;  //标识新增收入
    public static final int ACTION_OUT = 1;  //标识新增支出
    private AnimatorSet animaTitle; //标题的动画

    private ImageView imgBack;  //点击返回
    private TextView tvTitle;   //标题
    private ImageView imgTitleIndicator;    //标题指示器
    private WheelView wvOne,wvTwo,wvThree,wvFour,wvFive;    //滚轮选择器
    private LinearLayout llWheel;   //滚轮选择器的容器
    private RelativeLayout rlCategory;  //类别项
    private RelativeLayout rlDate;  //日期项
    private TextView tvMoneyLabel,tvCategory,tvGatvCategoryLabel,tvDate,tvDateLabel;  //
    private EditText etMoney;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAnimationSet();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_create_record,container,false);
        imgBack = (ImageView) view.findViewById(R.id.frag_create_img_back);
        tvTitle = (TextView) view.findViewById(R.id.frag_create_tv_action);
        imgTitleIndicator = (ImageView) view.findViewById(R.id.frag_create_img_indicator);
        wvOne = (WheelView) view.findViewById(R.id.frag_create_wheel_one);
        wvTwo = (WheelView) view.findViewById(R.id.frag_create_wheel_two);
        wvThree = (WheelView) view.findViewById(R.id.frag_create_wheel_three);
        wvFour = (WheelView) view.findViewById(R.id.frag_create_wheel_four);
        wvFive = (WheelView) view.findViewById(R.id.frag_create_wheel_five);
        llWheel = (LinearLayout) view.findViewById(R.id.frag_create_ll_wheels);
        rlCategory = (RelativeLayout) view.findViewById(R.id.frag_create_rl_category);
        rlDate = (RelativeLayout) view.findViewById(R.id.frag_create_rl_date);
        tvMoneyLabel = (TextView) view.findViewById(R.id.frag_create_tv_category_label);
        etMoney = (EditText) view.findViewById(R.id.frag_create_et_money);
        tvCategory = (TextView) view.findViewById(R.id.frag_create_tv_category);
        tvGatvCategoryLabel = (TextView) view.findViewById(R.id.frag_create_tv_category_label);
        tvDate = (TextView) view.findViewById(R.id.frag_create_tv_date);
        tvDateLabel = (TextView) view.findViewById(R.id.frag_create_tv_date_label);
        wvOne.setGravity(WheelView.VERTICAL);
        wvTwo.setGravity(WheelView.VERTICAL);
        wvThree.setGravity(WheelView.VERTICAL);
        wvFour.setGravity(WheelView.VERTICAL);
        wvFive.setGravity(WheelView.VERTICAL);
        return view;
    }

    @Override
    protected void initEvent(View view) {
        tvTitle.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        rlCategory.setOnClickListener(this);
        rlDate.setOnClickListener(this);
        wvOne.setOnEndFlingListener(this);
        wvTwo.setOnEndFlingListener(this);
        wvFive.setOnEndFlingListener(this);
        wvThree.setOnEndFlingListener(this);
        wvFour.setOnEndFlingListener(this);
        wvOne.setAdapter(new WheelTextAdapter(getActivity()));
        wvTwo.setAdapter(new WheelTextAdapter(getActivity()));
        wvFive.setAdapter(new WheelTextAdapter(getActivity()));
        wvThree.setAdapter(new WheelTextAdapter(getActivity()));
        wvFour.setAdapter(new WheelTextAdapter(getActivity()));
    }

    @Override
    public void onEndFling(TosGallery v) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_create_tv_action:
                changeTitle();
                break;
            case R.id.frag_create_img_back:
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
                break;
            case R.id.frag_create_rl_category:
                showCategory();
                break;
            case R.id.frag_create_rl_date:
                showDateTime();
                break;
        }
    }

    /**
     * 展示时间选择器
     */
    private void showDateTime() {
        if(llWheel.getVisibility() == View.VISIBLE) {
            hideWheel();
        }
        wvTwo.setVisibility(View.VISIBLE);
        wvThree.setVisibility(View.VISIBLE);
        wvFour.setVisibility(View.VISIBLE);
        showWheel();
    }

    /**
     * 展示滚轮
     */
    private void showWheel() {
        ValueAnimator show = ValueAnimator.ofFloat(1f,0f);
        show.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                llWheel.setTranslationY((float)valueAnimator.getAnimatedValue());
            }
        });
        show.setDuration(120);
        show.start();
    }

    /**
     * 隐藏滚轮
     */
    private void hideWheel() {
        ValueAnimator hide = ValueAnimator.ofFloat(0f,1f);
        hide.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                llWheel.setTranslationY((float)valueAnimator.getAnimatedValue());
            }
        });
        hide.setDuration(120);
        hide.start();
    }

    /**
     * 展示目录选项
     */
    private void showCategory() {
        if(llWheel.getVisibility() == View.VISIBLE) {
            hideWheel();
        }
        wvTwo.setVisibility(View.GONE);
        wvThree.setVisibility(View.GONE);
        wvFour.setVisibility(View.GONE);
        WheelTextAdapter adapterOne = (WheelTextAdapter)wvOne.getAdapter();
        adapterOne.setData(WheelDataUtils.firstCategoryIn);
        WheelTextAdapter adapterTwo = (WheelTextAdapter)wvFive.getAdapter();
        adapterTwo.setData(WheelDataUtils.mapFirstIn.get(0));
        showWheel();
    }

    /**
     * 修改标题
     */
    private void changeTitle() {
        animaTitle.start();
    }

    /**
     * 初始化动画
     */
    private void initAnimationSet() {
        animaTitle = new AnimatorSet();
        ValueAnimator fadeOut = ValueAnimator.ofFloat(1.0f,0.0f);
        final ValueAnimator fadeIn = ValueAnimator.ofFloat(0.0f,1.0f);
        fadeIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = (float) valueAnimator.getAnimatedValue();
                tvTitle.setAlpha(scale);
                tvTitle.setScaleX(scale);
                tvTitle.setScaleY(scale);
            }
        });
        fadeIn.setDuration(100);
        fadeOut.setDuration(100);
        fadeOut.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = (float) valueAnimator.getAnimatedValue();
                tvTitle.setAlpha(scale);
                tvTitle.setScaleX(scale);
                tvTitle.setScaleY(scale);
            }
        });
        fadeOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (action == ACTION_OUT) {
                    tvTitle.setText("新增收入");
                } else {
                    tvTitle.setText("新增支出");
                }
                fadeIn.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        ValueAnimator rotate = ValueAnimator.ofFloat(0f, 180f);
        rotate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = (float) valueAnimator.getAnimatedValue();
                if (action == ACTION_IN) {
                    scale = 180f - scale;
                }
                imgTitleIndicator.setRotation(scale);
            }
        });
        rotate.setDuration(200);
        animaTitle.play(fadeOut).with(rotate);
        animaTitle.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                tvTitle.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                tvTitle.setClickable(true);
                action = action == ACTION_IN ? ACTION_OUT : ACTION_IN;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                tvTitle.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

}
