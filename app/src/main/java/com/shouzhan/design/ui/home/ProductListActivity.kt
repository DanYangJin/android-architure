package com.shouzhan.design.ui.home

import android.os.Bundle
import android.view.View
import com.shouzhan.design.R
import com.shouzhan.design.adapter.ProductListAdapter
import com.shouzhan.design.base.BaseActivity
import com.shouzhan.design.compontent.recyclerview.FsRecyclerViewAdapter
import com.shouzhan.design.databinding.ActivityProductListBinding
import com.shouzhan.design.extens.logD
import kotlinx.android.synthetic.main.activity_product_list.*

/**
 * @author danbin
 * @version ProductListActivity.java, v 0.1 2019-06-27 08:42 danbin
 */
class ProductListActivity : BaseActivity<ActivityProductListBinding>() {


    private val mAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ProductListAdapter(mContext)
    }

    private lateinit var mLuRecyclerViewAdapter: FsRecyclerViewAdapter

    override fun onClick(view: View?) {
    }

    override fun getLayoutId(): Int = R.layout.activity_product_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    override fun initView() {
        super.initView()
        product_rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(mContext)
        mLuRecyclerViewAdapter = FsRecyclerViewAdapter(mAdapter)
        mLuRecyclerViewAdapter.setOnItemClickListener { _, _ ->
            logD("点击啦")
        }
        product_rv.adapter = mLuRecyclerViewAdapter
        product_rv.setRefreshEnabled(false)
        product_rv.setHasFixedSize(true)
    }

    override fun getData() {
        var productList: MutableList<String> = mutableListOf()
        productList.add("11111")
        productList.add("22222")
        productList.add("33333")
        mAdapter.setNewData(productList)
    }


}
