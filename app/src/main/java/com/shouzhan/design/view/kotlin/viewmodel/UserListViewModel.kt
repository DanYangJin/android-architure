package com.shouzhan.design.view.kotlin.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.shouzhan.design.base.BaseViewModel
import com.shouzhan.design.datasource.http.loan.LoanApiCallback
import com.shouzhan.design.datasource.http.performance.PerformanceApiCallback
import com.shouzhan.design.model.remote.result.BasePageResult
import com.shouzhan.design.model.remote.result.BountyInfoResult
import com.shouzhan.design.model.remote.result.MerchantLoanTypeResult
import com.shouzhan.design.model.remote.result.UserListResult
import com.shouzhan.design.repository.BountyRepository
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
    private var mJavaRepository: BountyRepository = BountyRepository()

    fun requestData(page: Int){
        addSubscribe( mUserRepository.getUserList(page), object : PerformanceApiCallback<BasePageResult<UserListResult>>() {
            override fun onSuccess(data: BasePageResult<UserListResult>?) {
                userListResult.value = data
            }
        })
    }

    fun getMerchantLoansType() {
        addSubscribe(mLoanRepository.getMerchantLoansType("1538976"),   object : LoanApiCallback<MerchantLoanTypeResult>() {

            override fun onSuccess(data: MerchantLoanTypeResult?) {
                Log.e("Catch", data.toString())
            }

        })
    }

    fun getBountyData() {
        addSubscribe(mJavaRepository.bountyData,   object : LoanApiCallback<BountyInfoResult>() {

            override fun onSuccess(data: BountyInfoResult?) {
                Log.e("Catch", data.toString())
            }

        })
    }


}
