package com.shouzhan.design.compontent.recyclerview;

import android.view.View;

/**
 * @author danbin
 * @version FsRecyclerViewGlobalConfig.java, v 0.1 2019-05-17 上午11:40 danbin
 */
public class FsRecyclerViewGlobalConfig {

    /**
     * 全局配置参数
     * */
    public boolean mGlobalPullRefreshEnabled = true;
    public boolean mGlobalLoadMoreEnabled = true;
    public boolean mGlobalNetErrorEnabled = false;

    /**
     * 全局配置视图
     * */
    public View mGlobalEmptyView = null;
    public View mGlobalNetErrorView = null;
    public View mGlobalFooterView = null;
    public View mGlobalHeaderView = null;

    /**
     * 设置是否开启下拉刷新
     * */
    public FsRecyclerViewGlobalConfig setGlobalPullRefreshEnabled(boolean enabled) {
        this.mGlobalPullRefreshEnabled = enabled;
        return this;
    }

    /**
     * 设置是否开启上拉加载
     * */
    public FsRecyclerViewGlobalConfig setGlobalLoadMoreEnabled(boolean enabled) {
        this.mGlobalLoadMoreEnabled = enabled;
        return this;
    }

    /**
     * 设置是否开启网络错误
     * */
    public FsRecyclerViewGlobalConfig setGlobalNetErrorEnabled(boolean enabled) {
        this.mGlobalNetErrorEnabled = enabled;
        return this;
    }

    /**
     * 设置空视图
     * */
    public FsRecyclerViewGlobalConfig setGlobalEmptyView(View emptyView) {
        this.mGlobalEmptyView = emptyView;
        return this;
    }

    /**
     * 设置网络错误视图
     * */
    public FsRecyclerViewGlobalConfig setGlobalNetErrorView(View netErrorView) {
        this.mGlobalNetErrorView = netErrorView;
        return this;
    }

    /**
     * 设置底部视图
     * */
    public FsRecyclerViewGlobalConfig setGlobalFooterView(View footerView) {
        this.mGlobalFooterView = footerView;
        return this;
    }

    /**
     * 设置头部视图
     * */
    public FsRecyclerViewGlobalConfig setGlobalHeaderView(View headerView) {
        this.mGlobalHeaderView = headerView;
        return this;
    }

}
