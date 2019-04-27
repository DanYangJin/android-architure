package com.shouzhan.design.ui.user

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.shouzhan.design.R
import com.shouzhan.design.adapter.UserListAdapter
import com.shouzhan.design.base.LazyFragment
import com.shouzhan.design.compontent.recyclerview.FsRecyclerViewAdapter
import com.shouzhan.design.extens.yes
import com.shouzhan.design.ui.home.MainActivity
import com.shouzhan.design.ui.user.viewmodel.UserListViewModel
import kotlinx.android.synthetic.main.fragment_user_list.*

/**
 * @author danbin
 * @version HeaderFragment.java, v 0.1 2019-03-02 下午10:36 danbin
 * 自定义下拉头布局
 */
class HeaderFragment : LazyFragment(), SwipeRefreshLayout.OnRefreshListener {

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
        private var instance: HeaderFragment? = null
            get() {
                if (field == null) {
                    field = HeaderFragment()
                }
                return field
            }

        fun get(): HeaderFragment {
            return instance!!
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_costom_header

    override fun initView() {
        mDataAdapter = UserListAdapter()
        recycler_view.layoutManager = LinearLayoutManager(mContext)
        mLuRecyclerViewAdapter = FsRecyclerViewAdapter(mDataAdapter)
        mLuRecyclerViewAdapter!!.setOnItemClickListener { _, _ ->
            startActivity(Intent(mContext, MainActivity::class.java))
        }
        recycler_view.adapter = mLuRecyclerViewAdapter
        recycler_view.setHasFixedSize(true)
        recycler_view.setOnLoadMoreListener {
            if (mCurrentSize < mTotalSize) {
                mCurPage++
                getUserData()
            } else {
                recycler_view.setNoMore(true)
            }
        }
        recycler_view.setOnRefreshListener{
            mCurrentSize = 0
            mCurPage = 1
            recycler_view.setRefreshing(true)
            getUserData()
        }
        viewModel.observerUserListResult().observe(this, Observer {
            mTotalSize = 30
            mCurrentSize += it!!.list!!.size
            if (mCurPage == 1) {
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