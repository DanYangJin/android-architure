package com.shouzhan.design;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.didichuxing.doraemonkit.DoraemonKit;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.fshows.android.stark.utils.Prefs;
import com.shouzhan.design.callback.impl.CommonActivityLifecycleCallbacks;
import com.shouzhan.design.di.AppInjector;
import com.shouzhan.design.ui.h5.CommonH5Activity;
import com.shouzhan.design.utils.Constants;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

/**
 * @author danbin
 * @version App.java, v 0.1 2019-02-24 下午4:24 danbin
 */
public class App extends Application implements HasActivityInjector {

    private static App INSTANCE = null;
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new CommonActivityLifecycleCallbacks());
        Fresco.initialize(getApplicationContext());
        initStetho();
        INSTANCE = this;
        Prefs.init(this);
        configUnits();
        initDoraemonKit();

        AppInjector.init(this);
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
        DoraemonKit.setWebDoorCallback((context, url) -> {
                    Intent intent = new Intent(App.this, CommonH5Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constants.EXTRA_COMMON_H5_URL, "http://www.baidu.com");
                    startActivity(intent);
                }
        );
    }

    private void initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(getApplicationContext());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}
