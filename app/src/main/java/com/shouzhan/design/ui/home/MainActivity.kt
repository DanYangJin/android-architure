package com.shouzhan.design.ui.home

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import com.shouzhan.design.App
import com.shouzhan.design.R
import com.shouzhan.design.base.BaseKotlinActivity
import com.shouzhan.design.databinding.ActivityMainBinding
import com.shouzhan.design.ui.home.viewmodel.MainViewModel

/**
 * @author danbin
 * @version MainActivity.java, v 0.1 2019-02-27 上午12:11 danbin
 */
class MainActivity : BaseKotlinActivity<ActivityMainBinding>() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider.AndroidViewModelFactory.
                getInstance(App.getInstance()).create(MainViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.vm = viewModel
        getData()
    }

    override fun initView() {

    }

    override fun getData() {
        viewModel!!.refreshHeadImage("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1551108895&di=745684775de1e4b78f063fd0785ea90f&src=http://pic5.nipic.com/20100127/2177138_082501971985_2.jpg")
    }

    override fun onClick(view: View) {

    }

}
