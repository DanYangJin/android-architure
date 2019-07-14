package com.shouzhan.design.repository;

import com.shouzhan.design.base.BasePageResult;
import com.shouzhan.design.base.BaseResult;
import com.shouzhan.design.datasource.http.ApiService;
import com.shouzhan.design.model.remote.request.UserListRequest;
import com.shouzhan.design.model.remote.result.UserListResult;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author danbin
 * @version HomeRepository.java, v 0.1 2019-02-21 下午5:59 danbin
 */
@Singleton
public class DraggerRepository {

    public ApiService mApiService;

    @Inject
    public DraggerRepository(ApiService apiService) {
        this.mApiService = apiService;
    }

    public Observable<BaseResult<BasePageResult<UserListResult>>> getUserList(int page) {
        UserListRequest request = new UserListRequest();
        request.setPage(page);
        return mApiService.getUserList(request.getPage(), request.getPageSize(), null);
    }


}
