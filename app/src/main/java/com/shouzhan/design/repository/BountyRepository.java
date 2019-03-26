package com.shouzhan.design.repository;

import com.shouzhan.design.datasource.http.ApiService;
import com.shouzhan.design.model.remote.result.BountyInfoResult;
import com.shouzhan.design.model.remote.result.JavaBaseResult;

import io.reactivex.Observable;

/**
 * @author danbin
 * @version BountyRepository.java, v 0.1 2019-02-21 下午5:59 danbin
 */
public class BountyRepository {

    public ApiService mApiService;

    public BountyRepository() {
        this.mApiService = ApiService.Builder.getJavaServer();
    }

    public Observable<JavaBaseResult<BountyInfoResult>> getBountyData() {
        return mApiService.getBountyData();
    }


}
