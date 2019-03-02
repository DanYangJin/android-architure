package com.shouzhan.design.compontent.recyclerview.processor;

import android.view.MotionEvent;

/**
 * @author lcodecore on 16/3/2.
 */

public interface IDecorator {
    /**
     * 触摸事件回调
     * @param ev
     * @return
     * */
    boolean dispatchTouchEvent(MotionEvent ev);

    /**
     * 拦截事件回调
     * @param ev
     * @return
     * */
    boolean interceptTouchEvent(MotionEvent ev);
    /**
     * 消费事件回调
     * @param ev
     * @return
     * */
    boolean dealTouchEvent(MotionEvent ev);

    /**
     * 向下扫过
     * @param ev
     * @return
     * */
    void onFingerDown(MotionEvent ev);

    /**
     * 向上扫过
     * @param ev
     * @param isFling
     * @return
     * */
    void onFingerUp(MotionEvent ev, boolean isFling);

    /**
     * 手指滚动
     * @param e1
     * @param e2
     * @param distanceY
     * @param velocityY
     * @param velocityX
     * @param distanceX
     * @return
     * */
    void onFingerScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY, float velocityX, float velocityY);

    /**
     * 手指扫过
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * */
    void onFingerFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
}
