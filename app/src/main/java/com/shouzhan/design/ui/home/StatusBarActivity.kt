package com.shouzhan.design.ui.home

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.ColorUtils
import android.support.v4.widget.NestedScrollView
import android.view.View
import android.widget.RelativeLayout
import com.gyf.immersionbar.ImmersionBar
import com.shouzhan.design.R
import com.shouzhan.design.base.BaseActivity
import com.shouzhan.design.databinding.ActivityStatusBarBinding
import kotlinx.android.synthetic.main.activity_status_bar.*
import kotlinx.android.synthetic.main.layout_fb_home_status_bar.*

/**
 * @author danbin
 * @version StatusBarActivity.java, v 0.1 2019-12-28 11:23 danbin
 */
class StatusBarActivity : BaseActivity<ActivityStatusBarBinding>() {

    private var toolBarHeight = 0

    override fun getLayoutId() = R.layout.activity_status_bar

    override fun getData() {}

    override fun onClick(view: View?) {}

    override fun initView() {
        super.initView()
        toolBarHeight = resources.getDimensionPixelOffset(R.dimen.home_toolbar_height)
        dynamicCalcHomeGradientViewHeight()
        ImmersionBar.setTitleBar(this, toolbar)
        toolbar.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT, ContextCompat.getColor(mContext, R.color.colorPrimary), 0f))
        scroll_view.setOnScrollChangeListener(object: NestedScrollView.OnScrollChangeListener {
            private var totalDy = 0

            override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                totalDy += (scrollY - oldScrollY)
                if (totalDy <= toolBarHeight) {
                    val alpha = totalDy.toFloat() / toolBarHeight
                    toolbar.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT, ContextCompat.getColor(mContext, R.color.colorPrimary), alpha))
                } else {
                    toolbar.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT, ContextCompat.getColor(mContext, R.color.colorPrimary), 1f))
                }
            }

        })

    }

    /**
     * 动态计算渐变色的高度
     * */
    private fun dynamicCalcHomeGradientViewHeight() {
        var params = home_top_gradient_view.layoutParams
        params.width = RelativeLayout.LayoutParams.MATCH_PARENT
        params.height = ImmersionBar.getStatusBarHeight(this) + toolBarHeight
        home_top_gradient_view.layoutParams = params
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this).statusBarColorTransformEnable(false)
                .keyboardEnable(false)
                .init()
    }

}