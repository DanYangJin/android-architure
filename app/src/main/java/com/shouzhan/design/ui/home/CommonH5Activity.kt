package com.shouzhan.design.ui.home

import android.view.View
import android.webkit.WebViewClient
import com.shouzhan.design.R
import com.shouzhan.design.base.BaseActivity
import com.shouzhan.design.databinding.ActivityCommonH5Binding
import kotlinx.android.synthetic.main.activity_common_h5.*

/**
 * @author danbin
 * @version CommonH5Activity.java, v 0.1 2019-04-24 上午1:27 danbin
 */
class CommonH5Activity : BaseActivity<ActivityCommonH5Binding>() {

    override fun getLayoutId() = R.layout.activity_common_h5

    private var url:String? = "file:///android_asset/keyboard.html"

//    override fun extraIntentData() {
//        super.extraIntentData()
//        url = intent.getStringExtra(Constants.EXTRA_COMMON_H5_URL)
//    }

    override fun getData() {

    }

    override fun initView() {
        webview.webViewClient = WebViewClient()
        webview.loadUrl(url)
    }

    override fun onClick(view: View?) {

    }

}
