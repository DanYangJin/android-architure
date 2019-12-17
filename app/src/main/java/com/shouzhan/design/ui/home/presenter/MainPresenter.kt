package com.shouzhan.design.ui.home.presenter


import android.content.Context
import android.databinding.ViewDataBinding
import com.shouzhan.design.base.BasePresenter
import com.shouzhan.design.databinding.ActivityMainBinding
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

    init {
        initObserver()
    }

    override fun initUI() {

    }

    override fun initObserver() {
        (mBinding as ActivityMainBinding).vm = viewModel
    }

    override fun updateSameLiveData() {
        viewModel.inputTxt.set("哈哈哈哈")
    }


    override fun refreshHeadImage(headUrl: String) {
        viewModel.headImage.set(headUrl)
    }

}