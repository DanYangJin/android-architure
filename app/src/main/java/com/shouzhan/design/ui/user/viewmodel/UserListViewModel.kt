package com.shouzhan.design.ui.user.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import com.shouzhan.design.base.BasePageResult
import com.shouzhan.design.base.BaseViewModel
import com.shouzhan.design.datasource.http.HttpCompositeDisposable
import com.shouzhan.design.datasource.http.performance.PerformanceApiCallback
import com.shouzhan.design.repository.UserListRepository
import com.shouzhan.design.ui.user.model.remote.result.UserListResult


/**
 * @author danbin
 * @version UserListViewModel.java, v 0.1 2019-02-27 上午12:18 danbin
 */
class UserListViewModel : BaseViewModel() {

    private val userListResult = MutableLiveData<BasePageResult<UserListResult>>()

    private var userRepository: UserListRepository = UserListRepository()

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

    private val userListPagingResult = LivePagedListBuilder(UserListDataSourceFactory(httpDisposable, userRepository),
            PagedList.Config.Builder()
                    .setPageSize(10)
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(10)
                    .build()
    ).build()

    fun observerUserListPageResult(): LiveData<PagedList<UserListResult>> {
        return userListPagingResult
    }

    class UserListDataSourceFactory(var mHttpDisposable:  HttpCompositeDisposable, var mUserRepository: UserListRepository) : DataSource.Factory<Int, UserListResult>() {

        override fun create(): DataSource<Int, UserListResult> {
            return UserListDataSource(mHttpDisposable, mUserRepository)
        }

    }

    class UserListDataSource(private var mHttpDisposable : HttpCompositeDisposable, private var mUserRepository: UserListRepository) : PageKeyedDataSource<Int, UserListResult>() {

        override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, UserListResult>) {
            mHttpDisposable.addSubscribe(mUserRepository.getUserList(1), object : PerformanceApiCallback<BasePageResult<UserListResult>>() {
                override fun onSuccess(data: BasePageResult<UserListResult>?) {
                    callback.onResult(data!!.list!!.toMutableList(), null, 2)
                }
            })

        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UserListResult>) {
            mHttpDisposable.addSubscribe(mUserRepository.getUserList(params.key), object : PerformanceApiCallback<BasePageResult<UserListResult>>() {
                override fun onSuccess(data: BasePageResult<UserListResult>?) {
                    callback.onResult(data!!.list!!.toMutableList(), params.key + 1)
                }
            })
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UserListResult>) {
            mHttpDisposable.addSubscribe(mUserRepository.getUserList(params.key), object : PerformanceApiCallback<BasePageResult<UserListResult>>() {
                override fun onSuccess(data: BasePageResult<UserListResult>?) {
                    callback.onResult(data!!.list!!.toMutableList(), params.key - 1)
                }
            })
        }

    }


}
