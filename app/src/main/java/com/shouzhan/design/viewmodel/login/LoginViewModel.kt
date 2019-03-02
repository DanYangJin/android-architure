package com.shouzhan.design.viewmodel.login

import android.arch.lifecycle.MutableLiveData
import com.shouzhan.design.base.BaseViewModel
import com.shouzhan.design.datasource.http.ApiCallback
import com.shouzhan.design.extens.logE
import com.shouzhan.design.model.remote.result.LoginResult
import com.shouzhan.design.repository.LoginRepository

/**
 * @author danbin
 * @version LoginViewModel.java, v 0.1 2019-02-27 上午12:18 danbin
 */
class LoginViewModel : BaseViewModel() {

    val loginResult = MutableLiveData<LoginResult>()

    var mLoginRepository: LoginRepository = LoginRepository()

    fun toLogin(username: String, password: String) {
        addSubscribe(mLoginRepository.toLogin(username, password), object : ApiCallback<LoginResult>() {
            override fun onSuccess(data: LoginResult) {
                loginResult.value = data
            }

            override fun onFailure(resultMsg: String?, resultCode: Int) {
                super.onFailure(resultMsg, resultCode)
                logE("onFailure@@@@")
            }
        })
    }


}
