package com.shouzhan.design.ui.launchmode

import android.content.DialogInterface
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.shouzhan.design.R
import com.shouzhan.design.base.BaseActivity
import com.shouzhan.design.databinding.ActivityLaunchModeBinding
import com.shouzhan.design.ui.home.MainActivity
import kotlinx.android.synthetic.main.activity_launch_mode.*


/**
 * @author danbin
 * @version LaunchModeActivity.java, v 0.1 2019-02-27 上午12:11 danbin
 */
class LaunchModeActivity : BaseActivity<ActivityLaunchModeBinding>() {

    override fun onClick(view: View?) {
    }

    override fun getLayoutId(): Int = R.layout.activity_launch_mode

    override fun initView() {
        super.initView()
        show_dialog_btn.setOnClickListener {
            AlertDialog.Builder(this)
                    .setMessage("哈哈哈")
                    .setTitle("啊啊啊啊")
                    .setPositiveButton("复制", DialogInterface.OnClickListener { dialogInterface, i -> })
                    .setNeutralButton("取消", null)
                    .create()
                    .show()
        }
        skip_dialog_btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun getData() {

    }


}
