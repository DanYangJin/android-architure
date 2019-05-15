package com.shouzhan.design.ui.user.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.shouzhan.design.base.BaseViewModel;
import com.shouzhan.design.ui.user.model.javabean.DataInfo;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author danbin
 * @version UserPagingViewModel.java, v 0.1 2019-05-16 上午12:26 danbin
 */
public class UserPagingViewModel extends BaseViewModel {

    private Executor myExecutor = Executors.newSingleThreadExecutor();

    private PagedList.Config myPagingConfig = new PagedList.Config.Builder()
            .setInitialLoadSizeHint(20)
            .setPageSize(10)
            .setPrefetchDistance(30)
            .setEnablePlaceholders(false)
            .build();

    private DataSource.Factory<Integer, DataInfo> myConcertDataSource =
            new DataSourceFactory();

    public LiveData<PagedList<DataInfo>> getConcertList() {
        return concertList;
    }

    private LiveData<PagedList<DataInfo>> concertList =
            new LivePagedListBuilder<>(myConcertDataSource, myPagingConfig)
                    .setFetchExecutor(myExecutor)
                    .build();

    public void invalidateDataSource() {
        PagedList<DataInfo> pagedList = concertList.getValue();
        if (pagedList != null) {
            pagedList.getDataSource().invalidate();
        }
    }

}
