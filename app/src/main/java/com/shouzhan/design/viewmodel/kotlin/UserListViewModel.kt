package com.shouzhan.design.viewmodel.kotlin

import android.arch.lifecycle.MutableLiveData
import com.shouzhan.design.base.BaseViewModel
import com.shouzhan.design.datasource.http.ApiCallback
import com.shouzhan.design.model.remote.result.BasePageResult
import com.shouzhan.design.model.remote.result.UserListResult
import com.shouzhan.design.repository.UserRepository

/**
 * @author danbin
 * @version KotlinViewModel.java, v 0.1 2019-02-27 上午12:18 danbin
 */
class UserListViewModel : BaseViewModel() {

    val userListResult = MutableLiveData<BasePageResult<UserListResult>>()

    private var mUserRepository: UserRepository = UserRepository()

    fun requestData(page: Int){
        addSubscribe( mUserRepository.getUserList(page), object : ApiCallback<BasePageResult<UserListResult>>() {
            override fun onSuccess(data: BasePageResult<UserListResult>?) {
                userListResult.value = data
            }
        })

    }


}
