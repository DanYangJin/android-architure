package com.shouzhan.design.compontent.recyclerview.callback;

import android.view.MotionEvent;

/**
 * @author lcodecore on 16/3/2.
 */

public interface OnGestureListener {

    /**
     * 向下
     * @param ev
     */
    void onDown(MotionEvent ev);

    /**
     * 滑动
     * @param downEvent
     * @param distanceX
     * @param distanceY
     */
    void onScroll(MotionEvent downEvent, MotionEvent currentEvent, float distanceX, float distanceY);

    /**
     * 向上
     * @param ev
     * @param isFling
     */
    void onUp(MotionEvent ev, boolean isFling);

    /**
     * 扫过
     * @param downEvent
     * @param upEvent
     * @param velocityX
     * @param velocityY
     * */
    void onFling(MotionEvent downEvent, MotionEvent upEvent, float velocityX, float velocityY);
}