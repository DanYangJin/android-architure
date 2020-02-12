package com.shouzhan.design.adapter

import androidx.fragment.app.FragmentManager
import com.shouzhan.design.base.BaseLazyFragment
import com.shouzhan.design.ui.user.CustomHeaderFragment
import com.shouzhan.design.ui.user.UserListFragment

/**
 * @author danbin
 * @version HomePagerAdapter.java, v 0.1 2019-02-26 下午11:23 danbin
 */
class HomePagerAdapter(fm: FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {

    private var fragments: MutableList<BaseLazyFragment<*>> = ArrayList()

    init {
        fragments.add(UserListFragment.get())
        fragments.add(CustomHeaderFragment.get())
    }

    override fun getItem(position: Int): BaseLazyFragment<*> {
        return fragments[position]
    }

    override fun getCount() = fragments.size

}