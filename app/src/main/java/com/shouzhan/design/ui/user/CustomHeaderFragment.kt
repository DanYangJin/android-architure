package com.shouzhan.design.ui.user

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.shouzhan.design.R
import com.shouzhan.design.adapter.UserListPinnedAdapter
import com.shouzhan.design.base.LazyFragment
import com.shouzhan.design.compontent.recyclerview.FsLoadRefreshListener
import com.shouzhan.design.compontent.recyclerview.FsRecyclerViewAdapter
import com.shouzhan.design.ui.home.MainActivity
import com.shouzhan.design.ui.user.viewmodel.UserListViewModel
import kotlinx.android.synthetic.main.fragment_custom_header.*

/**
 * @author danbin
 * @version HeaderFragment.java, v 0.1 2019-03-02 下午10:36 danbin
 * 自定义下拉头布局
 */
class CustomHeaderFragment : LazyFragment() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        vmProviders(UserListViewModel::class.java)
    }

    private lateinit var mDataAdapter: UserListPinnedAdapter
    private var mLuRecyclerViewAdapter: FsRecyclerViewAdapter? = null

    /**已经获取到多少条数据了 */
    private var mCurrentSize = 0
    private var mCurPage = 1
    private var mTotalSize = 10

    companion object {
        fun get(): CustomHeaderFragment {
            return CustomHeaderFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_custom_header

    override fun initView() {
        mDataAdapter = UserListPinnedAdapter()
        recycler_view.layoutManager = LinearLayoutManager(mContext)
//        recycler_view.addItemDecoration(FsPinnedHeaderItemDecoration())
        mLuRecyclerViewAdapter = FsRecyclerViewAdapter(mDataAdapter)
        mLuRecyclerViewAdapter!!.setOnItemClickListener { _, _ ->
            startActivity(Intent(mContext, MainActivity::class.java))
        }
        recycler_view.adapter = mLuRecyclerViewAdapter
        recycler_view.setHasFixedSize(true)
        recycler_view.setOnLoadRefreshListener(object : FsLoadRefreshListener() {
            override fun onRefresh() {
                super.onRefresh()
                mCurrentSize = 0
                mCurPage = 1
                recycler_view.setRefreshing(true)
                getUserData()
            }

            override fun onLoadMore() {
                super.onLoadMore()
                if (mCurrentSize < mTotalSize) {
                    mCurPage++
                    getUserData()
                } else {
                    recycler_view.setNoMore(true)
                }
            }
        })
        viewModel.observerUserListResult().observe(this, Observer {
            it?.let { data ->
                data.list?.let { results ->
                    mTotalSize = data.total
                    mCurrentSize += results.size
                    if (mCurPage == 1) {
                        recycler_view.refreshComplete()
                        mDataAdapter.setNewData(results.toMutableList())
                    } else {
                        recycler_view.refreshComplete()
                        mDataAdapter.addData(results.toMutableList())
                    }
                }
            }
        })
    }

    override fun getData() {
        recycler_view.forceToRefresh()
    }

    private fun getUserData() {
        viewModel.requestData(mCurPage)
    }


}