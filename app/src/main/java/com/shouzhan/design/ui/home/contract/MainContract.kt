package com.shouzhan.design.ui.home.contract

import com.shouzhan.design.base.IBasePresenter
import com.shouzhan.design.base.IBaseView

/**
 * @author danbin
 * @version MainContract.java, v 0.1 2019-12-13 17:17 danbin
 */
interface MainContract {

    interface View : IBaseView {

    }

    interface Presenter : IBasePresenter {

        fun updateSameLiveData()

    }

}