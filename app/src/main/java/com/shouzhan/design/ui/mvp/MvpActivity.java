package com.shouzhan.design.ui.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.fshows.android.stark.utils.FsLogUtil;
import com.shouzhan.design.R;
import com.shouzhan.design.base.BaseActivity;
import com.shouzhan.design.databinding.ActivityMvpBinding;

/**
 * @author danbin
 * @version MvpActivity.java, v 0.1 2019-07-17 00:38 danbin
 */
public class MvpActivity extends BaseActivity<ActivityMvpBinding> implements MvpContract.View {

    private static final String TAG = MvpActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MvpPresenter(mContext, mBinding.getRoot(), this);
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
        FsLogUtil.error(TAG, "updateTitleBar: " + title);
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
