package com.shouzhan.design.repository;

import com.shouzhan.design.datasource.http.ApiService;
import com.shouzhan.design.ui.kotlin.model.remote.request.UserListRequest;
import com.shouzhan.design.base.BasePageResult;
import com.shouzhan.design.base.BaseResult;
import com.shouzhan.design.ui.kotlin.model.remote.result.UserListResult;

import io.reactivex.Observable;

/**
 * @author danbin
 * @version UserRepository.java, v 0.1 2019-02-21 下午5:59 danbin
 */
public class UserListRepository {

    public ApiService mApiService;

    public UserListRepository() {
        this.mApiService = ApiService.Builder.getJavaPerformanceServer();
    }

    public Observable<BaseResult<BasePageResult<UserListResult>>> getUserList(int page) {
        UserListRequest request = new UserListRequest();
        request.setPage(page);
        return mApiService.getUserList(request.getPage(), request.getPageSize(), null);
    }


}
