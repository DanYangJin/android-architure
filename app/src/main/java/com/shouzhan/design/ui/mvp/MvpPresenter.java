package com.shouzhan.design.ui.mvp;

import android.support.annotation.Nullable;

/**
 * @author danbin
 * @version MvpPresenter.java, v 0.1 2019-07-17 00:40 danbin
 */
public class MvpPresenter implements MvpContract.Presenter {

    @Nullable
    private MvpContract.View mvpView;
//    @Nullable
//    private MvpViewModel mvpViewModel;
//
//    public MvpPresenter(MvpViewModel mvpViewModel) {
//        this.mvpViewModel = mvpViewModel;
//    }

    @Override
    public void bindView(MvpContract.View view) {
        this.mvpView = view;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void switchTitle() {
        mvpView.updateTitleBar("飞飞飞");
    }

}
