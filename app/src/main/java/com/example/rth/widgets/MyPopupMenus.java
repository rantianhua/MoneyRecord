package com.example.rth.widgets;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    private RelativeLayout rlCreateRecrd;   //记账菜单项
    private RelativeLayout rlRecordChart; //历史账单菜单项
    private RelativeLayout rlUsercenter;    //用户中心菜单项
    private FrameLayout frameBg;    //视图背景
    private Rect rect = new Rect(); //获取一些控件的坐标区域
    private PopupMenuListener listener; //回调接口

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

    public void setListener(PopupMenuListener listener) {
        this.listener = listener;
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
        rlCreateRecrd = (RelativeLayout) rlMenus.findViewById(R.id.view_menus_rl_record);
        rlRecordChart = (RelativeLayout) rlMenus.findViewById(R.id.view_menus_rl_chart);
        rlUsercenter = (RelativeLayout) rlMenus.findViewById(R.id.view_menus_rl_user_center);
        frameBg = (FrameLayout) view.findViewById(R.id.view_menu_frame_bg);
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
        rlCreateRecrd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击“记账”菜单
                listener.onItemClick(0);
                hideMenus();
            }
        });
        rlRecordChart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击“查看图表”菜单
                listener.onItemClick(1);
                hideMenus();
            }
        });
        rlUsercenter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击“个人中心”菜单
                listener.onItemClick(2);
                hideMenus();
            }
        });
        rlCreateRecrd.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        rlCreateRecrd.setAlpha(0.6f);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        rlCreateRecrd.setAlpha(1.0f);
                        break;
                }
                return false;
            }
        });
    }

//    int test = -1;
//    /**
//     * 事件的分发
//     * @param ev
//     * @return
//     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if(menusShow) {
//            int touchX = (int)ev.getX();
//            int touchY = (int)ev.getY();
//            if(touchThisView(touchX,touchY,rlCreateRecrd) || touchThisView(touchX,touchY,rlRecordChart)
//                    || touchThisView(touchX,touchY,rlUsercenter) || touchThisView(touchX,touchY,imgMenusControll)) {
//                //点击了这些空间则将事件分发
//                return false;
//            }else {
//                //自己处理
//                return true;
//            }
//        }else {
//            Log.e("Dispatch TEst is",test + "");
//            test++;
//            return false;
//        }
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.e("Intercept TEst is",test + "");
//        test++;
//        return true;
//    }
//
//    /**
//     * 检查是不是点击了指定的View
//     * @param touchX    点击的x坐标
//     * @param touchY    点击的y坐标
//     * @param view  待检查的VIew
//     * @return false 没有点击该view
//     *         true  点击了该view
//     */
//    private boolean touchThisView(int touchX, int touchY, View view) {
//        view.getGlobalVisibleRect(rect);
//        return rect.contains(touchX,touchY);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.e("Ontouch TEst is",test + "");
//        test++;
//        if(event.getAction() == MotionEvent.ACTION_UP && menusShow) {
//            hideMenus();
//            return true;
//        }
//        return super.onTouchEvent(event);
//    }

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
        ValueAnimator rotate = ValueAnimator.ofFloat(0f, 135f);
        rotate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float radio = (float) valueAnimator.getAnimatedValue();
                imgMenusControll.setRotation(radio);
            }
        });
        animators.add(rotate);
        ValueAnimator alpha = ValueAnimator.ofFloat(0.0f, 1.0f);
        alpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float radio = (float)valueAnimator.getAnimatedValue();
                rlMenus.setAlpha(radio);
            }
        });
        animators.add(alpha);
        ValueAnimator scale = ValueAnimator.ofFloat(2.0f, 1.0f);
        scale.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rlMenus.setScaleY((float) valueAnimator.getAnimatedValue());
            }
        });
        animators.add(scale);
        ValueAnimator bg = ValueAnimator.ofFloat(0.0f, 0.9f);
        bg.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                frameBg.setAlpha((float) valueAnimator.getAnimatedValue());
            }
        });
        animators.add(bg);
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
        ValueAnimator bgBack = ValueAnimator.ofFloat(0.9f, 0.0f);
        bgBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                frameBg.setAlpha((float) valueAnimator.getAnimatedValue());
            }
        });
        hide.add(bgBack);
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

    /**
     * 事件接口，向调用着传送事件
     */
    public interface PopupMenuListener{
        /**
         * 菜单项点击时间的回调方法
         * @param position   0 表示点击了“记一笔”
         *                   1 表示点击了“查看账单”
         *                   2 表示点击了“个人中心”
         */
        public void onItemClick(int position);
    }

}
