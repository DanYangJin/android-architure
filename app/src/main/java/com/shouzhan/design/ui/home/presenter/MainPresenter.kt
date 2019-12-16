package com.shouzhan.design.ui.home.presenter


import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Context
import android.databinding.ViewDataBinding
import com.fshows.android.stark.utils.FsLogUtil
import com.shouzhan.design.base.BasePresenter
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
        viewModel.sameLiveData.observe(mContext as LifecycleOwner, Observer {
            FsLogUtil.error("Catch", "it: $it")
        })
    }

    override fun updateSameLiveData() {
        viewModel.sameLiveData.value = "哈哈哈"
    }


}