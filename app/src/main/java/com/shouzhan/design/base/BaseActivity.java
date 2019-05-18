package com.shouzhan.design.base;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.shouzhan.design.App;
import com.shouzhan.design.BR;

/**
 * @author danbin
 * @version BaseActivity.java, v 0.1 2019-02-26 下午11:23 danbin
 */
public abstract class BaseActivity<VB extends ViewDataBinding> extends AppCompatActivity implements BaseViewPresenter, BaseBindingPresenter {

    private static final String TAG = BaseActivity.class.getSimpleName();

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

    /**
     * 获取ViewModel
     *
     * @param modelClass
     * @param <T>
     * @return
     */
    protected <T extends BaseViewModel> T vmProviders(@NonNull Class<T> modelClass) {
        T viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(App.getInstance()).create(modelClass);
        viewModel.observerPageStatus().observe(this, pageStatus -> {
            switch (pageStatus) {
                case EMPTY:
                    showEmptyView();
                    break;
                case ERROR:
                    showEmptyView();
                    break;
                default:
                    break;
            }
        });
        return viewModel;
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
