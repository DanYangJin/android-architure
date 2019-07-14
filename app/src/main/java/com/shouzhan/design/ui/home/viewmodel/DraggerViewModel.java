package com.shouzhan.design.ui.home.viewmodel;

import com.fshows.android.stark.utils.FsLogUtil;
import com.shouzhan.design.base.BaseViewModel;
import com.shouzhan.design.datasource.http.performance.PerformanceApiCallback;
import com.shouzhan.design.repository.DraggerRepository;

import javax.inject.Inject;

/**
 * @author danbin
 * @version HomeViewModel.java, v 0.1 2019-07-14 08:56 danbin
 */
public class DraggerViewModel extends BaseViewModel {

    private static final String TAG = DraggerViewModel.class.getSimpleName();

    private DraggerRepository repository;

    @Inject
    public DraggerViewModel(DraggerRepository repository) {
        this.repository = repository;
    }

    public void requestHomeData(int page) {
        addSubscribe(repository.getUserList(page), new PerformanceApiCallback() {
            @Override
            protected void onSuccess(Object data) {
                FsLogUtil.error(TAG, "requestHomeData");
            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

}
