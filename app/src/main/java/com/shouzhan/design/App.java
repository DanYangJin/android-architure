package com.shouzhan.design;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.shouzhan.design.utils.NewPrefs;

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
        NewPrefs.init(this);
        configUnits();
    }

    public static App getInstance() {
        return INSTANCE;
    }

    private void configUnits() {
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false).setSupportSubunits(Subunits.MM);
    }

}
