package com.shouzhan.design.ui.home

import android.view.View
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.shouzhan.design.R
import com.shouzhan.design.adapter.CatAdapter
import com.shouzhan.design.base.BaseActivity
import com.shouzhan.design.databinding.ActivityDatePickerBinding
import kotlinx.android.synthetic.main.activity_flex_box.*

/**
 * @author danbin
 * @version FlexboxActivity.java, v 0.1 2019-07-11 23:43 danbin
 */
class FlexboxActivity : BaseActivity<ActivityDatePickerBinding>() {

    override fun onClick(view: View?) {
    }

    override fun getLayoutId(): Int = R.layout.activity_flex_box

    override fun initView() {
        super.initView()
        val flexboxLayoutManager = FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }

        cat_rv.apply {
            layoutManager = flexboxLayoutManager
            adapter = CatAdapter()
        }
    }

    override fun getData() {
    }

}