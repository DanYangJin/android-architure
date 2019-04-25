package com.shouzhan.design.ui.h5;

import android.content.Context
import android.content.Intent
import com.shouzhan.design.R
import com.shouzhan.design.base.BaseKotlinNoBindingActivity
import com.shouzhan.design.utils.Constants
import kotlinx.android.synthetic.main.activity_common_h5.*

/**
 * @author danbin
 * @version CommonH5Activity.java, v 0.1 2019-04-24 上午1:27 danbin
 */
class CommonH5Activity : BaseKotlinNoBindingActivity() {

    override fun getLayoutId() = R.layout.activity_common_h5

    companion object {
        fun run(context: Context, url: String) {
            var intent = Intent(context, CommonH5Activity.javaClass)
            intent.putExtra(Constants.EXTRA_COMMON_H5_URL, url)
            context.startActivity(intent)
        }
    }

    var url:String? = null

    override fun extraIntentData() {
        super.extraIntentData()
        url = intent.getStringExtra(Constants.EXTRA_COMMON_H5_URL)
    }

    override fun getData() {

    }

    override fun initView() {
        webview.loadUrl(url)
    }

}
