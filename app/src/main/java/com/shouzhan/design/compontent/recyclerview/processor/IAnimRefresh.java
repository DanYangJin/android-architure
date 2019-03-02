package com.shouzhan.design.compontent.recyclerview.processor;

/**
 * @author lcodecore on 16/3/2.
 */

public interface IAnimRefresh {
    /**
     * 动画刷新
     * @param moveY
     * */
    void scrollHeadByMove(float moveY);
    /**
     * 动画刷新
     * @param moveY
     * */
    void scrollBottomByMove(float moveY);
    /**
     * 动画刷新
     * */
    void animHeadToRefresh();
    /**
     * 动画刷新
     * @param isFinishRefresh
     * */
    void animHeadBack(boolean isFinishRefresh);
    /**
     * 动画刷新
     * @param vy
     * */
    void animHeadHideByVy(int vy);
    /**
     * 动画刷新
     * */
    void animBottomToLoad();
    /**
     * 动画刷新
     * @param isFinishRefresh
     * */
    void animBottomBack(boolean isFinishRefresh);
    /**
     * 动画刷新
     * @param vy
     * */
    void animBottomHideByVy(int vy);
}
