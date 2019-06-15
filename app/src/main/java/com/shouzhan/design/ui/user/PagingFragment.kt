package com.shouzhan.design.ui.user

import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.support.v7.widget.LinearLayoutManager
import com.shouzhan.design.R
import com.shouzhan.design.adapter.UserPagingAdapter
import com.shouzhan.design.base.LazyFragment
import com.shouzhan.design.model.remote.result.UserListResult
import com.shouzhan.design.ui.user.viewmodel.UserPagingViewModel
import kotlinx.android.synthetic.main.fragment_paging.*

/**
 * @author danbin
 * @version PagingFragment.java, v 0.1 2019-03-02 下午10:36 danbin
 */
class PagingFragment : LazyFragment() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        vmProviders(UserPagingViewModel::class.java)
    }

    companion object {
        fun get(): PagingFragment {
            return PagingFragment()
        }
    }

    private var mAdapter: UserPagingAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_paging

    override fun initView() {
        recycler_view.layoutManager = LinearLayoutManager(mContext)
        mAdapter = UserPagingAdapter()
        recycler_view.adapter = mAdapter
        viewModel.concertList.observe(this, Observer<PagedList<UserListResult>> {
            mAdapter?.submitList(viewModel.concertList.value)
        })
        swipe_refresh_layout.setProgressViewOffset(false, 0, 48)
        swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary)
        swipe_refresh_layout.setOnRefreshListener {
            getData()
            swipe_refresh_layout.isRefreshing = false
        }
    }

    override fun getData() {
        viewModel.invalidateDataSource()
    }

}