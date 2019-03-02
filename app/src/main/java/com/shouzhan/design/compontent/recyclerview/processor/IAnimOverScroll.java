package com.shouzhan.design.compontent.recyclerview.processor;

/**
 * @author lcodecore on 16/3/2.
 */

public interface IAnimOverScroll {
    /**
     * 滚动到顶部
     * @param vy
     * @param computeTimes
     * */
    void animOverScrollTop(float vy, int computeTimes);
    /**
     * 滚动到底部
     * @param vy
     * @param computeTimes
     * */
    void animOverScrollBottom(float vy, int computeTimes);
}
