package com.shouzhan.design.compontent.picker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shouzhan.design.R;
import com.shouzhan.design.callback.OnPickerConfirmClickListener;

import java.util.ArrayList;

/**
 * @author danbin
 * 时分秒滚轮效果的视图
 */
public class TimeWheelView extends LinearLayout {

    private WheelView hoursWheelView;
    private WheelView minutesWheelView;
    private WheelView secondsWheelView;
    private TextView selectOKTv;
    private TextView dataTitleTv;
    private TextView selectNowTimeTv;

    private ArrayList<String> list_hours = new ArrayList<>();
    private ArrayList<String> list_minutes = new ArrayList<>();
    private ArrayList<String> list_seconds = new ArrayList<>();

    private String startTime = "10:10:10";
    private int hours = 10;
    private int minutes = 10;
    private int seconds = 10;

    public TimeWheelView(Context context) {
        this(context, null);
    }

    public TimeWheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
        initData();
        initEvent();
    }


    /**
     * 初始化数据
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_loop_time, this);
        hoursWheelView = findViewById(R.id.loopView_year);
        minutesWheelView = findViewById(R.id.loopView_month);
        secondsWheelView = findViewById(R.id.loopView_day);
        selectOKTv = findViewById(R.id.tv_selectOK);
        dataTitleTv = findViewById(R.id.tv_data_title);
        selectNowTimeTv = findViewById(R.id.tv_selectToday);
    }

    private void initData() {
        initDataList();
        hours = getHours();
        minutes = getMinutes();
        seconds = getSeconds();
        hoursWheelView.setAdapter(new StringWheelAdapter(list_hours));
        minutesWheelView.setAdapter(new StringWheelAdapter(list_minutes));
        secondsWheelView.setAdapter(new StringWheelAdapter(list_seconds));
        //默认时间,当前时分秒
        for (int i = 0; i < list_hours.size(); i++) {
            if (Integer.parseInt(list_hours.get(i)) == getHours()) {
                hoursWheelView.setCurrentItem(i);
            }
        }
        minutesWheelView.setCurrentItem(getMinutes());
        secondsWheelView.setCurrentItem(getSeconds());
    }


    private void initEvent() {
        //滚动监听,小时
        hoursWheelView.setOnItemSelectedListener(index -> hours = Integer.parseInt(list_hours.get(index)));
        //滚动监听,分钟
        minutesWheelView.setOnItemSelectedListener(index -> minutes = Integer.parseInt(list_minutes.get(index)));
        //滚动监听,秒数
        secondsWheelView.setOnItemSelectedListener(index -> seconds = Integer.parseInt(list_seconds.get(index)));
        //确定按钮的监听
        selectOKTv.setOnClickListener(v -> {
            startTime = hours + ":" + minutes + ":" + seconds;
            //监听
            if (confirmClickListener != null) {
                confirmClickListener.selectData(startTime);
            }
        });
        //滚轮显示当时时间的监听
        selectNowTimeTv.setOnClickListener(v -> {
            for (int i = 0; i < list_hours.size(); i++) {
                if (Integer.parseInt(list_hours.get(i)) == getHours()) {
                    hoursWheelView.setCurrentItem(i);
                }
            }
            minutesWheelView.setCurrentItem(getMinutes());
            secondsWheelView.setCurrentItem(getSeconds());
            hours = getHours();
            minutes = getMinutes();
            seconds = getSeconds();
        });
    }

    /**
     * 初始化时间的几个集合数据
     */
    private void initDataList() {
        list_hours.clear();
        list_minutes.clear();
        list_seconds.clear();
        //小时的时间,12小时，还是24？
        for (int i = 0; i <= 23; i++) {
            list_hours.add("" + i);
        }
        //分钟的时间
        for (int i = 0; i <= 59; i++) {
            list_minutes.add("" + i);
        }
        //秒数的时间
        for (int i = 0; i <= 59; i++) {
            list_seconds.add("" + i);
        }
    }


    /**
     * 获取得当前时间的秒数
     *
     * @return
     */
    private int getSeconds() {
        return TimeUtil.getTimeInt("s");
    }


    /**
     * 获得当前时间的分钟
     */
    private int getMinutes() {
        return TimeUtil.getTimeInt("m");
    }

    /**
     * 获得当前时间的小时
     */
    private int getHours() {
        return TimeUtil.getTimeInt("H");
    }


    /**
     * 设置标题的名字
     *
     * @param titleName
     */
    public void setTitleName(String titleName) {
        dataTitleTv.setText("" + titleName);
    }

    /**
     * 设置标题的字体颜色
     */
    public void setTitleColor(int color) {
        dataTitleTv.setTextColor(color);
    }


    /**
     * 设置标题的背景
     */
    public void setTitleBackground(int color) {
        dataTitleTv.setBackgroundColor(color);
    }


    /**
     * 设置滚轮不可以循环，默认是可以循环的
     */
    public void setCyclic(boolean cyclic) {
        hoursWheelView.setCyclic(cyclic);
        minutesWheelView.setCyclic(cyclic);
        secondsWheelView.setCyclic(cyclic);
    }

    private OnPickerConfirmClickListener confirmClickListener;

    /**
     * 设置回调监听事件，往外面发送选中的日期字符串
     *
     * @param confirmClickListener
     */
    public void setPickerConfirmClickListener(OnPickerConfirmClickListener confirmClickListener) {
        this.confirmClickListener = confirmClickListener;
    }

}
