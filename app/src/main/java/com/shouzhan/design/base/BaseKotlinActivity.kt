package com.shouzhan.design.base

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import com.shouzhan.design.App
import com.shouzhan.design.BR


/**
 * @author danbin
 * @version BaseKotlinActivity.java, v 0.1 2019-02-26 下午11:23 danbin
 */
abstract class BaseKotlinActivity<VB : ViewDataBinding> : AppCompatActivity(), BaseViewPresenter, BaseBindingPresenter {

    protected lateinit var mContext: Context
    protected lateinit var mBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extraIntentData()
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mBinding.setVariable(BR.presenter, this)
        mBinding.setLifecycleOwner(this)
        initView()
        mContext = this
    }

    /**
     * 获取ViewModel
     * @param modelClass
     * @param <T>
     * @return
     */
    protected fun <T : BaseViewModel> vmProviders(@NonNull modelClass: Class<T>): T {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(App.getInstance()).create(modelClass)
    }

    override fun extraIntentData() {

    }

    override fun showLoadingView() {

    }

    override fun showEmptyView() {

    }

    override fun showErrorView() {

    }


}
