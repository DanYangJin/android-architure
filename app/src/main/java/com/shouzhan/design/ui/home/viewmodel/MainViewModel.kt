package com.shouzhan.design.ui.home.viewmodel

import androidx.databinding.ObservableField
import com.shouzhan.design.base.BaseViewModel

/**
 * @author danbin
 * @version MainViewModel.java, v 0.1 2019-02-27 上午12:20 danbin
 */
class MainViewModel : BaseViewModel() {

    val headImageUrl = ObservableField<String>()
    val inputTxt = ObservableField<String>()
    val rememberMe = ObservableField<Boolean>(true)

    fun setHeadImageUrl() {
        headImageUrl.set("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1551108895&di=745684775de1e4b78f063fd0785ea90f&src=http://pic5.nipic.com/20100127/2177138_082501971985_2.jpg")
    }
}