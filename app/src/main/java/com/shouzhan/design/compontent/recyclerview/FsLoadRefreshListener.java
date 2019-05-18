package com.shouzhan.design.compontent.recyclerview;

import android.util.Log;

/**
 * @author danbin
 * @version FsLoadRefreshListener.java, v 0.1 2019-05-18 下午5:57 danbin
 */
public class FsLoadRefreshListener implements OnLoadRefreshListener {

    private static final String TAG = FsLoadRefreshListener.class.getSimpleName();

    @Override
    public void onRefresh() {
        Log.e(TAG, "onRefresh");
    }

    @Override
    public void onLoadMore() {
        Log.e(TAG, "onLoadMore");
    }

}
