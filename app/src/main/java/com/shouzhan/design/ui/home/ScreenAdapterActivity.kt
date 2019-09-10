package com.shouzhan.design.ui.home

import android.os.Bundle
import android.view.View
import com.shouzhan.design.R
import com.shouzhan.design.base.BaseActivity
import com.shouzhan.design.databinding.ActivityScreenAdapterBinding
import com.shouzhan.design.utils.Util
import kotlinx.android.synthetic.main.activity_screen_adapter.*


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
        // 修改屏幕分辨率密度
//        val displayMetrics = resources.displayMetrics
//        displayMetrics.density = 1f
//        displayMetrics.xdpi = 25f
        // 打印设备信息
        Util.printDeviceInfo(this)
        // 打印布局宽高, 以dp为单位
        Util.dumpLayoutParams(test_view_dp.layoutParams)
        // 打印布局宽高, 以mm为单位
        Util.dumpLayoutParams(test_view_mm.layoutParams)
        // 打印tv的字体大小
        Util.dumpTextSize(test_tv_size)
    }

    override fun getData() {

    }

    override fun onClick(view: View?) {
    }

}
