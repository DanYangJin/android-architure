package com.shouzhan.design.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.shouzhan.design.BR;

/**
 * @author danbin
 * @version BaseActivity.java, v 0.1 2019-02-26 下午11:23 danbin
 */
abstract class BaseActivity<VB extends ViewDataBinding> extends AppCompatActivity implements BaseViewPresenter, BaseBindingPresenter {

    protected Context mContext;
    protected VB mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extraIntentData();
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mBinding.setVariable(BR.presenter, this);
        mBinding.setLifecycleOwner(this);
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
