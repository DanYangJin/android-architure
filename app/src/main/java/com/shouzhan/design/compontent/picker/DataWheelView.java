package com.shouzhan.design.compontent.picker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.shouzhan.design.R;
import com.shouzhan.design.callback.OnOKClickListener;
import com.shouzhan.design.callback.OnPickerScrollListener;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 年月日滚轮效果的视图
 *
 * @author zsc
 */
public class DataWheelView extends LinearLayout {

    private WheelView loopViewYear;
    private WheelView loopViewMonth;
    private WheelView loopViewDay;

    private ArrayList<String> listYear = new ArrayList<>();
    private ArrayList<String> listMonth = new ArrayList<>();
    private ArrayList<String> listDay = new ArrayList<>();
    private Calendar now = Calendar.getInstance();

    private String startTime = "2010-10-10";
    private int year = 2010;
    private int month = 10;
    private int day = 10;
    private int oldDayCounts = 31;
    private int oldMonthCounts = 12;

    public DataWheelView(Context context) {
        this(context, null);
    }

    public DataWheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initData();
        initEvent();
    }

    /**
     * 初始化数据
     *
     * @param context
     */
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_loop_date, this);
        loopViewYear = findViewById(R.id.loopView_year);
        loopViewMonth = findViewById(R.id.loopView_month);
        loopViewDay = findViewById(R.id.loopView_day);
    }

    public void initData() {
        initDataList();

        this.year = getYear();
        this.month = getMonth();
        this.day = getDay();

        loopViewYear.setAdapter(new StringWheelAdapter(listYear));
        loopViewMonth.setAdapter(new StringWheelAdapter(listMonth));
        loopViewDay.setAdapter(new StringWheelAdapter(listDay));

        //默认时间,当前年月日
        for (int i = 0; i < listYear.size(); i++) {
            if (Integer.parseInt(listYear.get(i)) == getYear()) {
                loopViewYear.setCurrentItem(i);
            }
        }
        loopViewMonth.setCurrentItem(getMonth() - 1);
        loopViewDay.setCurrentItem(getDay() - 1);
        setRightMonthCount();
        setRightDayCount();
    }

    public void setDate(int year, int month, int day) {

        initDataList();

        //设置原始数据
        this.year = year;
        this.month = month;
        this.day = day;

        loopViewYear.setAdapter(new StringWheelAdapter(listYear));
        loopViewMonth.setAdapter(new StringWheelAdapter(listMonth));
        loopViewDay.setAdapter(new StringWheelAdapter(listDay));

        //默认时间,当前年月日
        for (int i = 0; i < listYear.size(); i++) {
            if (Integer.parseInt(listYear.get(i)) == year) {
                loopViewYear.setCurrentItem(i);
            }
        }
        loopViewMonth.setCurrentItem(month - 1);
        loopViewDay.setCurrentItem(day - 1);
        setRightMonthCount();
        setRightDayCount();

    }

    private void initEvent() {
        //滚动监听,年份
        loopViewYear.setOnItemSelectedListener(index -> {
            year = Integer.parseInt(listYear.get(index));
            setRightMonthCount();
            setRightDayCount();
            if (year == getYear() && month > getMonth()) {
                month = getMonth();
            }
            if (month < 10 && day < 10) {
                startTime = year + "-0" + month + "-0" + day;
            } else if (month < 10) {
                startTime = year + "-0" + month + "-" + day;
            } else if (day < 10) {
                startTime = year + "-" + month + "-0" + day;
            } else {
                startTime = year + "-" + month + "-" + day;
            }
            //监听
            if (leftScrollListener != null) {
                leftScrollListener.selectData(startTime);
            }
            //监听
            if (rightScrollListener != null) {
                rightScrollListener.selectData(startTime);
            }
        });
        //滚动监听,月份
        loopViewMonth.setOnItemSelectedListener(index -> {
            month = Integer.parseInt(listMonth.get(index));
            setRightMonthCount();
            setRightDayCount();
            if (month < 10 && day < 10) {
                startTime = year + "-0" + month + "-0" + day;
            } else if (month < 10) {
                startTime = year + "-0" + month + "-" + day;
            } else if (day < 10) {
                startTime = year + "-" + month + "-0" + day;
            } else {
                startTime = year + "-" + month + "-" + day;
            }
            //监听
            if (leftScrollListener != null) {
                leftScrollListener.selectData(startTime);
            }
            //监听
            if (rightScrollListener != null) {
                rightScrollListener.selectData(startTime);
            }
        });
        //滚动监听,天数
        loopViewDay.setOnItemSelectedListener(index -> {
            day = Integer.parseInt(listDay.get(index));
            if (month < 10 && day < 10) {
                startTime = year + "-0" + month + "-0" + day;
            } else if (month < 10) {
                startTime = year + "-0" + month + "-" + day;
            } else if (day < 10) {
                startTime = year + "-" + month + "-0" + day;
            } else {
                startTime = year + "-" + month + "-" + day;
            }
            //监听
            if (leftScrollListener != null) {
                leftScrollListener.selectData(startTime);
            }
            //监听
            if (rightScrollListener != null) {
                rightScrollListener.selectData(startTime);
            }
        });

    }

    /**
     * 初始化日期的几个集合数据
     */
    private void initDataList() {
        listYear.clear();
        listMonth.clear();
        listDay.clear();
        int date = TimeUtil.getDateToString();
        //年的时间
        for (int i = 2017; i < date + 1; i++) {
            listYear.add("" + i);
        }
        //月的时间
        for (int i = 1; i < 13; i++) {
            listMonth.add("" + i);
        }
        //日的时间
        for (int i = 1; i < 32; i++) {
            listDay.add("" + i);
        }
    }

    private void setRightMonthCount() {
        int monthCounts = 12;
        if (year == now.get(Calendar.YEAR)) {
            monthCounts = now.get(Calendar.MONTH);
            if (month > monthCounts && monthCounts < 12) {
                month = (monthCounts + 1);
            }
        }
        listMonth.clear();
        //日的时间
        if (monthCounts == 12) {
            for (int i = 1; i <= monthCounts; i++) {
                listMonth.add("" + i);
            }
        } else {
            for (int i = 1; i <= monthCounts + 1; i++) {
                listMonth.add("" + i);
            }
        }
        //如果新老的天数不一样才变换天数
        if (oldMonthCounts != monthCounts) {
            //重新改变天的数量
            loopViewMonth.setAdapter(new StringWheelAdapter(listMonth));
            //判断是否要变更选中的天数，比如选中3-31滑动到2月变成2-28或2-29
            if (monthCounts < month) {
                loopViewMonth.setCurrentItem(monthCounts);
            }
            oldMonthCounts = monthCounts;
        }
    }

    /**
     * 根据年数的月份显示对应的天数
     */
    private void setRightDayCount() {
        int dayCounts = 31;

        if ((year == now.get(Calendar.YEAR)) && (month == (now.get(Calendar.MONTH) + 1))) {
            dayCounts = now.get(Calendar.DAY_OF_MONTH);
        } else if (TimeUtil.isLeapYear(year) && month == 2) {
            //28天的情况，润年2月
            dayCounts = 29;
        } else if (!TimeUtil.isLeapYear(year) && month == 2) {
            //29天的情况，平年2月
            dayCounts = 28;
        } else if (month == 2 || month == 4 || month == 6 || month == 9 || month == 11) {
            //30天的情况，2，4，6，9，11
            dayCounts = 30;
        } else {
            //31天的情况 ，1，3，5，7，8，10，12
            dayCounts = 31;
        }
        listDay.clear();
        //日的时间
        for (int i = 1; i <= dayCounts; i++) {
            listDay.add("" + i);
        }

        //如果新老的天数不一样才变换天数
        if (oldDayCounts != dayCounts) {
            //重新改变天的数量
            loopViewDay.setAdapter(new StringWheelAdapter(listDay));

            //判断是否要变更选中的天数，比如选中3-31滑动到2月变成2-28或2-29
            if (dayCounts < day) {
                loopViewDay.setCurrentItem(dayCounts - 1);
                day = dayCounts;
            }
            oldDayCounts = dayCounts;
        }
    }

    /**
     * 获取得当前时间的天数
     *
     * @return
     */
    public int getDay() {
        return TimeUtil.getTimeInt("dd");
    }

    /**
     * 获得当前时间的月份
     */
    public int getMonth() {
        return TimeUtil.getTimeInt("MM");
    }

    /**
     * 获得当前时间的年份
     */
    public int getYear() {
        return TimeUtil.getTimeInt("yyyy");
    }


    /**
     * 设置滚轮不可以循环，默认是可以循环的
     */

    public void setCyclic(boolean cyclic) {
        loopViewYear.setCyclic(cyclic);
        loopViewMonth.setCyclic(cyclic);
        loopViewDay.setCyclic(cyclic);
    }


    private OnOKClickListener listenerOKClick;

    /**
     * 设置回调监听事件，往外面发送选中的日期字符串
     *
     * @param listenerOKClick
     */
    public void setOKClickListener(OnOKClickListener listenerOKClick) {
        this.listenerOKClick = listenerOKClick;
    }

    /**
     * 滚动时左边Text的回调方法
     */
    public interface OnLeftListenerScroll {
        void selectData(String dataString);
    }

    private OnPickerScrollListener leftScrollListener;


    private OnPickerScrollListener rightScrollListener;

    /**
     * 设置回调监听事件，往外面发送选中的日期字符串
     *
     * @param leftScrollListener
     */
    public void setLeftListenerScroll(OnPickerScrollListener leftScrollListener) {
        this.leftScrollListener = leftScrollListener;
    }

    /**
     * 设置回调监听事件，往外面发送选中的日期字符串
     *
     * @param rightListenerScroll
     */
    public void setRightListenerScroll(OnPickerScrollListener rightListenerScroll) {
        this.rightScrollListener = rightListenerScroll;
    }

}
