package com.shouzhan.design.compontent.recyclerview;

import android.view.View;

/**
 * @author lcodecore on 16/3/2.
 */

public interface IBottomView {

    /**
     * 获取当前视图
     * @return View
     * */
    View getView();

    /**
     * 上拉准备加载更多的动作
     *
     * @param fraction      上拉高度与Bottom总高度之比
     * @param maxBottomHeight 底部部可拉伸最大高度
     * @param bottomHeight    底部高度
     */
    void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight);

    /**
     * 开始动画回调
     * @param maxBottomHeight
     * @param bottomHeight
     * */
    void startAnim(float maxBottomHeight, float bottomHeight);

    /**
     * 上拉释放过程
     * @param bottomHeight
     * @param fraction
     * @param maxBottomHeight
     */
    void onPullReleasing(float fraction, float maxBottomHeight, float bottomHeight);

    /**
     * 完成动画回调
     * */
    void onFinish();
    /**
     * 用于在必要情况下复位View，清除动画
     */
    void reset();
}
