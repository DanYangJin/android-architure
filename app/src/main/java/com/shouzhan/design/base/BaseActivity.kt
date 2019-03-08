package com.shouzhan.design.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shouzhan.design.BR

/**
 * @author danbin
 * @version BaseActivity.java, v 0.1 2019-02-26 下午11:23 danbin
 */
abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity(), BaseViewPresenter, BaseBindingPresenter {

    protected lateinit var mContext: Context
    protected lateinit var mBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extraIntentData()
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mBinding.setVariable(BR.presenter, this)
        mBinding.executePendingBindings()
        mBinding.setLifecycleOwner(this)
        initView()
        mContext = this
    }

    override fun showLoadingView() {

    }

    override fun showEmptyView() {

    }

    override fun showErrorView() {

    }


}
