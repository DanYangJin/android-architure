package com.shouzhan.design.view.kotlin.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.shouzhan.design.base.BaseViewModel
import com.shouzhan.design.datasource.http.LoanApiCallback
import com.shouzhan.design.datasource.http.PerformanceApiCallback
import com.shouzhan.design.model.javabean.MerchantLoanTypeInfo
import com.shouzhan.design.model.remote.result.BasePageResult
import com.shouzhan.design.model.remote.result.UserListResult
import com.shouzhan.design.repository.MerchantLoanRepository
import com.shouzhan.design.repository.UserRepository

/**
 * @author danbin
 * @version UserListViewModel.java, v 0.1 2019-02-27 上午12:18 danbin
 */
class UserListViewModel : BaseViewModel() {

    val userListResult = MutableLiveData<BasePageResult<UserListResult>>()

    private var mUserRepository: UserRepository = UserRepository()
    private var mLoanRepository: MerchantLoanRepository = MerchantLoanRepository()

    fun requestData(page: Int){
        addSubscribe( mUserRepository.getUserList(page), object : PerformanceApiCallback<BasePageResult<UserListResult>>() {
            override fun onSuccess(data: BasePageResult<UserListResult>?) {
                userListResult.value = data
            }
        })
    }

    fun getMerchantLoansType() {
        addSubscribe(mLoanRepository.getMerchantLoansType("1538976"),   object : LoanApiCallback<MerchantLoanTypeInfo>() {

            override fun onSuccess(data: MerchantLoanTypeInfo?) {
                Log.e("Catch", data.toString())
            }

        })
    }


}
