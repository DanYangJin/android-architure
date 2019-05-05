package com.shouzhan.design.ui.home

import android.os.Bundle
import android.view.View
import com.shouzhan.design.R
import com.shouzhan.design.base.BaseActivity
import com.shouzhan.design.compontent.picker.StringWheelAdapter
import com.shouzhan.design.databinding.ActivityNumberPickerBinding
import com.shouzhan.design.utils.Utils
import kotlinx.android.synthetic.main.activity_number_picker.*


/**
 * @author danbin
 * @version NumberPickerActivity.java, v 0.1 2019-02-27 上午12:11 danbin
 */
class NumberPickerActivity : BaseActivity<ActivityNumberPickerBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_number_picker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    override fun initView() {
        var intervalTimes = resources.getIntArray(R.array.member_interval_time)
        wheel_view.isCyclic = false
        wheel_view.currentItem = 1
        wheel_view.setDrawCenterBg(true)
        wheel_view.adapter = StringWheelAdapter(Utils.intTransformStringArray(intervalTimes))
        date_wheel_view.setCyclic(false)
    }

    override fun getData() {

    }

    override fun onClick(view: View) {

    }
}
