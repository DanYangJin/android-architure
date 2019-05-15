package com.shouzhan.design.utils;

import android.content.Context;

import com.didichuxing.doraemonkit.kit.Category;
import com.didichuxing.doraemonkit.kit.IKit;
import com.shouzhan.design.R;

/**
 * @author danbin
 * @version EnvSwitchKit.java, v 0.1 2019-02-24 下午4:24 danbin
 */
public class EnvSwitchKit implements IKit {

    @Override
    public int getCategory() {
        return Category.BIZ;
    }
 
    @Override
    public int getName() {
        return R.string.env_switch;
    }
 
    @Override
    public int getIcon() {
        return R.drawable.ic_launcher_background;
    }
 
    @Override
    public void onClick(Context context) {

    }
 
    @Override
    public void onAppInit(Context context) {
    
    }
}