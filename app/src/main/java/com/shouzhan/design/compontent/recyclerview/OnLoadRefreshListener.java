package com.shouzhan.design.compontent.recyclerview;


/**
 * @author danbin
 */
public interface OnLoadRefreshListener {
    /**
     * 下拉刷新回调
     * */
    void onRefresh();

    /**
     * 加载更多回调
     * */
    void onLoadMore();
}
