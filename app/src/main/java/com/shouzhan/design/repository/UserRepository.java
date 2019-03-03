package com.shouzhan.design.repository;

import com.shouzhan.design.datasource.http.ApiService;
import com.shouzhan.design.datasource.http.RetrofitUtils;
import com.shouzhan.design.model.remote.request.UserListRequest;
import com.shouzhan.design.model.remote.result.BasePageResult;
import com.shouzhan.design.model.remote.result.BaseResult;
import com.shouzhan.design.model.remote.result.UserListResult;

import io.reactivex.Observable;

/**
 * @author danbin
 * @version UserRepository.java, v 0.1 2019-02-21 下午5:59 danbin
 */
public class UserRepository {

    public ApiService mApiService;

    public UserRepository() {
        this.mApiService = RetrofitUtils.getService();
    }

    public Observable<BaseResult<BasePageResult<UserListResult>>> getUserList(int page) {
        UserListRequest request = new UserListRequest();
        request.setPage(page);
        return mApiService.getUserList(request.getPage(), request.getPageSize(), null);
    }


}
