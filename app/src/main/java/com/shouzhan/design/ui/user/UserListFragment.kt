package com.shouzhan.design.ui.user

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.shouzhan.design.R
import com.shouzhan.design.adapter.UserListAdapter
import com.shouzhan.design.base.LazyFragment
import com.shouzhan.design.compontent.recyclerview.FsPinnedHeaderItemDecoration
import com.shouzhan.design.compontent.recyclerview.FsRecyclerViewAdapter
import com.shouzhan.design.compontent.view.CustomLoadingFooter
import com.shouzhan.design.extens.yes
import com.shouzhan.design.ui.home.MainActivity
import com.shouzhan.design.ui.user.viewmodel.UserListViewModel
import kotlinx.android.synthetic.main.fragment_user_list.*

/**
 * @author danbin
 * @version FragmentOne.java, v 0.1 2019-03-02 下午10:36 danbin
 */
class UserListFragment : LazyFragment(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        vmProviders(UserListViewModel::class.java)
    }

    private lateinit var mDataAdapter: UserListAdapter
    private var mLuRecyclerViewAdapter: FsRecyclerViewAdapter? = null

    /**已经获取到多少条数据了 */
    private var mCurrentSize= 0
    private var mCurPage = 1
    private var mTotalSize = 10

    companion object {
        private var instance: UserListFragment? = null
            get() {
                if (field == null) {
                    field = UserListFragment()
                }
                return field
            }

        fun get(): UserListFragment {
            return instance!!
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_user_list

    override fun initView() {
        swipe_refresh_layout.setProgressViewOffset(false, 0, 48)
        swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary)
        swipe_refresh_layout.setOnRefreshListener(this)

        mDataAdapter = UserListAdapter()
        recycler_view.layoutManager = LinearLayoutManager(mContext)
        mLuRecyclerViewAdapter = FsRecyclerViewAdapter(mDataAdapter)
        mLuRecyclerViewAdapter!!.setOnItemClickListener { _, _ ->
            startActivity(Intent(mContext, MainActivity::class.java))
        }
        // 自定义底部加载布局
        recycler_view.setLoadMoreFooter(CustomLoadingFooter(mContext))
        // 暂不考虑
        recycler_view.addItemDecoration(FsPinnedHeaderItemDecoration())
        recycler_view.adapter = mLuRecyclerViewAdapter
        recycler_view.setRefreshEnabled(false)
        recycler_view.setHasFixedSize(true)
        recycler_view.setOnLoadMoreListener {
            if (mCurrentSize < mTotalSize) {
                mCurPage++
                getUserData()
            } else {
                recycler_view.setNoMore(true)
            }
        }
        viewModel.observerUserListResult().observe(this, Observer {
            mTotalSize = 30
            mCurrentSize += it!!.list!!.size
            if (mCurPage == 1) {
                swipe_refresh_layout.isRefreshing = false
                recycler_view.refreshComplete()
                mDataAdapter.setNewData(it!!.list!!.toMutableList())
            } else {
                recycler_view.refreshComplete()
                mDataAdapter.addData(it!!.list!!.toMutableList())
            }
        })
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        (isVisibleToUser && isInitView).yes {
            getData()
        }
    }

    override fun getData() {
        swipe_refresh_layout.isRefreshing = true
        onRefresh()
    }

    override fun onRefresh() {
        mCurrentSize = 0
        mCurPage = 1
        recycler_view.setRefreshing(true)
        getUserData()
    }

    private fun getUserData() {
        viewModel.requestData(mCurPage)
    }


}