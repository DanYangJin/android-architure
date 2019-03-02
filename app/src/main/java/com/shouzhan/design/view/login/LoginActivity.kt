package com.shouzhan.design.view.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.View
import com.shouzhan.design.App
import com.shouzhan.design.R
import com.shouzhan.design.base.BaseActivity
import com.shouzhan.design.databinding.ActivityLoginBinding
import com.shouzhan.design.extens.toast
import com.shouzhan.design.viewmodel.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @author danbin
 * @version LoginActivity.java, v 0.1 2019-02-27 上午12:10 danbin
 */
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider.AndroidViewModelFactory.
                getInstance(App.getInstance()).create(LoginViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel!!.loginResult.observe(this, Observer { loginResult ->
            Log.e(TAG, "onChanged: " + loginResult!!.toString())
            mBinding.loginresult = loginResult
        })
    }

    override fun initView() {

    }

    override fun getData() {
        var account: String = account_et.text.toString()
        var password: String  = password_et.text.toString()
        if (account.isNullOrBlank() || password.isNullOrBlank()) {
            toast("账号和密码不能为空")
            return
        }
        viewModel!!.toLogin(account, password)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.send_btn -> getData()
            else -> {
            }
        }
    }

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }

}
