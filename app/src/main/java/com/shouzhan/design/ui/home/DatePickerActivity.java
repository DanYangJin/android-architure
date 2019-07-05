package com.shouzhan.design.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.shouzhan.design.R;
import com.shouzhan.design.base.BaseActivity;
import com.shouzhan.design.compontent.picker.NumericWheelAdapter;
import com.shouzhan.design.compontent.picker.OnItemSelectedListener;
import com.shouzhan.design.compontent.picker.TimeUtil;
import com.shouzhan.design.databinding.ActivityDatePickerBinding;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @author danbin
 * @version DatePickerActivity.java, v 0.1 2019-07-03 20:48 danbin
 */
public class DatePickerActivity extends BaseActivity<ActivityDatePickerBinding> {

    private static final String[] MONTHS_BIG = {"1", "3", "5", "7", "8", "10", "12"};
    private static final String[] MONTHS_LITTLE = {"4", "6", "9", "11"};

    final List<String> bigMonthList = Arrays.asList(MONTHS_BIG);
    final List<String> littleMonthList = Arrays.asList(MONTHS_LITTLE);

    private static final int DEFAULT_START_YEAR = 1900;
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_START_MONTH = 1;
    private static final int DEFAULT_END_MONTH = 12;
    private static final int DEFAULT_START_DAY = 1;
    private static final int DEFAULT_END_DAY = 31;

    private int startYear = DEFAULT_START_YEAR;
    private int endYear = DEFAULT_END_YEAR;
    private int startMonth = DEFAULT_START_MONTH;
    private int endMonth = DEFAULT_END_MONTH;
    private int startDay = DEFAULT_START_DAY;
    private int endDay = DEFAULT_END_DAY;


    /**
     * 当前选中时间
     * */
    public Calendar selectedDate;
    /**
     * 开始时间
     * */
    public Calendar startDate;
    /**
     * 终止时间
     * */
    public Calendar endDate;

    private int curYear;

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_date_picker;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Override
    public void initView() {
        super.initView();
        selectedDate = Calendar.getInstance();
        startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -36);
        endDate = Calendar.getInstance();
        initDefaultSelectedDate();
        mBinding.yearWheelView.setCyclic(false);
        mBinding.monthWheelView.setCyclic(false);
        mBinding.dayWheelView.setCyclic(false);
        startYear = startDate.get(Calendar.YEAR);
        endYear = endDate.get(Calendar.YEAR);
        startMonth = startDate.get(Calendar.MONTH) + 1;
        endMonth = endDate.get(Calendar.MONTH) + 1;
        startDay = startDate.get(Calendar.DAY_OF_MONTH);
        endDay = endDate.get(Calendar.DAY_OF_MONTH);
        setSolar(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
    }

    private void initDefaultSelectedDate() {
        if (selectedDate != null) {
            return;
        }
        //如果手动设置了时间范围
        if (startDate != null && endDate != null) {
            //若默认时间未设置，或者设置的默认时间越界了，则设置默认选中时间为开始时间。
            if (selectedDate.getTimeInMillis() < startDate.getTimeInMillis()
                    || selectedDate.getTimeInMillis() > endDate.getTimeInMillis()) {
                selectedDate = endDate;
            }
        } else if (startDate != null) {
            //没有设置默认选中时间,那就拿开始时间当默认时间
            selectedDate = startDate;
        } else if (endDate != null) {
            selectedDate = endDate;
        }
    }

