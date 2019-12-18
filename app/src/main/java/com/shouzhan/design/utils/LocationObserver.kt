package com.shouzhan.design.utils

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import com.fshows.android.stark.utils.FsLogUtil

/**
 * @author danbin
 * @version LocationObserver.java, v 0.1 2019-12-18 09:36 danbin
 */
class LocationObserver(private val lifecycle: Lifecycle) : LifecycleObserver {

    var location = MutableLiveData<String>()

    init {
        initLocation()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        FsLogUtil.error("Catch", "onStart")
        cbLocation()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        FsLogUtil.error("Catch", "onStop")
    }

    /**
     * 初始化定位
     * */
    private fun initLocation() {
        FsLogUtil.error("Catch", "initLocation")
    }

    /**
     * 位置信息回调
     * */
    private fun cbLocation() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            location.value = "获取定位信息啦"
        }
    }

}