package com.shouzhan.design.compontent.recyclerview;

import android.view.View;

import com.shouzhan.design.compontent.recyclerview.callback.OnAnimEndListener;


/**
 * @author lcodecore on 16/3/2.
 */

public interface IHeaderView {

    /**
     * 获取当前视图
     * @return View
     * */
    View getView();

    /**
     * 下拉准备刷新动作
     *
     * @param fraction      当前下拉高度与总高度的比
     * @param maxHeadHeight
     * @param headHeight
     */
    void onPullingDown(float fraction, float maxHeadHeight, float headHeight);

    /**
     * 下拉释放过程
     *
     * @param fraction
     * @param maxHeadHeight
     * @param headHeight
     */
    void onPullReleasing(float fraction, float maxHeadHeight, float headHeight);

    /**
     * 开始动画回调
     * @param headHeight
     * @param maxHeadHeight
     * */
    void startAnim(float maxHeadHeight, float headHeight);

    /**
     * 完成动画回调
     * @param animEndListener
     * */
    void onFinish(OnAnimEndListener animEndListener);

    /**
     * 用于在必要情况下复位View，清除动画
     */
    void reset();
}
