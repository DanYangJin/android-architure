package com.shouzhan.design.ui.user

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shouzhan.design.R
import com.shouzhan.design.adapter.UserListAdapter
import com.shouzhan.design.base.BaseLazyFragment
import com.shouzhan.design.compontent.recyclerview.FsLoadRefreshListener
import com.shouzhan.design.compontent.recyclerview.FsPinnedHeaderItemDecoration
import com.shouzhan.design.compontent.recyclerview.FsRecyclerViewAdapter
import com.shouzhan.design.databinding.FragmentUserListBinding
import com.shouzhan.design.ui.home.MainActivity
import com.shouzhan.design.ui.user.viewmodel.UserListViewModel
import kotlinx.android.synthetic.main.fragment_user_list.*

/**
 * @author danbin
 * @version FragmentOne.java, v 0.1 2019-03-02 下午10:36 danbin
 */
class UserListFragment : BaseLazyFragment<FragmentUserListBinding>(), androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        vmProviders(UserListViewModel::class.java)
    }

    private lateinit var mDataAdapter: UserListAdapter
    private var mLuRecyclerViewAdapter: FsRecyclerViewAdapter? = null

    /**已经获取到多少条数据了 */
    private var mCurrentSize = 0
    private var mCurPage = 1
    private var mTotalSize = 10

    companion object {
        fun get(): UserListFragment {
            return UserListFragment()
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
        recycler_view.addItemDecoration(FsPinnedHeaderItemDecoration())
        recycler_view.adapter = mLuRecyclerViewAdapter
        recycler_view.setRefreshEnabled(false)
        recycler_view.setHasFixedSize(true)
        recycler_view.setOnLoadRefreshListener(object : FsLoadRefreshListener() {
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
                        swipe_refresh_layout.isRefreshing = false
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