package com.shouzhan.design.di;

import com.shouzhan.design.ui.home.DaggerActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author danbin
 */
@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector()
    abstract DaggerActivity contributeDaggerActivity();
}
