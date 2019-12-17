package com.shouzhan.design.ui.home

import android.view.View
import com.shouzhan.design.R
import com.shouzhan.design.base.BaseActivity
import com.shouzhan.design.databinding.ActivityFlexBoxBinding
import com.shouzhan.design.ui.home.contract.MainContract
import com.shouzhan.design.ui.home.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_flex_box.*

/**
 * @author danbin
 * @version FlexboxActivity.java, v 0.1 2019-07-11 23:43 danbin
 */
class FlexboxActivity : BaseActivity<ActivityFlexBoxBinding>(), MainContract.View {

    private val presenter by lazy(LazyThreadSafetyMode.NONE) {
        MainPresenter(this, this, mBinding)
    }

    override fun getLayoutId(): Int = R.layout.activity_flex_box

    override fun initView() {
        super.initView()
//        val flexboxLayoutManager = FlexboxLayoutManager(this).apply {
//            flexWrap = FlexWrap.WRAP
//            flexDirection = FlexDirection.ROW
//            alignItems = AlignItems.STRETCH
//        }
//
//        cat_rv.apply {
//            layoutManager = flexboxLayoutManager
//            adapter = CatAdapter()
//        }
        update_btn.setOnClickListener {
            presenter.updateLiveData()
        }

    }

    override fun getData() {
    }

    override fun onClick(view: View?) {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

}