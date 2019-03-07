package com.shouzhan.design;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author danbin
 * @version App.java, v 0.1 2019-02-24 下午4:24 danbin
 */
public class App extends Application {

    private static App INSTANCE = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
        INSTANCE = this;
    }

    public static App getInstance() {
        return INSTANCE;
    }

}
