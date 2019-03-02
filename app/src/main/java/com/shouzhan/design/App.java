package com.shouzhan.design;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.shouzhan.design.compontent.recyclerview.TwinklingRefreshLayout;
import com.shouzhan.design.compontent.view.FooterView;
import com.shouzhan.design.compontent.view.HeaderView;

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
        TwinklingRefreshLayout.setDefaultHeader(HeaderView.class.getName());
        TwinklingRefreshLayout.setDefaultFooter(FooterView.class.getName());
        INSTANCE = this;
    }

    public static App getInstance() {
        return INSTANCE;
    }

}
