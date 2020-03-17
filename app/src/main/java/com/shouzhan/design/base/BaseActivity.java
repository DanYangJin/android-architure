package com.shouzhan.design.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.gyf.immersionbar.ImmersionBar;
import com.shouzhan.design.App;
import com.shouzhan.design.BR;
import com.shouzhan.design.R;

/**
 * @author danbin
 * @version BaseActivity.java, v 0.1 2019-02-26 下午11:23 danbin
 */
public abstract class BaseActivity<VB extends ViewDataBinding> extends AppCompatActivity implements IBasePagePresenter, Presenter {

    protected Context mContext;
    protected VB mBinding;

    private View mEmptyView = null;
    private View mErrorView = null;
    private View mLoadingView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("xss", "onCreate");
        extraIntentData();
        mContext = this;
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mBinding.setVariable(BR.presenter, this);
        mBinding.setLifecycleOwner(this);
        initView();
        initImmersionBar();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("xss", "onNewIntent");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("xss", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("xss", "onRestart");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("xss", "onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("xss", "onResume");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e("xss", "onConfigurationChanged");
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimary)
                .fitsSystemWindows(true)
                .init();
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
        viewModel.getViewStatus().observe(this, viewStatus -> {
            assert viewStatus != null;
            switch (viewStatus) {
                case EMPTY:
                    showEmptyView();
                    break;
                case ERROR:
                    showErrorView();
                    break;
                case LOADING:
                    showLoadingView();
                    break;
                default:
                    break;
            }
        });
        return viewModel;
    }

    @Override
    public void initView() {
        mEmptyView = findViewById(R.id.empty_rl);
        mErrorView = findViewById(R.id.error_rl);
        mLoadingView = findViewById(R.id.loading_rl);
    }

    @Override
    public void extraIntentData() {

    }

    @Override
    public void showLoadingView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void showEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
        if (mErrorView != null) {
            mErrorView.setVisibility(View.VISIBLE);
        }
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("xss", "onPause");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("xss", "onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("xss", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("xss", "onDestroy");
    }


}
