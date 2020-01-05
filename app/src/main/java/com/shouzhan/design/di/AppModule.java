package com.shouzhan.design.di;

import androidx.lifecycle.ViewModelProvider;
import com.shouzhan.design.datasource.http.ApiService;
import com.shouzhan.design.model.javabean.UserInfo;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(subcomponents = ViewModelSubComponent.class)
class AppModule {

    @Singleton
    @Provides
    UserInfo provideUserInfo() {
        return new UserInfo("danyangjin", 18);
    }

    @Singleton
    @Provides
    ApiService provideApiService() {
        return ApiService.Builder.getJavaPerformanceServer();
    }

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(ViewModelSubComponent.Builder viewModelSubComponent) {
        return new ViewModelFactory(viewModelSubComponent.build());
    }

}
