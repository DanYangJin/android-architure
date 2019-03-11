package com.shouzhan.design.view.kotlin

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.view.Menu
import android.view.MenuItem
import com.shouzhan.design.App
import com.shouzhan.design.R
import com.shouzhan.design.adapter.HomePagerAdapter
import com.shouzhan.design.base.BaseNoBindingActivity
import com.shouzhan.design.utils.Preference
import com.shouzhan.design.viewmodel.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_kotlin.*

/**
 * @author danbin
 * @version KotlinActivity.java, v 0.1 2019-02-26 上午11:27 danbin
 * 通过Fragment实现加载两种不同的翻页功能
 */
class KotlinActivity : BaseNoBindingActivity() {

    private var accessToken: String? by Preference("accessToken", "")

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider.AndroidViewModelFactory.
                getInstance(App.getInstance()).create(LoginViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_kotlin
    }

    override fun initView() {
        var adapter = HomePagerAdapter(supportFragmentManager)
        home_view_pager.adapter = adapter
        viewModel.loginResult.observe(this, Observer { loginResult ->
            accessToken = loginResult!!.accessToken
        })
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
                viewModel.toLogin("danbin", "111111")
            }
        }
        return super.onOptionsItemSelected(item)
    }

}


