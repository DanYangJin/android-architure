package com.shouzhan.design.di;

import com.shouzhan.design.App;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

import javax.inject.Singleton;

/**
 * @author danbin
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
         * @param app
         * @return
         */
        @BindsInstance
        Builder application(App app);

        /**
         * build
         *
         * @return
         */
        AppComponent build();
    }

    /**
     * inject
     *
     * @param app
     * @return
     */
    void inject(App app);
}
