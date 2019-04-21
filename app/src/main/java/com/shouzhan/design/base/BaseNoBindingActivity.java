package com.shouzhan.design.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author danbin
 * @version BaseActivity.java, v 0.1 2019-02-26 下午11:23 danbin
 */
abstract class BaseNoBindingActivity extends AppCompatActivity implements BaseViewPresenter, BaseBindingPresenter {

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extraIntentData();
        setContentView(getLayoutId());
        initView();
        mContext = this;
    }

    @Override
    public void extraIntentData() {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView() {

    }

}
