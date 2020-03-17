package com.shouzhan.design.ui.home.model;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author danbin
 * @version MonitorHandler.java, v 0.1 2020-03-15 3:38 PM danbin
 */
public class MonitorHandler implements InvocationHandler {

    private Object mObject;

    public MonitorHandler(Object object) {
        this.mObject = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.e("xss", "开始执行方法啦");
        Object object = method.invoke(mObject, args);
        Log.e("xss", "结束执行方法啦");
        return object;
    }

}
