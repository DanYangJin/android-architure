package com.shouzhan.design;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.kit.IKit;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.shouzhan.design.callback.impl.CommonActivityLifecycleCallbacks;
import com.shouzhan.design.ui.h5.CommonH5Activity;
import com.shouzhan.design.utils.Constants;
import com.shouzhan.design.utils.EnvSwitchKit;
import com.shouzhan.framework.utils.Prefs;

import java.util.ArrayList;
import java.util.List;

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
        registerActivityLifecycleCallbacks(new CommonActivityLifecycleCallbacks());
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
        List<IKit> selfKits = new ArrayList<>();
        selfKits.add(new EnvSwitchKit());
        DoraemonKit.install(this, selfKits);
        DoraemonKit.setWebDoorCallback((context, url) -> {
                    Intent intent = new Intent(App.this, CommonH5Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constants.EXTRA_COMMON_H5_URL, "http://www.baidu.com");
                    startActivity(intent);
                }
        );
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
