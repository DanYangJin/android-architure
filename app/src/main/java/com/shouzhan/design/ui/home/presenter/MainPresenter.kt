package com.shouzhan.design.ui.home.presenter


import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Context
import android.databinding.ViewDataBinding
import com.fshows.android.stark.utils.FsLogUtil
import com.shouzhan.design.base.BasePresenter
import com.shouzhan.design.databinding.ActivityMainBinding
import com.shouzhan.design.model.javabean.InputInfo
import com.shouzhan.design.ui.home.contract.MainContract
import com.shouzhan.design.ui.home.viewmodel.MainViewModel
import com.shouzhan.design.utils.LocationObserver

/**
 * @author danbin
 * @version MainPresenter.java, v 0.1 2019-12-13 17:14 danbin
 */

class MainPresenter(context: Context?, view: MainContract.View?, binding: ViewDataBinding?, viewModel: MainViewModel?) : BasePresenter<Context, MainContract.View, ViewDataBinding, MainViewModel>(context, view, binding, viewModel), MainContract.Presenter {

    private val locationObserver by lazy(LazyThreadSafetyMode.NONE) {
        LocationObserver(mLifecycle)
    }


    private val input by lazy(LazyThreadSafetyMode.NONE) {
        InputInfo()
    }

    init {
        mLifecycle.addObserver(locationObserver)
        initView()
        initObserver()
    }

    override fun initView() {
        (mBinding as ActivityMainBinding).vm = mViewModel
        (mBinding as ActivityMainBinding).input = input
    }

    override fun initObserver() {
        locationObserver.location.observe(mContext as LifecycleOwner, Observer {
            FsLogUtil.error("Catch", "location: $it")
        })
    }

    override fun updateLiveData() {
        mViewModel.inputTxt.set("哈哈哈哈")
    }


    override fun refreshHeadImage(headUrl: String) {
        mViewModel.headImage.set(headUrl)
    }

    override fun showLiveData() {
        FsLogUtil.error("Catch", "showLiveData: ${input.inputTxt}")
    }

}