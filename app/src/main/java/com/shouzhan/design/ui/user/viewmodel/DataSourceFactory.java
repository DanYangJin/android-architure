package com.shouzhan.design.ui.user.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.shouzhan.design.datasource.http.HttpCompositeDisposable;
import com.shouzhan.design.repository.UserListRepository;
import com.shouzhan.design.model.remote.result.UserListResult;

/**
 * @author danbin
 * @version DataSourceFactory.java, v 0.1 2019-02-27 上午12:18 danbin
 */
public class DataSourceFactory
        extends DataSource.Factory<Integer, UserListResult> {

    private MutableLiveData<LocalDataSource> mSourceLiveData =
            new MutableLiveData<>();

    private HttpCompositeDisposable mHttpDisposable;
    private UserListRepository mUserListRepository;

    public DataSourceFactory(HttpCompositeDisposable httpDisposable, UserListRepository userListRepository) {
        this.mHttpDisposable = httpDisposable;
        this.mUserListRepository = userListRepository;
    }

    @Override
    public DataSource<Integer, UserListResult> create() {
        LocalDataSource source = new LocalDataSource(mHttpDisposable, mUserListRepository);
        mSourceLiveData.postValue(source);
        return source;
    }

}