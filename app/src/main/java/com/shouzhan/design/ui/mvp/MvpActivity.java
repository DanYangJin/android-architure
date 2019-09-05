package com.shouzhan.design.ui.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.shouzhan.design.R;
import com.shouzhan.design.base.BaseActivity;
import com.shouzhan.design.databinding.ActivityMvpBinding;

/**
 * @author danbin
 * @version MvpActivity.java, v 0.1 2019-07-17 00:38 danbin
 */
public class MvpActivity extends BaseActivity<ActivityMvpBinding> implements MvpContract.View {

    private MvpPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MvpPresenter(mContext, mBinding, this);
        mBinding.setMp(mPresenter);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mPresenter.onKeyUp(keyCode, event)) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    public void getData() {

    }

    @Override
    public void initView() {
        super.initView();
    }


    @Override
    public void updateTitleBar(String title) {
        mBinding.titleTv.setText(title);
    }

    @Override
    public void showLoading() {
        showLoadingView();
    }

    @Override
    public void hideLoading() {

    }


}
