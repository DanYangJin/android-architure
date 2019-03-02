package com.shouzhan.design.base

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author danbin
 * @version BaseNoBindingActivity.java, v 0.1 2019-02-26 下午11:25 danbin
 */
abstract class BaseNoBindingActivity : AppCompatActivity(), BaseImpl {

    protected lateinit var mContext: Context

    /**
     * 设置布局
     *
     * @return
     */
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extraIntentData()
        setContentView(layoutId)
        mContext = this
        initView()
    }

    /**
     * 初始化UI
     */
    protected abstract fun initView()

    /**
     * 获取请求数据
     */
    protected abstract fun getData()

    /**
     * 页面数据传递
     */
    private fun extraIntentData() {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }


}
