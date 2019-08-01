package com.shouzhan.design.ui.user

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.shouzhan.design.R
import com.shouzhan.design.adapter.HomePagerAdapter
import com.shouzhan.design.base.BaseNoBindingActivity
import com.shouzhan.design.ui.home.FlexboxActivity
import com.shouzhan.design.ui.login.LoginActivity
import com.shouzhan.design.ui.mvp.MvpActivity
import kotlinx.android.synthetic.main.activity_user.*

/**
 * @author danbin
 * @version KotlinActivity.java, v 0.1 2019-02-26 上午11:27 danbin
 * 通过Fragment实现加载两种不同的翻页功能
 */
class UserActivity : BaseNoBindingActivity() {

    private var titles = mutableListOf("测试1", "测试2")

    override fun getLayoutId(): Int {
        return R.layout.activity_user
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        home_tab_layout.setupWithViewPager(home_view_pager)
        var adapter = HomePagerAdapter(supportFragmentManager)
        home_view_pager.adapter = adapter
        repeat(titles.size) {
            home_tab_layout.getTabAt(it)?.text = titles[it]
        }
    }

    override fun getData() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.id_login -> {
                startActivity(Intent(mContext, LoginActivity::class.java))
            }
            R.id.id_flex_box -> {
                startActivity(Intent(mContext, FlexboxActivity::class.java))
            }
            R.id.id_dragger -> {
                startActivity(Intent(mContext, MvpActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View?) {
    }

}


