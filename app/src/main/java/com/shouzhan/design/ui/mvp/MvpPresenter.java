package com.shouzhan.design.ui.mvp;

import android.support.annotation.Nullable;

/**
 * @author danbin
 * @version MvpPresenter.java, v 0.1 2019-07-17 00:40 danbin
 */
public class MvpPresenter implements MvpContract.Presenter {

    @Nullable
    private MvpContract.View mvpView;

    @Override
    public void takeView(MvpContract.View view) {
        this.mvpView = view;
    }

    @Override
    public void dropView() {
        this.mvpView = null;
    }

}
