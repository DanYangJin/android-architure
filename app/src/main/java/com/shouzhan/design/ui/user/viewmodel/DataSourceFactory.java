package com.shouzhan.design.ui.user.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.shouzhan.design.datasource.http.CommonCompositeDisposable;
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

    private CommonCompositeDisposable mHttpDisposable;
    private UserListRepository mUserListRepository;

    public DataSourceFactory(CommonCompositeDisposable httpDisposable, UserListRepository userListRepository) {
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