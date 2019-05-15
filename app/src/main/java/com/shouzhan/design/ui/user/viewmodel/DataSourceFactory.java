package com.shouzhan.design.ui.user.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.shouzhan.design.ui.user.model.javabean.DataInfo;

/**
 * @author danbin
 * @version DataSourceFactory.java, v 0.1 2019-02-27 上午12:18 danbin
 */
public class DataSourceFactory
        extends DataSource.Factory<Integer, DataInfo> {

    private MutableLiveData<LocalDataSource> mSourceLiveData =
            new MutableLiveData<>();

    @Override
    public DataSource<Integer, DataInfo> create() {
        LocalDataSource source = new LocalDataSource();
        mSourceLiveData.postValue(source);
        return source;
    }

}