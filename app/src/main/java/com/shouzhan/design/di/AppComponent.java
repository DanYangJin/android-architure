package com.shouzhan.design.di;

import com.shouzhan.design.ui.home.DraggerActivity;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

import javax.inject.Singleton;

/**
 * @author danbin
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class
})
public interface AppComponent {

    /**
     * inject
     *
     * @param activity
     * @return
     * */
    void inject(DraggerActivity activity);

}