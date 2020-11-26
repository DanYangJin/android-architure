package com.shouzhan.design.utils

import android.content.Context
import androidx.startup.Initializer
import com.fshows.android.stark.utils.FsLogUtil
import com.fshows.android.stark.utils.Prefs

/**
 * @author danbin
 * @version PrefsInitializer.java, v 0.1 2020-11-26 8:27 PM danbin
 */
class PrefsInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        FsLogUtil.error("sxs", "createcreatecreate")
        Prefs.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

}