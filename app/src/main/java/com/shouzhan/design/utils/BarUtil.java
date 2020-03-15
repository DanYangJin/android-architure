package com.shouzhan.design.utils;

/**
 * @author danbin
 * @version BarUtil.java, v 0.1 2019-12-27 23:14 danbin
 * 系统状态栏以及导航栏等工具类
 */
public class BarUtil {

    // 单例模式

    private volatile static BarUtil mBarUtil;

    private BarUtil() {

    }

    public static BarUtil getInstance() {
        if (mBarUtil == null) {
            synchronized (BarUtil.class) {
                if (mBarUtil == null) {
                    mBarUtil = new BarUtil();
                }
            }
        }
        return mBarUtil;
    }



}
