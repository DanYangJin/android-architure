package com.shouzhan.design.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.DaggerActivity;

/**
 * @author danbin
 * @version ActivityBuildersModule.java, v 0.1 2019-07-14 13:20 danbin
 */
@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract DaggerActivity contributeDaggerActivityInjector();

}
