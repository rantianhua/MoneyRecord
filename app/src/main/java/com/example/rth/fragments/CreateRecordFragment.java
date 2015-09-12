package com.example.rth.fragments;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rth.BaseHomeFragment;
import com.example.rth.adapter.WheelTextAdapter;
import com.example.rth.data.MoneyRecord;
import com.example.rth.data.WheelData;
import com.example.rth.moneyrecord.R;
import com.example.rth.util.Utils;
import com.example.rth.util.WheelDataUtils;
import com.example.rth.widgets.wheel.TosGallery;
import com.example.rth.widgets.wheel.WheelView;

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
    private TextView tvMoneyLabel,tvCategory,tvGatvCategoryLabel,tvDate,tvDateLabel,tvRemark;  //
    private EditText etMoney;   //输入金额
    private Button btnSave; //保存帐单
    private Button btnAgain;    //再记一笔
    private float llWheelHeight;
    private ValueAnimator wheelShow; //滚轮显示的动画
    private ValueAnimator wheelHide; //滚轮消失的动画
    private int currentWheel = -1;   //表示当前滚轮显示的内容，0表示日期，1表示类别
    private StringBuilder builder = new StringBuilder();    //字符串拼接
    private String remark;  //备注
    private String dateTime;    //时间
    private String cateGory;    //类别
    private int iconId; //图片id

    private int colorRed,colorGreen;
    private boolean haveLimitInputSize = false; //标识有没有限制etMoney的长度

    //为EditText设置监听
    private final TextWatcher moneyWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if(text.contains(".") && !haveLimitInputSize) {
                //限制长度
                etMoney.setFilters(new InputFilter[]{new InputFilter.LengthFilter(text.length()+2)});
                haveLimitInputSize = true;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WheelDataUtils.getYears();
        colorGreen = getResources().getColor(R.color.green);
        colorRed = getResources().getColor(R.color.red);
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
        //得出llWheel的高度
        llWheel.post(new Runnable() {
            @Override
            public void run() {
                llWheelHeight = llWheel.getHeight();
            }
        });
        rlCategory = (RelativeLayout) view.findViewById(R.id.frag_create_rl_category);
        rlDate = (RelativeLayout) view.findViewById(R.id.frag_create_rl_date);
        tvMoneyLabel = (TextView) view.findViewById(R.id.frag_create_tv_money);
        etMoney = (EditText) view.findViewById(R.id.frag_create_et_money);
        etMoney.addTextChangedListener(moneyWatcher);
        setEtMoneyColor();
        tvCategory = (TextView) view.findViewById(R.id.frag_create_tv_category);
        tvGatvCategoryLabel = (TextView) view.findViewById(R.id.frag_create_tv_category_label);
        tvDate = (TextView) view.findViewById(R.id.frag_create_tv_date);
        tvDateLabel = (TextView) view.findViewById(R.id.frag_create_tv_date_label);
        tvRemark = (TextView) view.findViewById(R.id.frag_create_tv_remark);
        if(!TextUtils.isEmpty(remark)) {
            tvRemark.setText(remark);
        }
        btnSave = (Button) view.findViewById(R.id.frag_create_btn_save);
        btnAgain = (Button) view.findViewById(R.id.frag_create_btn_again);
        wvOne.setGravity(WheelView.VERTICAL);
        wvTwo.setGravity(WheelView.VERTICAL);
        wvThree.setGravity(WheelView.VERTICAL);
        wvFour.setGravity(WheelView.VERTICAL);
        wvFive.setGravity(WheelView.VERTICAL);
        wvOne.setAdapter(new WheelTextAdapter(getActivity()));
        wvTwo.setAdapter(new WheelTextAdapter(getActivity()));
        wvFive.setAdapter(new WheelTextAdapter(getActivity()));
        wvThree.setAdapter(new WheelTextAdapter(getActivity()));
        wvFour.setAdapter(new WheelTextAdapter(getActivity()));
        changeWheelData(wvTwo, WheelDataUtils.MONTH);
        wvTwo.setSelection(Utils.getCurrentMonth());
        WheelDataUtils.getDay(Utils.getCurrentYear(), Utils.getCurrentMonth() + 1);
        changeWheelData(wvThree, WheelDataUtils.DAY);
        wvThree.setSelection(Utils.getCurrentDay() - 1);
        changeWheelData(wvFour, WheelDataUtils.HOURS);
        wvFour.setSelection(Utils.getCurrentHours());
        if(!TextUtils.isEmpty(dateTime)) {
            tvDate.setText(dateTime);
        }else {
            showTime(null,null,null,null,null);
        }
        if(!TextUtils.isEmpty(cateGory)) {
            tvCategory.setText(cateGory);
        }else {
            showCategoryText(0, 0);
        }
        initAnimationSet();
        return view;
    }

    public void setEtMoneyColor() {
        if(action == ACTION_IN) {
            etMoney.setTextColor(colorRed);
        }else if(action == ACTION_OUT) {
            etMoney.setTextColor(colorGreen);
        }
    }

    @Override
    protected void initEvent(View view) {
        tvTitle.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        rlCategory.setOnClickListener(this);
        tvRemark.setOnClickListener(this);
        rlDate.setOnClickListener(this);
        wvOne.setOnEndFlingListener(this);
        wvTwo.setOnEndFlingListener(this);
        wvFive.setOnEndFlingListener(this);
        wvThree.setOnEndFlingListener(this);
        wvFour.setOnEndFlingListener(this);
        wvFive.setOnEndFlingListener(this);
    }

    /**
     * 显示当前分类
     * @param mainIndex 主分类的索引
     * @param subIndex 子分类的索引
     */
    private void showCategoryText(int mainIndex,int subIndex) {
        if(action == ACTION_IN) {
            builder.append(WheelDataUtils.firstCategoryIn.get(mainIndex).text);
            builder.append(">");
            builder.append(WheelDataUtils.mapSecondIn.get(mainIndex).get(subIndex).text);
            iconId = WheelDataUtils.mapSecondIn.get(mainIndex).get(subIndex).imgId;
        }else if(action == ACTION_OUT) {
            builder.append(WheelDataUtils.firstCategoryOut.get(mainIndex).text);
            builder.append(">");
            builder.append(WheelDataUtils.mapSecondOut.get(mainIndex).get(subIndex).text);
            iconId = WheelDataUtils.mapSecondOut.get(mainIndex).get(subIndex).imgId;
        }
        tvCategory.setText(builder.toString());
        builder.delete(0, builder.length());
    }

    /**
     * 显示当前或选择的时间
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minite
     */
    private void showTime(String year,String month,String day,String hour,String minite) {
        if(year == null) {
            year = Utils.getCurrentYear()+"";
            int mon = Utils.getCurrentMonth() + 1;
            month = mon < 10 ? "0" + mon : "" + mon;
            int d = Utils.getCurrentDay();
            day = d < 10 ? "0" + d : "" + d;
            int h = Utils.getCurrentHours();
            hour = h < 10 ? "0" + h : "" + h;
            int min = Utils.getCurrentMinite();
            minite = min < 10 ? "0" + min : "" + min;
        }else {
            year = year.substring(0,year.length()-1);
            month = month.substring(0,month.length()-1);
            day = day.substring(0,day.length()-1);
            if(month.length() == 1) month = "0" + month;
            if(day.length() == 1) day = "0" + day;
            if(hour.length() == 1) hour = "0" + hour;
            if(minite.length() == 1) minite = "0" + minite;
        }
        builder.append(year);
        builder.append("-");
        builder.append(month);
        builder.append("-");
        builder.append(day);
        builder.append(" ");
        builder.append(hour);
        builder.append(":");
        builder.append(minite);
        tvDate.setText(builder.toString());
        builder.delete(0, builder.length());
    }

    @Override
    public void onEndFling(TosGallery v) {
        //得到选中项的索引
        int position = v.getSelectedItemPosition();
        if(v == wvOne) {
            if(currentWheel == 1) {
                List<WheelData> newData = action == ACTION_IN ? WheelDataUtils.mapSecondIn.get(position)
                        : WheelDataUtils.mapSecondOut.get(position);
                changeWheelData(wvFive, newData);
                showCategoryText(position,wvFive.getSelectedItemPosition());
            }
        }else if(v == wvTwo) {
            String mon = WheelDataUtils.MONTH.get(wvTwo.getSelectedItemPosition()).text;
            mon = mon.substring(0,mon.length()-1);
            WheelDataUtils.getDay(Utils.getCurrentYear(), Integer.valueOf(mon));
            int select = wvThree.getSelectedItemPosition();
            while (select >= WheelDataUtils.DAY.size()) {
                select--;
            }
            wvThree.setSelection(select);
            changeWheelData(wvThree,WheelDataUtils.DAY);

        }else if(v == wvFive) {
            if(currentWheel == 1) {
                showCategoryText(wvOne.getSelectedItemPosition(),position);
            }
        }
        if(currentWheel == 0) {
            //更新日期的显示
            showTime(WheelDataUtils.YEAR.get(wvOne.getSelectedItemPosition()).text,
                    WheelDataUtils.MONTH.get(wvTwo.getSelectedItemPosition()).text,
                    WheelDataUtils.DAY.get(wvThree.getSelectedItemPosition()).text,
                    WheelDataUtils.HOURS.get(wvFour.getSelectedItemPosition()).text,
                    WheelDataUtils.MINIITE.get(wvFive.getSelectedItemPosition()).text);
        }
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
            case R.id.frag_create_tv_remark:
                getRemark();
                break;
            case R.id.frag_create_btn_save:
                saveRecord();
                dismiss();
                break;
            case R.id.frag_create_btn_again:
                saveRecord();
                resetInput();
                break;
        }
    }

    /**
     * 重新输入
     */
    private void resetInput() {
        showTime(null, null, null, null, null);
        tvRemark.setText("请填写备注信息");
    }

    /**
     * 退出该界面
     */
    private void dismiss() {
        if(getActivity() != null) {
            getActivity().finish();
        }
    }

    /**
     * 保存该账单
     */
    private void saveRecord() {
        String money = etMoney.getText().toString();
        String moneyIn = null;
        String moneyOut = null;
        if(action == ACTION_IN) {
            builder.append("+");
            builder.append(money);
            moneyIn = builder.toString();
        }else if(action == ACTION_OUT){
            builder.append("-");
            builder.append(money);
            moneyOut = builder.toString();
        }
        builder.delete(0,builder.length());
        cateGory = tvCategory.getText().toString();
        dateTime = tvDate.getText().toString();
        remark = tvRemark.getText().toString();
        MoneyRecord moneyRecord = new MoneyRecord(cateGory.substring(cateGory.indexOf(">")+1),
                dateTime,moneyIn,moneyOut,remark,iconId,MoneyRecord.TYPE_NOW_RECORD);
        Utils.RECORDS.add(moneyRecord);
        cateGory = null;
        remark = null;
        dateTime = null;
    }

    /**
     * 展示时间选择器
     */
    private void showDateTime() {
        if(llWheel.getVisibility() == View.VISIBLE) {
            if(currentWheel != 0) {
                hideWheel(true,0);
            }else {
                hideWheel(false,-1);
            }
        }else {
            showWheel(0);
        }
    }

    /**
     * 展示滚轮
     * @param index 0表示显示日期滚轮
     *              1表示显示类别滚轮
     */
    private void showWheel(final int index) {
        wheelShow = ValueAnimator.ofFloat( llWheelHeight,0f);
        wheelShow.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                llWheel.setTranslationY((float) valueAnimator.getAnimatedValue());
            }
        });
        wheelShow.setInterpolator(new DecelerateInterpolator());
        wheelShow.setDuration(200);
        wheelShow.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                btnSave.setVisibility(View.INVISIBLE);
                btnAgain.setVisibility(View.INVISIBLE);
                llWheel.setVisibility(View.VISIBLE);
                if(index == 0) {
                    //选择日期
                    wvTwo.setVisibility(View.VISIBLE);
                    wvThree.setVisibility(View.VISIBLE);
                    wvFour.setVisibility(View.VISIBLE);
                    changeWheelData(wvOne, WheelDataUtils.YEAR);
                    wvOne.setSelection(WheelDataUtils.YEAR.size() / 2);
                    changeWheelData(wvFive, WheelDataUtils.MINIITE);
                    wvFive.setSelection(Utils.getCurrentMinite());
                }else if(index == 1) {
                    //选择类别
                    wvTwo.setVisibility(View.GONE);
                    wvThree.setVisibility(View.GONE);
                    wvFour.setVisibility(View.GONE);
                    if(action == ACTION_IN) {
                        changeWheelData(wvOne, WheelDataUtils.firstCategoryIn);
                        changeWheelData(wvFive, WheelDataUtils.mapSecondIn.get(0));
                    }else if(action == ACTION_OUT) {
                        changeWheelData(wvOne, WheelDataUtils.firstCategoryOut);
                        changeWheelData(wvFive, WheelDataUtils.mapSecondOut.get(0));
                    }
                    wvOne.setSelection(0);
                    wvFive.setSelection(0);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                currentWheel = index;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        wheelShow.start();
    }

    /**
     * 更新滚轮的数据
     * @param wv    待更新的滚轮
     * @param data 新的数据
     */
    private void changeWheelData(WheelView wv,List<WheelData> data) {
        WheelTextAdapter adapterOne = (WheelTextAdapter)wv.getAdapter();
        adapterOne.setData(data);
    }

    /**
     * 隐藏滚轮
     * @param showOther 表示要不要在消失后再显示新的滚轮
     * @param index 0表示显示日期滚轮
     *              1表示显示类别滚轮
     */
    private void hideWheel(final boolean showOther,final int index) {
        wheelHide = ValueAnimator.ofFloat(0f,llWheelHeight);
        wheelHide.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                llWheel.setTranslationY((float) valueAnimator.getAnimatedValue());
            }
        });
        wheelHide.setDuration(200);
        wheelHide.setInterpolator(new DecelerateInterpolator());
        wheelHide.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                currentWheel = -1;
                if (showOther) {
                    showWheel(index);
                } else {
                    llWheel.setVisibility(View.INVISIBLE);
                    btnSave.setVisibility(View.VISIBLE);
                    btnAgain.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        wheelHide.start();
    }

    /**
     * 展示目录选项
     */
    private void showCategory() {
        if(llWheel.getVisibility() == View.VISIBLE) {
            if(currentWheel != 1) {
                hideWheel(true,1);
            }else {
                hideWheel(false,-1);
            }
        }else {
            showWheel(1);
        }
    }

    /**
     * 修改标题
     */
    private void changeTitle() {
        if(llWheel.getVisibility() ==View.VISIBLE) {
            hideWheel(false, -1);
        }
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
                setEtMoneyColor();
                showCategoryText(0, 0);
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

    @Override
    public void onPause() {
        super.onPause();
        remark = tvRemark.getText().toString();
        cateGory = tvCategory.getText().toString();
        dateTime = tvDate.getText().toString();
    }

    /**
     * 获取用户输入的备注
     */
    public void getRemark() {
        String category = tvCategory.getText().toString();
        String subCate = category.substring(category.indexOf(">") + 1);
        String mon = etMoney.getText().toString();
        if(TextUtils.isEmpty(mon)) {
            mon = "0.00";
        }
        Fragment remarkFragment = RemarkFragment.getInstance(mon,subCate,remark);
        remarkFragment.setTargetFragment(this,REQUEST_REMARK);
        getFragmentManager().beginTransaction().replace(R.id.activity_create_rl_container,remarkFragment)
                .addToBackStack("remark").commit();
    }

    public static final int REQUEST_REMARK = 0; //获取备注的请求代码

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_REMARK && resultCode == Activity.RESULT_OK && data != null) {
            remark = data.getStringExtra("remark");
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
