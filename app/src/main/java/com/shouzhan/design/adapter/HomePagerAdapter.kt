package com.shouzhan.design.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.shouzhan.design.base.LazyFragment
import com.shouzhan.design.ui.user.HeaderFragment
import com.shouzhan.design.ui.user.UserListFragment

/**
 * @author danbin
 * @version HomePagerAdapter.java, v 0.1 2019-02-26 下午11:23 danbin
 */
class HomePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private var fragments: MutableList<LazyFragment> = ArrayList()

    init {
        fragments.add(UserListFragment.get())
        fragments.add(HeaderFragment.get())
    }

    override fun getItem(position: Int): LazyFragment {
        return fragments[position]
    }

    override fun getCount() = fragments.size

}