package com.shouzhan.design.compontent.recyclerview.callback;

import com.shouzhan.design.compontent.recyclerview.TwinklingRefreshLayout;

/**
 * @author lcodecore on 16/3/2.
 */
public interface PullListener {
    /**
     * 下拉中
     *
     * @param refreshLayout
     * @param fraction
     */
    void onPullingDown(TwinklingRefreshLayout refreshLayout, float fraction);

    /**
     * 上拉
     *
     * @param refreshLayout
     * @param fraction
     */
    void onPullingUp(TwinklingRefreshLayout refreshLayout, float fraction);

    /**
     * 下拉松开
     *
     * @param refreshLayout
     * @param fraction
     */
    void onPullDownReleasing(TwinklingRefreshLayout refreshLayout, float fraction);

    /**
     * 上拉松开
     *
     * @param refreshLayout
     * @param fraction
     */
    void onPullUpReleasing(TwinklingRefreshLayout refreshLayout, float fraction);

    /**
     * 刷新中
     *
     * @param refreshLayout
     */
    void onRefresh(TwinklingRefreshLayout refreshLayout);

    /**
     * 加载更多中
     *
     * @param refreshLayout
     */
    void onLoadMore(TwinklingRefreshLayout refreshLayout);

    /**
     * 手动调用finishRefresh或者finishLoadMore之后的回调
     */
    void onFinishRefresh();

    /**
     * 完成加载更多
     */
    void onFinishLoadMore();

    /**
     * 正在刷新时向上滑动屏幕，刷新被取消
     */
    void onRefreshCanceled();

    /**
     * 正在加载更多时向下滑动屏幕，加载更多被取消
     */
    void onLoadMoreCanceled();
}