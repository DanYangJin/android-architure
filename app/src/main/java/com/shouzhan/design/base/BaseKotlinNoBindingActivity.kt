package com.shouzhan.design.base

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import com.shouzhan.design.App

/**
 * @author danbin
 * @version BaseNoBindingActivity.java, v 0.1 2019-02-26 下午11:25 danbin
 */
abstract class BaseKotlinNoBindingActivity : AppCompatActivity(), BaseViewPresenter {

    protected lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extraIntentData()
        setContentView(getLayoutId())
        mContext = this
        initView()
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
