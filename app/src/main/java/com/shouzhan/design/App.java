package com.shouzhan.design;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.kit.webdoor.WebDoorManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.shouzhan.design.ui.h5.CommonH5Activity;
import com.shouzhan.design.datasource.local.Prefs;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

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
        Prefs.init(this);
        configUnits();
        initDoraemonKit();
    }

    public static App getInstance() {
        return INSTANCE;
    }

    private void configUnits() {
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false).setSupportSubunits(Subunits.MM);
    }

    private void initDoraemonKit() {
        DoraemonKit.install(this);
        DoraemonKit.setWebDoorCallback(new WebDoorManager.WebDoorCallback() {
            @Override
            public void overrideUrlLoading(Context context, String url) {
                CommonH5Activity.Companion.run(context, "http://www.baidu.com");
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
