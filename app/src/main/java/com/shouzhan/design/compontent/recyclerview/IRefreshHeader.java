package com.shouzhan.design.compontent.recyclerview;

import android.view.View;

/**
 * @author danbin
 */
public interface IRefreshHeader {

    int STATE_NORMAL = 0;
    int STATE_RELEASE_TO_REFRESH = 1;
    int STATE_REFRESHING = 2;
    int STATE_DONE = 3;

    int TYPE_HEADER_NORMAL = 0;
    int TYPE_HEADER_MATERIAL = 1;

    /**
     * 重置
     */
    void onReset();

    /**
     * 处于可以刷新的状态，已经过了指定距离
     */
    void onPrepare();

    /**
     * 正在刷新
     */
    void onRefreshing();

    /**
     * 下拉移动
     *
     * @param offSet
     * @param sumOffSet
     */
    void onMove(float offSet, float sumOffSet);

    /**
     * 下拉松开
     *
     * @return
     */
    boolean onRelease();

    /**
     * 下拉刷新完成
     */
    void refreshComplete();

    /**
     * 获取HeaderView
     *
     * @return
     */
    View getHeaderView();

    /**
     * 获取Header的显示高度
     *
     * @return
     */
    int getVisibleHeight();

    /**
     * 获取Header的显示宽度,横向滑动时使用
     *
     * @return
     */
    int getVisibleWidth();

    /**
     * 获取Header的类型
     *
     * @return
     */
    int getType();
}