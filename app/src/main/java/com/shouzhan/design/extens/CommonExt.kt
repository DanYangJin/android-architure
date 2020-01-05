package com.shouzhan.design.extens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.util.TypedValue
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
fun <T : androidx.fragment.app.Fragment> T.toast(text: CharSequence) {
    context?.toast(text)
}

/**
 * @param resId 字符串资源
 */
fun <T : androidx.fragment.app.Fragment> T.toast(@StringRes resId: Int) {
    toast(getString(resId))
}

fun Float.dp2px(context: Context) : Int{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this,
        context.resources.displayMetrics).toInt()
}

inline fun <reified T : Activity> Activity?.startActivity() {
    this?.startActivity(Intent(this, T::class.java))
}

