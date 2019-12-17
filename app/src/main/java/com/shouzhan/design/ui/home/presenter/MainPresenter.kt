package com.shouzhan.design.ui.home.presenter


import android.content.Context
import android.databinding.ViewDataBinding
import com.fshows.android.stark.utils.FsLogUtil
import com.shouzhan.design.base.BasePresenter
import com.shouzhan.design.databinding.ActivityMainBinding
import com.shouzhan.design.model.javabean.InputInfo
import com.shouzhan.design.ui.home.contract.MainContract
import com.shouzhan.design.ui.home.viewmodel.MainViewModel

/**
 * @author danbin
 * @version MainPresenter.java, v 0.1 2019-12-13 17:14 danbin
 */

class MainPresenter(context: Context?, view: MainContract.View?, binding: ViewDataBinding?) : BasePresenter<Context, MainContract.View, ViewDataBinding>(context, view, binding), MainContract.Presenter {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        vmProviders(MainViewModel::class.java)
    }

    private val input by lazy(LazyThreadSafetyMode.NONE) {
        InputInfo()
    }

    init {
        initObserver()
    }

    override fun initUI() {

    }

    override fun initObserver() {
        (mBinding as ActivityMainBinding).vm = viewModel
        (mBinding as ActivityMainBinding).input = input
    }

    override fun updateLiveData() {
        viewModel.inputTxt.set("哈哈哈哈")
    }


    override fun refreshHeadImage(headUrl: String) {
        viewModel.headImage.set(headUrl)
    }

    override fun showLiveData() {
        FsLogUtil.error("Catch", "showLiveData: ${input.inputTxt}")
    }

}