package com.example.rth.widgets;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.rth.moneyrecord.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rth on 15-9-10.
 * 自定义弹出菜单
 *
 */
public class MyPopupMenus extends FrameLayout {

    private RelativeLayout rlMenus; //弹出菜单的容器
    private ImageView imgMenusControll; //控制弹出菜单的显示和隐藏
    private boolean menusShow = false;  //标识弹出菜单是否已经显示
    private AnimatorSet animatorSetShow;    //显示菜单的动画集合
    private AnimatorSet animatorSetHide;    //隐藏菜单的动画集合

    public MyPopupMenus(Context context) {
        super(context);
        initView(context);
    }

    public MyPopupMenus(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyPopupMenus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化视图
     */
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_menus,null);
        imgMenusControll = (ImageView) view.findViewById(R.id.view_menus_img_menus);
        rlMenus = (RelativeLayout) view.findViewById(R.id.view_menus_rl_menus);
        //先隐藏弹出菜单
        rlMenus.setVisibility(INVISIBLE);
        addView(view);
        initEvent();
        initAnimations();
    }

    /**
     * 注册事件
     */
    private void initEvent() {
        imgMenusControll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MenusControll();
            }
        });
    }



    /**
     * imgMenusControll的点击事件
     */
    public void MenusControll() {
        if(menusShow) {
            hideMenus();
        }else{
            showMenus();
        }
    }


    /**
     * 显示弹出菜单
     */
    private void showMenus() {
        if(animatorSetShow != null) {
            animatorSetShow.start();
        }
    }


    /**
     * 隐藏菜单
     */
    private void hideMenus() {
        animatorSetHide.start();
    }

    /**
     * 初始化动画
     */
    private void initAnimations() {
        //显示的动画
        animatorSetShow = new AnimatorSet();
        animatorSetShow.setInterpolator(new DecelerateInterpolator());
        animatorSetShow.setDuration(200);
        List<Animator> animators = new ArrayList<>();
        ValueAnimator rotate = ValueAnimator.ofFloat(0f,135f);
        rotate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float radio = (float)valueAnimator.getAnimatedValue();
                imgMenusControll.setRotation(radio);
            }
        });
        animators.add(rotate);
        ValueAnimator alpha = ValueAnimator.ofFloat(0.0f,1.0f);
        alpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float radio = (float)valueAnimator.getAnimatedValue();
                rlMenus.setAlpha(radio);
            }
        });
        animators.add(alpha);
        ValueAnimator scale = ValueAnimator.ofFloat(2.0f,1.0f);
        scale.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rlMenus.setScaleY((float) valueAnimator.getAnimatedValue());
            }
        });
        animators.add(scale);
        animatorSetShow.playTogether(animators);
        animatorSetShow.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                rlMenus.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                menusShow = true;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        //隐藏的动画
        animatorSetHide = new AnimatorSet();
        animatorSetHide.setDuration(200);
        animatorSetHide.setInterpolator(new DecelerateInterpolator());
        List<Animator> hide = new ArrayList<>();
        ValueAnimator rotateBack = ValueAnimator.ofFloat(135f,0f);
        rotateBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float radio = (float) valueAnimator.getAnimatedValue();
                imgMenusControll.setRotation(radio);
            }
        });
        hide.add(rotateBack);
        ValueAnimator alphaBack = ValueAnimator.ofFloat(1.0f,0.0f);
        alphaBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float radio = (float)valueAnimator.getAnimatedValue();
                rlMenus.setAlpha(radio);
            }
        });
        hide.add(alphaBack);
        ValueAnimator scaleBack = ValueAnimator.ofFloat(1.0f,2.0f);
        scaleBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rlMenus.setScaleY((float) valueAnimator.getAnimatedValue());
            }
        });
        hide.add(scaleBack);
        animatorSetHide.playTogether(hide);
        animatorSetHide.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rlMenus.setVisibility(INVISIBLE);
                menusShow = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

}
