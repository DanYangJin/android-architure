package com.shouzhan.design.extens

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * @author danbin
 * @version CommonExt.java, v 0.1 2019-02-26 下午11:17 danbin
 * 应用基础扩展
 */
private var toast: Toast? = null

fun Context.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    toast ?: let {
        toast = Toast.makeText(this.applicationContext, null, duration)
    }
    toast?.apply {
        setText(msg)
        show()
    }
}

@SuppressLint("ShowToast")
fun <T : Fragment> T.toast(text: CharSequence) {
    context?.toast(text)
}

/**
 * @param resId 字符串资源
 */
fun <T : Fragment> T.toast(@StringRes resId: Int) {
    toast(getString(resId))
}
