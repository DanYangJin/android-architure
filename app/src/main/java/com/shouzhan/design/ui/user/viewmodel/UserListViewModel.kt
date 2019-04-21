package com.shouzhan.design.ui.user.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.shouzhan.design.base.BasePageResult
import com.shouzhan.design.base.BaseViewModel
import com.shouzhan.design.datasource.http.performance.PerformanceApiCallback
import com.shouzhan.design.repository.UserListRepository
import com.shouzhan.design.ui.user.model.remote.result.UserListResult

/**
 * @author danbin
 * @version UserListViewModel.java, v 0.1 2019-02-27 上午12:18 danbin
 */
class UserListViewModel : BaseViewModel() {

    val userListResult = MutableLiveData<BasePageResult<UserListResult>>()

    private var mUserRepository: UserListRepository = UserListRepository()

    fun requestData(page: Int){
        addSubscribe( mUserRepository.getUserList(page), object : PerformanceApiCallback<BasePageResult<UserListResult>>() {
            override fun onSuccess(data: BasePageResult<UserListResult>?) {
                userListResult.value = data
            }
        })
    }


}
