package com.shouzhan.design.ui.home

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import com.shouzhan.design.R
import com.shouzhan.design.base.BaseActivity
import com.shouzhan.design.databinding.ActivityMainBinding
import com.shouzhan.design.ui.home.viewmodel.DraggerViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


/**
 * @author danbin
 * @version DraggerActivity.java, v 0.1 2019-02-27 上午12:11 danbin
 */
class DraggerActivity : BaseActivity<ActivityMainBinding>(), HasActivityInjector {

    override fun onClick(view: View?) {
    }

    @Inject
    internal var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null

    override fun activityInjector(): AndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory

    private val homeViewModel by lazy(LazyThreadSafetyMode.NONE) {
        viewModelFactory.create(DraggerViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_dragger
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    override fun initView() {
        super.initView()
        select_btn.setOnClickListener(this)
    }

    override fun getData() {
        homeViewModel.requestHomeData(1)
    }

}
