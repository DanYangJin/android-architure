package com.shouzhan.design.ui.user.viewmodel;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.shouzhan.design.base.BasePageResult;
import com.shouzhan.design.datasource.http.CommonCompositeDisposable;
import com.shouzhan.design.datasource.http.performance.PerformanceApiCallback;
import com.shouzhan.design.repository.UserListRepository;
import com.shouzhan.design.model.remote.result.UserListResult;

/**
 * @author danbin
 * @version LocalDataSource.java, v 0.1 2019-02-27 上午12:18 danbin
 */
public class LocalDataSource extends PageKeyedDataSource<Integer, UserListResult> {

    private static final String TAG = LocalDataSource.class.getSimpleName();

    private CommonCompositeDisposable mHttpDisposable;
    private UserListRepository mUserListRepository;

    public LocalDataSource(CommonCompositeDisposable httpDisposable, UserListRepository userListRepository) {
        this.mHttpDisposable = httpDisposable;
        this.mUserListRepository = userListRepository;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, UserListResult> callback) {
        Log.e(TAG, "loadInitial: " + params.requestedLoadSize);
        mHttpDisposable.addSubscribe(mUserListRepository.getUserList(1), new PerformanceApiCallback<BasePageResult<UserListResult>>() {
            @Override
            protected void onSuccess(BasePageResult<UserListResult> data) {
                callback.onResult(data.getList(), 1, 2);
            }

        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, UserListResult> callback) {
        // ignored, since we only ever append to our initial load
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, UserListResult> callback) {
        Log.e(TAG, "loadAfter: " + params.key + " , " + params.requestedLoadSize);
        mHttpDisposable.addSubscribe(mUserListRepository.getUserList(params.key), new PerformanceApiCallback<BasePageResult<UserListResult>>() {
            @Override
            protected void onSuccess(BasePageResult<UserListResult> data) {
                callback.onResult(data.getList(), params.key + 1);
            }

        });
    }

}