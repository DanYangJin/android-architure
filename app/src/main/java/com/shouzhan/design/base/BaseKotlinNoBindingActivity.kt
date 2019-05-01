package com.shouzhan.design.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

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

    override fun extraIntentData() {

    }

    override fun showLoadingView() {

    }

    override fun showEmptyView() {

    }

    override fun showErrorView() {

    }


}
