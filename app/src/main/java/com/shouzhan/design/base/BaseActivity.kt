package com.shouzhan.design.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import com.shouzhan.design.BR

/**
 * @author danbin
 * @version BaseActivity.java, v 0.1 2019-02-26 下午11:23 danbin
 */
abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity(), BaseImpl, BaseBindingPresenter {

    protected lateinit var mContext: Context
    protected lateinit var mBinding: VB

    /**
     * 设置布局
     *
     * @return
     */
    @LayoutRes
    protected abstract fun getLayoutId() : Int

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
