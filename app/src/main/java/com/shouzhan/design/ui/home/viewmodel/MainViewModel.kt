package com.shouzhan.design.ui.home.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import com.shouzhan.design.base.BaseViewModel

/**
 * @author danbin
 * @version MainViewModel.java, v 0.1 2019-02-27 上午12:20 danbin
 */
class MainViewModel : BaseViewModel() {

    val headImage = ObservableField<String>()
    var sameLiveData = MutableLiveData<String>()

    fun refreshHeadImage(headUrl: String) {
        headImage.set(headUrl)
    }


}