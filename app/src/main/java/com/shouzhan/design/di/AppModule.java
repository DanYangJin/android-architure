package com.shouzhan.design.di;

import com.shouzhan.design.model.javabean.Student;
import dagger.Module;
import dagger.Provides;

/**
 * @author danbin
 * @version DraggerModule.java, v 0.1 2019-07-14 13:35 danbin
 */
@Module
public class AppModule {

    @Provides
    Student providesStudent() {
        return new Student();
    }
}