package com.shouzhan.design.view.kotlin

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.widget.LinearLayoutManager
import com.shouzhan.design.App
import com.shouzhan.design.R
import com.shouzhan.design.adapter.UserListAdapter
import com.shouzhan.design.base.LazyFragment
import com.shouzhan.design.compontent.recyclerview.TwinklingRefreshLayout
import com.shouzhan.design.compontent.recyclerview.callback.OnRefreshListener
import com.shouzhan.design.extens.logE
import com.shouzhan.design.viewmodel.KotlinViewModel
import kotlinx.android.synthetic.main.fragment_one.*

/**
 * @author danbin
 * @version FragmentOne.java, v 0.1 2019-03-02 下午10:36 danbin
 */
class FragmentOne: LazyFragment() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider.AndroidViewModelFactory.
                getInstance(App.getInstance()).create(KotlinViewModel::class.java)
    }

    var page = 1
    var adapter: UserListAdapter? = null

    companion object {
        private var instance: FragmentOne? = null
            get() {
                if (field == null) {
                    field = FragmentOne()
                }
                return field
            }
        fun get(): FragmentOne {
            return instance!!
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_one

    override fun initView() {
        twink_refresh_layout.setFloatRefresh(true)
        twink_refresh_layout.setOverScrollRefreshShow(false)
        twink_refresh_layout.setOverScrollHeight(200F)
        twink_refresh_layout.setOnRefreshListener(object : OnRefreshListener() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout) {
                super.onRefresh(refreshLayout)
                logE("onRefresh")
                page = 1
                getData()
            }

            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout) {
                super.onLoadMore(refreshLayout)
                logE("onLoadMore")
                page++
                getData()
            }
        })
        val layoutManager = LinearLayoutManager(mContext)
        twink_refresh_rv.layoutManager = layoutManager
        adapter = UserListAdapter(arrayListOf())
        twink_refresh_rv.adapter = adapter
        viewModel.userListResult.observe(this, Observer {
//            logE("${it!!.list}")
            adapter!!.addData(it!!.list!!.toMutableList())
        })
    }

    override fun getData() {
        viewModel.requestData()
    }



}