package com.shouzhan.design.ui.user

import android.animation.ObjectAnimator
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.fshows.android.parker.navigation.FsTabLayout
import com.shouzhan.design.R
import com.shouzhan.design.adapter.HomePagerAdapter
import com.shouzhan.design.base.BaseActivity
import com.shouzhan.design.databinding.ActivityUserBinding
import com.shouzhan.design.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_user.*


/**
 * @author danbin
 * @version KotlinActivity.java, v 0.1 2019-02-26 上午11:27 danbin
 * 通过Fragment实现加载两种不同的翻页功能
 */
class UserActivity : BaseActivity<ActivityUserBinding>() {

    private var titles = mutableListOf("我的设备", "设备商城")

    override fun getLayoutId(): Int {
        return R.layout.activity_user
    }

    override fun initView() {
        login_tv.setOnClickListener {
            startActivity(Intent(mContext, LoginActivity::class.java))
        }
        home_tab_layout.addOnTabSelectedListener(object : FsTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: FsTabLayout.Tab) {
                Log.e("xsd", "onTabSelected: ${tab.position}")
                var view = tab.customView
                view?.run {
                    val tabIcon: ImageView = findViewById(R.id.tab_icon)
                    val tabName: TextView = findViewById(R.id.tab_name)
                    tabName.setTextColor(ContextCompat.getColor(mContext, R.color.color_red_f04144))
                    when (tab.position) {
                        1 -> {
                            tabIcon.setImageResource(R.mipmap.ic_arrow_down_red)
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: FsTabLayout.Tab) {
                Log.e("xsd", "onTabUnselected: ${tab.position}")
                var view = tab.customView
                view?.run {
                    val tabIcon: ImageView = findViewById(R.id.tab_icon)
                    val tabName: TextView = findViewById(R.id.tab_name)
                    tabName.setTextColor(ContextCompat.getColor(mContext, R.color.color_666))
                    when (tab.position) {
                        1 -> {
                            tabIcon.setImageResource(R.mipmap.ic_arrow_down_black)
                        }
                    }
                }
            }

            var isVisible = false
            override fun onTabReselected(tab: FsTabLayout.Tab) {
                Log.e("xsd", "onTabReselected: ${tab.position}")
                var view = tab.customView
                view?.run {
                    val tabIcon: ImageView = findViewById(R.id.tab_icon)
                    when (tab.position) {
                        1 -> {
                            isVisible = !isVisible
                            tabIcon.clearAnimation()
                            val animator = ObjectAnimator.ofFloat(tabIcon, "rotation", if (isVisible) 0f else 180f, if (isVisible) 180f else 360f)
                            animator.duration = 300
                            animator.start()
                        }
                    }
                }
            }
        })
        home_tab_layout.setSlidingIndicatorAnimType(FsTabLayout.AnimType.GLUE)
        home_tab_layout.setTabIndicatorWidth(0.3f)
        home_tab_layout.setupWithViewPager(home_view_pager)
        var adapter = HomePagerAdapter(supportFragmentManager)
        home_view_pager.adapter = adapter
        repeat(titles.size) {
            val tab = home_tab_layout.getTabAt(it)
            val view = LayoutInflater.from(this).inflate(R.layout.item_tab, home_tab_layout, false)
            val tabIcon: ImageView = view.findViewById(R.id.tab_icon)
            val tabName: TextView = view.findViewById(R.id.tab_name)
            tabName.text = titles[it]
            tabName.setTextColor(if (it == 0) {
                ContextCompat.getColor(mContext, R.color.color_f04144)
            } else {
                ContextCompat.getColor(mContext, R.color.color_666)
            })
            tabIcon.visibility = if (it == 0) {
                View.GONE
            } else {
                View.VISIBLE
            }
            tab?.customView = view
        }
    }

    override fun getData() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onClick(view: View?) {

    }

}


