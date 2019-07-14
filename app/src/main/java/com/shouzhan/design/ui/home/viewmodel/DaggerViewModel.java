package com.shouzhan.design.ui.home.viewmodel;

import com.shouzhan.design.base.BasePageResult;
import com.shouzhan.design.base.BaseViewModel;
import com.shouzhan.design.datasource.http.performance.PerformanceApiCallback;
import com.shouzhan.design.model.remote.result.UserListResult;
import com.shouzhan.design.repository.DaggerRepository;

import javax.inject.Inject;


/**
 * @author danbin
 * @version DaggerViewModel.java, v 0.1 2019-02-27 上午12:18 danbin
 */
public class DaggerViewModel extends BaseViewModel {

    public DaggerRepository repository;

    @Inject
    public DaggerViewModel(DaggerRepository repository) {
        this.repository = repository;
    }

    public void requestDaggerData() {
        addSubscribe(repository.getUserList(1), new PerformanceApiCallback<BasePageResult<UserListResult>>() {
            @Override
            protected void onSuccess(BasePageResult<UserListResult> data) {

            }
        });
    }

}
