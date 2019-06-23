package com.shouzhan.design.extens

import android.util.Log
import com.shouzhan.design.BuildConfig

/**
 * @author danbin
 * @version LogExt.java, v 0.1 2019-06-23 07:59 danbin
 */
fun Any.logE(msg : String?) {
    if (BuildConfig.DEBUG) {
        Log.e(javaClass.simpleName, msg)
    }
}

fun Any.logD(msg : String?) {
    if (BuildConfig.DEBUG) {
        Log.d(javaClass.simpleName, msg)
    }
}