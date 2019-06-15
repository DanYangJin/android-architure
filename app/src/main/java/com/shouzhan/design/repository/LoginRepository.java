package com.shouzhan.design.repository;

import com.shouzhan.design.datasource.http.ApiService;
import com.shouzhan.design.model.remote.request.LoginRequest;
import com.shouzhan.design.base.BaseResult;
import com.shouzhan.design.model.remote.result.LoginResult;

import io.reactivex.Observable;

/**
 * @author danbin
 * @version LoginRepository.java, v 0.1 2019-02-21 下午5:59 danbin
 */
public class LoginRepository {

    public ApiService mApiService;

    public LoginRepository () {
        this.mApiService = ApiService.Builder.getJavaPerformanceServer();
    }

    public Observable<BaseResult<LoginResult>> toLogin(String username, String password) {
        return mApiService.login(new LoginRequest(username, password));
    }


}
