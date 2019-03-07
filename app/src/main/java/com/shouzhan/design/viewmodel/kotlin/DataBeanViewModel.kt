package com.shouzhan.design.viewmodel.kotlin

import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import com.shouzhan.design.base.BaseViewModel
import com.shouzhan.design.extens.logE
import com.shouzhan.design.model.javabean.DataBean
import com.shouzhan.design.repository.DataRepository

/**
 * @author danbin
 * @version FragmentViewModel.java, v 0.1 2019-03-07 下午9:35 danbin
 */
class FragmentOneViewModel: BaseViewModel() {

    val data = LivePagedListBuilder(CustomPageDataSourceFactory(DataRepository()), PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(20)
            .build()).build()


}

class CustomPageDataSource(private val repository: DataRepository) : PageKeyedDataSource<Int, DataBean>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, DataBean>) {
        val data = repository.loadData(params.requestedLoadSize)
        logE("loadInitial${params.requestedLoadSize}")
        callback.onResult(data, null, 2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DataBean>) {
        val data = repository.loadPageData(params.key,params.requestedLoadSize)
        logE("loadAfter: ${params.key}")
        data?.let {
            callback.onResult(data, params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DataBean>) {
        val data = repository.loadPageData(params.key,params.requestedLoadSize)
        logE("loadBefore: ${params.key}")
        data?.let {
            callback.onResult(data, params.key - 1)
        }
    }

}

class CustomPageDataSourceFactory(private val repository: DataRepository) : DataSource.Factory<Int, DataBean>() {
    override fun create(): DataSource<Int, DataBean> {
        return CustomPageDataSource(repository)
    }
}