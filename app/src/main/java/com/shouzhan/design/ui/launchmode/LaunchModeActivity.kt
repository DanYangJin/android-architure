package com.shouzhan.design.ui.launchmode

import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.shouzhan.design.R
import com.shouzhan.design.base.BaseActivity
import com.shouzhan.design.databinding.ActivityLaunchModeBinding


/**
 * @author danbin
 * @version LaunchModeActivity.java, v 0.1 2019-02-27 上午12:11 danbin
 */
class LaunchModeActivity : BaseActivity<ActivityLaunchModeBinding>() {

    override fun onClick(view: View?) {
    }

    override fun getLayoutId(): Int = R.layout.activity_launch_mode

    override fun initView() {
        super.initView()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.e("xss", "activity dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e("xss", "activity onTouchEvent")
        return super.onTouchEvent(event)
    }

    override fun getData() {

    }


}
