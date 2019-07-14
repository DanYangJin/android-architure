package com.shouzhan.design.di;

import com.shouzhan.design.model.javabean.Student;
import dagger.Module;
import dagger.Provides;


/**
 * @author danbin
 * @version AppModule.java, v 0.1 2019-07-14 13:20 danbin
 */
@Module
class AppModule {

    @Provides
    static Student provideStudent() {
        return new Student();
    }

}
