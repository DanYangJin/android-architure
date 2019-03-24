package com.shouzhan.design.view.kotlin

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.shouzhan.design.App
import com.shouzhan.design.R
import com.shouzhan.design.adapter.UserListAdapter
import com.shouzhan.design.base.LazyFragment
import com.shouzhan.design.compontent.recyclerview.LuRecyclerViewAdapter
import com.shouzhan.design.extens.logE
import com.shouzhan.design.extens.yes
import com.shouzhan.design.view.home.MainActivity
import com.shouzhan.design.viewmodel.kotlin.UserListViewModel
import kotlinx.android.synthetic.main.fragment_two.*

/**
 * @author danbin
 * @version FragmentOne.java, v 0.1 2019-03-02 下午10:36 danbin
 */
class FragmentTwo : LazyFragment(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider.AndroidViewModelFactory.getInstance(App.getInstance()).create(UserListViewModel::class.java)
    }

    private lateinit var mDataAdapter: UserListAdapter
    private var mLuRecyclerViewAdapter: LuRecyclerViewAdapter? = null

    /**已经获取到多少条数据了 */
    private var mCurrentCounter = 0
    private var mCurPage = 1
    private var mTotal = 0

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
        swipe_refresh_layout.setProgressViewOffset(false, 0, 48)
        swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary)
        swipe_refresh_layout.setOnRefreshListener(this)

        mDataAdapter = UserListAdapter(arrayListOf())
        recycler_view.layoutManager = LinearLayoutManager(mContext)
        mLuRecyclerViewAdapter = LuRecyclerViewAdapter(mDataAdapter)
        mLuRecyclerViewAdapter!!.setOnItemClickListener { _, _ ->
            startActivity(Intent(mContext, MainActivity::class.java))
        }
        recycler_view.adapter = mLuRecyclerViewAdapter
        recycler_view.setHasFixedSize(true)
        recycler_view.setOnLoadMoreListener {
            logE("OnLoadMore")
            if (mCurrentCounter < mTotal) {
                mCurPage++
                getUserData()
            } else {
                recycler_view.setNoMore(true)
            }
        }
        viewModel.userListResult.observe(this, Observer {
            mTotal = 10
            mCurrentCounter += it!!.list!!.size
            if (mCurPage == 1) {
                swipe_refresh_layout.isRefreshing = false
                recycler_view.refreshComplete(20)
                mDataAdapter.setNewData(it!!.list!!.subList(0, 10))
            } else {
                recycler_view.refreshComplete(20)
                mDataAdapter.addData(it!!.list!!.toMutableList())
            }
        })
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        (isVisibleToUser && isInitView).yes {
            getData()
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun getData() {
        swipe_refresh_layout.isRefreshing = true
        onRefresh()
    }

    override fun onRefresh() {
        logE("onRefresh")
        mCurrentCounter = 0
        mCurPage = 1
        recycler_view.setRefreshing(true)
        getUserData()
    }

    private fun getUserData() {
        viewModel.requestData(mCurPage)
    }


}