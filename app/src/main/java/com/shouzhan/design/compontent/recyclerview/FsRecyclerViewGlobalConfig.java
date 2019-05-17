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
     * 构造器
     * */
    public static FsRecyclerViewGlobalConfig build() {
        return new FsRecyclerViewGlobalConfig();
    }

    /**
     * 设置属性
     * */
    public FsRecyclerViewGlobalConfig setGlobalPullRefreshEnabled(boolean enabled) {
        this.mGlobalPullRefreshEnabled = enabled;
        return this;
    }

    public FsRecyclerViewGlobalConfig setGlobalLoadMoreEnabled(boolean enabled) {
        this.mGlobalLoadMoreEnabled = enabled;
        return this;
    }

    public FsRecyclerViewGlobalConfig setGlobalNetErrorEnabled(boolean enabled) {
        this.mGlobalNetErrorEnabled = enabled;
        return this;
    }

    public FsRecyclerViewGlobalConfig setGlobalEmptyView(View mGlobalEmptyView) {
        this.mGlobalEmptyView = mGlobalEmptyView;
        return this;
    }

    public FsRecyclerViewGlobalConfig setGlobalNetErrorView(View mGlobalNetErrorView) {
        this.mGlobalNetErrorView = mGlobalNetErrorView;
        return this;
    }

    public FsRecyclerViewGlobalConfig setGlobalFooterView(View mGlobalFooterView) {
        this.mGlobalFooterView = mGlobalFooterView;
        return this;
    }

    public FsRecyclerViewGlobalConfig setGlobalHeaderView(View mGlobalHeaderView) {
        this.mGlobalHeaderView = mGlobalHeaderView;
        return this;
    }

}
