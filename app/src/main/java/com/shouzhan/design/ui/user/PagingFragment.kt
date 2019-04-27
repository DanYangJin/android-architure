package com.shouzhan.design.ui.user

import android.support.v7.widget.LinearLayoutManager
import com.shouzhan.design.R
import com.shouzhan.design.adapter.UserListPagingAdapter
import com.shouzhan.design.base.LazyFragment
import com.shouzhan.design.extens.yes
import com.shouzhan.design.ui.user.viewmodel.UserListViewModel
import kotlinx.android.synthetic.main.fragment_paging.*

/**
 * @author danbin
 * @version HeaderFragment.java, v 0.1 2019-03-02 下午10:36 danbin
 * 自定义下拉头布局
 */
class PagingFragment : LazyFragment() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        vmProviders(UserListViewModel::class.java)
    }

    private lateinit var mDataAdapter: UserListPagingAdapter

    companion object {
        private var instance: PagingFragment? = null
            get() {
                if (field == null) {
                    field = PagingFragment()
                }
                return field
            }

        fun get(): PagingFragment {
            return instance!!
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_paging

    override fun initView() {
        mDataAdapter = UserListPagingAdapter()
        recycler_view.layoutManager = LinearLayoutManager(mContext)
        recycler_view.adapter = mDataAdapter
//        viewModel.observerUserListPageResult().observe(this, Observer {
//            Log.e("Catch", "" + it!![0].toString())
//            mDataAdapter.submitList(it)
//        })
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        (isVisibleToUser && isInitView).yes {
            getData()
        }
    }

    override fun getData() {
    }

}