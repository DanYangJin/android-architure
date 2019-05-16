package com.shouzhan.design.ui.login.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.shouzhan.design.base.BaseViewModel
import com.shouzhan.design.datasource.http.performance.PerformanceApiCallback
import com.shouzhan.design.repository.LoginRepository
import com.shouzhan.design.ui.login.model.remote.result.LoginResult

/**
 * @author danbin
 * @version LoginViewModel.java, v 0.1 2019-02-27 上午12:18 danbin
 */
class LoginViewModel : BaseViewModel() {

    val loginResult = MutableLiveData<LoginResult>()

    var mLoginRepository: LoginRepository = LoginRepository()

    fun toLogin(username: String, password: String) {
        addSubscribe(mLoginRepository.toLogin(username, password), object : PerformanceApiCallback<LoginResult>() {
            override fun onSuccess(data: LoginResult) {
                loginResult.value = data
            }

            override fun onFailure(resultMsg: String?, resultCode: Int) {
                super.onFailure(resultMsg, resultCode)
                pageStatus.value = PageStatus.ERROR
            }
        })
    }


}
