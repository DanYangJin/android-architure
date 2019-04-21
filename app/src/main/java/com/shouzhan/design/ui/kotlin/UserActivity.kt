package com.shouzhan.design.ui.kotlin

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.view.Menu
import android.view.MenuItem
import com.shouzhan.design.R
import com.shouzhan.design.R.id.home_view_pager
import com.shouzhan.design.adapter.HomePagerAdapter
import com.shouzhan.design.base.BaseKotlinNoBindingActivity
import com.shouzhan.design.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_user.*

/**
 * @author danbin
 * @version KotlinActivity.java, v 0.1 2019-02-26 上午11:27 danbin
 * 通过Fragment实现加载两种不同的翻页功能
 */
class UserActivity : BaseKotlinNoBindingActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_user
    }

    override fun initView() {
        var adapter = HomePagerAdapter(supportFragmentManager)
        home_view_pager.adapter = adapter
    }

    override fun getData() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.id_add_item -> {
                startActivity(Intent(mContext, LoginActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}


