package com.shouzhan.design.di;

import android.app.Application;
import com.shouzhan.design.App;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

import javax.inject.Singleton;

/**
 * @author danbin
 * @version AppComponent.java, v 0.1 2019-07-14 13:20 danbin
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityBuildersModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        /**
         * application
         *
         * @param application
         * @return
         */
        @BindsInstance
        Builder application(Application application);

        /**
         * build
         *
         * @return
         */
        AppComponent build();
    }

    /**
     * build
     *
     * @param app
     */
    void inject(App app);
}