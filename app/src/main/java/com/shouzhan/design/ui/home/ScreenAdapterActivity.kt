package com.shouzhan.design.ui.home

import android.os.Bundle
import android.view.View
import com.shouzhan.design.R
import com.shouzhan.design.base.BaseActivity
import com.shouzhan.design.databinding.ActivityScreenAdapterBinding


/**
 * @author danbin
 * @version ScreenAdapterActivity.java, v 0.1 2019-02-27 上午12:11 danbin
 */
class ScreenAdapterActivity : BaseActivity<ActivityScreenAdapterBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_screen_adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    override fun getData() {

    }

    override fun onClick(view: View?) {
    }

}
