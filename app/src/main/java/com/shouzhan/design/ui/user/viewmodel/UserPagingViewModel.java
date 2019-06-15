package com.shouzhan.design.ui.user.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.shouzhan.design.base.BaseViewModel;
import com.shouzhan.design.repository.UserListRepository;
import com.shouzhan.design.model.remote.result.UserListResult;

/**
 * @author danbin
 * @version UserPagingViewModel.java, v 0.1 2019-05-16 上午12:26 danbin
 */
public class UserPagingViewModel extends BaseViewModel {

    private UserListRepository userRepository = new UserListRepository();

    private PagedList.Config myPagingConfig = new PagedList.Config.Builder()
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .setPrefetchDistance(30)
            .setEnablePlaceholders(false)
            .build();

    private DataSource.Factory<Integer, UserListResult> myConcertDataSource =
            new DataSourceFactory(getHttpDisposable(), userRepository);

    public LiveData<PagedList<UserListResult>> getConcertList() {
        return concertList;
    }

    private LiveData<PagedList<UserListResult>> concertList =
            new LivePagedListBuilder<>(myConcertDataSource, myPagingConfig)
                    .build();

    public void invalidateDataSource() {
        PagedList<UserListResult> pagedList = concertList.getValue();
        if (pagedList != null) {
            pagedList.getDataSource().invalidate();
        }
    }

}
