package com.shouzhan.design.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.shouzhan.design.base.LazyFragment
import com.shouzhan.design.view.kotlin.FragmentOne
import com.shouzhan.design.view.kotlin.FragmentTwo

/**
 * @author danbin
 * @version HomePagerAdapter.java, v 0.1 2019-02-26 下午11:23 danbin
 */
class HomePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var fragments: MutableList<LazyFragment> = ArrayList()

    init {
        fragments.add(FragmentOne.get())
        fragments.add(FragmentTwo.get())
    }

    override fun getItem(position: Int): LazyFragment {
        return fragments[position]
    }

    override fun getCount() = 2

}