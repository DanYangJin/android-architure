package com.shouzhan.design.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import com.shouzhan.design.R
import com.shouzhan.design.base.BaseKotlinActivity
import com.shouzhan.design.databinding.ActivityNumberPickerBinding
import kotlinx.android.synthetic.main.activity_number_picker.*


/**
 * @author danbin
 * @version NumberPickerActivity.java, v 0.1 2019-02-27 上午12:11 danbin
 */
class NumberPickerActivity : BaseKotlinActivity<ActivityNumberPickerBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_number_picker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    override fun initView() {
        var intervalTimes = resources.getIntArray(R.array.member_interval_time)
        number_picker.labels = intervalTimes.toList()
        number_picker.selection = 1
        number_picker.setOnWheelItemSelectedListener {
            position -> Log.d("test", "" + position)
        }
    }

    override fun getData() {

    }

    override fun onClick(view: View) {

    }

}
