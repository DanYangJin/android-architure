package com.shouzhan.design.ui.user.viewmodel;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;

import com.shouzhan.design.ui.user.model.javabean.DataInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author danbin
 * @version LocalTestDataSource.java, v 0.1 2019-02-27 上午12:18 danbin
 */
public class LocalDataSource extends PositionalDataSource<DataInfo> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams params,
                            final @NonNull LoadInitialCallback<DataInfo> callback) {
        final int startPosition = 0;
        List<DataInfo> list = buildDataList(startPosition, params.requestedLoadSize);
        callback.onResult(list, 0);
    }

    @Override
    public void loadRange(@NonNull final LoadRangeParams params,
                          @NonNull final LoadRangeCallback<DataInfo> callback) {
        List<DataInfo> list = buildDataList(params.startPosition, params.loadSize);
        callback.onResult(list);
    }

    private List<DataInfo> buildDataList(int startPosition, int loadSize) {
        List<DataInfo> list = new ArrayList<>();
        DataInfo bean;
        for (int i = startPosition; i < startPosition + loadSize; i++) {
            bean = new DataInfo();
            bean.id = i;
            bean.content = String.format(Locale.getDefault(), "第%d条数据", i + 1);
            list.add(bean);
        }
        return list;
    }
}