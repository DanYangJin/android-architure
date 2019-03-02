package com.shouzhan.design.view.kotlin

import com.shouzhan.design.R
import com.shouzhan.design.base.LazyFragment
import com.shouzhan.design.extens.logE

/**
 * @author danbin
 * @version FragmentOne.java, v 0.1 2019-03-02 下午10:36 danbin
 */
class FragmentTwo: LazyFragment() {

    companion object {
        private var instance: FragmentTwo? = null
            get() {
                if (field == null) {
                    field = FragmentTwo()
                }
                return field
            }
        fun get(): FragmentTwo {
            return instance!!
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_two

    override fun initView() {

    }

    override fun getData() {
        logE("FragmentTwo: 已经执行了getData")
    }



}