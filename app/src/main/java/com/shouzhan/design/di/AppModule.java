package com.shouzhan.design.di;

import android.arch.lifecycle.ViewModelProvider;
import com.shouzhan.design.datasource.http.ApiService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * @author danbin
 * @version AppModule.java, v 0.1 2019-02-24 下午4:05 danbin
 */
@Module
class AppModule {

    @Singleton
    @Provides
    ApiService provideApiService() {
        return ApiService.Builder.getJavaPerformanceServer();
    }

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(
            ViewModelSubComponent.Builder viewModelSubComponent) {
        return new ViewModelsFactory(viewModelSubComponent.build());
    }


}
