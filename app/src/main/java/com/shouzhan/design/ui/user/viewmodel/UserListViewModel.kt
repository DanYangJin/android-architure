package com.shouzhan.design.ui.user.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.shouzhan.design.base.BasePageResult
import com.shouzhan.design.base.BaseViewModel
import com.shouzhan.design.datasource.http.performance.PerformanceApiCallback
import com.shouzhan.design.model.remote.result.UserListResult
import com.shouzhan.design.repository.UserListRepository


/**
 * @author danbin
 * @version UserListViewModel.java, v 0.1 2019-02-27 上午12:18 danbin
 */
class UserListViewModel : BaseViewModel() {

    private val userListResult = MutableLiveData<BasePageResult<UserListResult>>()

    private var userRepository: UserListRepository =
        UserListRepository()

    fun observerUserListResult(): MutableLiveData<BasePageResult<UserListResult>> {
        return userListResult
    }

    fun requestData(page: Int) {
        addSubscribe(userRepository.getUserList(page), object : PerformanceApiCallback<BasePageResult<UserListResult>>() {
            override fun onSuccess(data: BasePageResult<UserListResult>?) {
                userListResult.value = data
            }
        })
    }

}