    private void setSolar(int year, final int month, int day){
        curYear = year;
        // 设置"年"的显示数据
        mBinding.yearWheelView.setAdapter(new NumericWheelAdapter(startYear, endYear));
        mBinding.yearWheelView.setCurrentItem(year - startYear);
        if (startYear == endYear) {
            //开始年等于终止年
            mBinding.monthWheelView.setAdapter(new NumericWheelAdapter(startMonth, endMonth));
            mBinding.monthWheelView.setCurrentItem(month + 1 - startMonth);
        } else if (year == startYear) {
            //起始日期的月份控制
            mBinding.monthWheelView.setAdapter(new NumericWheelAdapter(startMonth, 12));
            mBinding.monthWheelView.setCurrentItem(month + 1 - startMonth);
        } else if (year == endYear) {
            //终止日期的月份控制
            mBinding.monthWheelView.setAdapter(new NumericWheelAdapter(1, endMonth));
            mBinding.monthWheelView.setCurrentItem(month);
        } else {
            mBinding.monthWheelView.setAdapter(new NumericWheelAdapter(1, 12));
            mBinding.monthWheelView.setCurrentItem(month);
        }
        if (startYear == endYear && startMonth == endMonth) {
            if (bigMonthList.contains(String.valueOf(month + 1))) {
                if (endDay > 31) {
                    endDay = 31;
                }
                mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(startDay, endDay));
            } else if (littleMonthList.contains(String.valueOf(month + 1))) {
                if (endDay > 30) {
                    endDay = 30;
                }
                mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(startDay, endDay));
            } else {
                // 闰年
                if (TimeUtil.isLeapYear(year)) {
                    if (endDay > 29) {
                        endDay = 29;
                    }
                    mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(startDay, endDay));
                } else {
                    if (endDay > 28) {
                        endDay = 28;
                    }
                    mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(startDay, endDay));
                }
            }
            mBinding.dayWheelView.setCurrentItem(day - startDay);
        } else if (year == startYear && month + 1 == startMonth) {
            // 起始日期的天数控制
            if (bigMonthList.contains(String.valueOf(month + 1))) {
                mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(startDay, 31));
            } else if (littleMonthList.contains(String.valueOf(month + 1))) {
                mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(startDay, 30));
            } else {
                // 闰年
                if (TimeUtil.isLeapYear(year)) {
                    mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(startDay, 29));
                } else {
                    mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(startDay, 28));
                }
            }
            mBinding.dayWheelView.setCurrentItem(day - startDay);
        } else if (year == endYear && month + 1 == endMonth) {
            // 终止日期的天数控制
            if (bigMonthList.contains(String.valueOf(month + 1))) {
                if (endDay > 31) {
                    endDay = 31;
                }
                mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(1, endDay));
            } else if (littleMonthList.contains(String.valueOf(month + 1))) {
                if (endDay > 30) {
                    endDay = 30;
                }
                mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(1, endDay));
            } else {
                // 闰年
                if (TimeUtil.isLeapYear(year)) {
                    if (endDay > 29) {
                        endDay = 29;
                    }
                    mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(1, endDay));
                } else {
                    if (endDay > 28) {
                        endDay = 28;
                    }
                    mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(1, endDay));
                }
            }
            mBinding.dayWheelView.setCurrentItem(day - 1);
        } else {
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (bigMonthList.contains(String.valueOf(month + 1))) {
                mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (littleMonthList.contains(String.valueOf(month + 1))) {
                mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(1, 30));
            } else {
                // 闰年
                if (TimeUtil.isLeapYear(year)) {
                    mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(1, 29));
                } else {
                    mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(1, 28));
                }
            }
            mBinding.dayWheelView.setCurrentItem(day - 1);
        }
        // 添加"年"监听
        mBinding.yearWheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int yearNum = index + startYear;
                curYear = yearNum;
                int currentMonthItem = mBinding.monthWheelView.getCurrentItem();
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (startYear == endYear) {
                    //重新设置月份
                    mBinding.monthWheelView.setAdapter(new NumericWheelAdapter(startMonth, endMonth));
                    if (currentMonthItem > mBinding.monthWheelView.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = mBinding.monthWheelView.getAdapter().getItemsCount() - 1;
                        mBinding.monthWheelView.setCurrentItem(currentMonthItem);
                    }
                    int monthNum = currentMonthItem + startMonth;
                    if (startMonth == endMonth) {
                        //重新设置日
                        setReDay(yearNum, monthNum, startDay, endDay);
                    } else if (monthNum == startMonth) {
                        //重新设置日
                        setReDay(yearNum, monthNum, startDay, 31);
                    } else if (monthNum == endMonth) {
                        setReDay(yearNum, monthNum, 1, endDay);
                    } else {//重新设置日
                        setReDay(yearNum, monthNum, 1, 31);
                    }
                } else if (yearNum == startYear) {
                    //重新设置月份
                    mBinding.monthWheelView.setAdapter(new NumericWheelAdapter(startMonth, 12));

                    if (currentMonthItem > mBinding.monthWheelView.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = mBinding.monthWheelView.getAdapter().getItemsCount() - 1;
                        mBinding.monthWheelView.setCurrentItem(currentMonthItem);
                    }
                    int month = currentMonthItem + startMonth;
                    if (month == startMonth) {
                        //重新设置日
                        setReDay(yearNum, month, startDay, 31);
                    } else {
                        //重新设置日
                        setReDay(yearNum, month, 1, 31);
                    }
                } else if (yearNum == endYear) {
                    //重新设置月份
                    mBinding.monthWheelView.setAdapter(new NumericWheelAdapter(1, endMonth));
                    if (currentMonthItem > mBinding.monthWheelView.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = mBinding.monthWheelView.getAdapter().getItemsCount() - 1;
                        mBinding.monthWheelView.setCurrentItem(currentMonthItem);
                    }
                    int monthNum = currentMonthItem + 1;
                    if (monthNum == endMonth) {
                        //重新设置日
                        setReDay(yearNum, monthNum, 1, endDay);
                    } else {
                        //重新设置日
                        setReDay(yearNum, monthNum, 1, 31);
                    }
                } else {
                    //重新设置月份
                    mBinding.monthWheelView.setAdapter(new NumericWheelAdapter(1, 12));
                    //重新设置日
                    setReDay(yearNum, mBinding.monthWheelView.getCurrentItem() + 1, 1, 31);
                }

            }
        });

        // 添加"月"监听
        mBinding.monthWheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int monthNum = index + 1;
                if (startYear == endYear) {
                    monthNum = monthNum + startMonth - 1;
                    if (startMonth == endMonth) {
                        //重新设置日
                        setReDay(curYear, monthNum, startDay, endDay);
                    } else if (startMonth == monthNum) {
                        //重新设置日
                        setReDay(curYear, monthNum, startDay, 31);
                    } else if (endMonth == monthNum) {
                        setReDay(curYear, monthNum, 1, endDay);
                    } else {
                        setReDay(curYear, monthNum, 1, 31);
                    }
                } else if (curYear == startYear) {
                    monthNum = monthNum + startMonth - 1;
                    if (monthNum == startMonth) {
                        //重新设置日
                        setReDay(curYear, monthNum, startDay, 31);
                    } else {
                        //重新设置日
                        setReDay(curYear, monthNum, 1, 31);
                    }
                } else if (curYear == endYear) {
                    if (monthNum == endMonth) {
                        //重新设置日
                        setReDay(curYear, mBinding.monthWheelView.getCurrentItem() + 1, 1, endDay);
                    } else {
                        setReDay(curYear, mBinding.monthWheelView.getCurrentItem() + 1, 1, 31);
                    }
                } else {
                    //重新设置日
                    setReDay(curYear, monthNum, 1, 31);
                }

            }
        });

    }

    @Override
    public void getData() {

    }


    private void setReDay(int yearNum, int monthNum, int startD, int endD) {
        int currentItem = mBinding.dayWheelView.getCurrentItem();

        if (bigMonthList.contains(String.valueOf(monthNum))) {
            if (endD > 31) {
                endD = 31;
            }
            mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(startD, endD));
        } else if (littleMonthList.contains(String.valueOf(monthNum))) {
            if (endD > 30) {
                endD = 30;
            }
            mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(startD, endD));
        } else {
            if (TimeUtil.isLeapYear(yearNum)) {
                if (endD > 29) {
                    endD = 29;
                }
                mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(startD, endD));
            } else {
                if (endD > 28) {
                    endD = 28;
                }
                mBinding.dayWheelView.setAdapter(new NumericWheelAdapter(startD, endD));
            }
        }

        if (currentItem > mBinding.dayWheelView.getAdapter().getItemsCount() - 1) {
            currentItem = mBinding.dayWheelView.getAdapter().getItemsCount() - 1;
            mBinding.dayWheelView.setCurrentItem(currentItem);
        }
    }



}