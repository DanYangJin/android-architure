package com.shouzhan.design.compontent.recyclerview.processor;

import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import com.shouzhan.design.compontent.recyclerview.TwinklingRefreshLayout;
import com.shouzhan.design.compontent.recyclerview.utils.ScrollingUtils;


/**
 * @author lcodecore on 16/3/2.
 */

public class OverScrollDecorator extends Decorator {

    /**
     * 主要为了监测Fling的动作,实现越界回弹
     */
    private float mVelocityY;

    /**
     * 满足越界的手势的最低速度(默认3000)
     */
    private static final int OVER_SCROLL_MIN_VX = 3000;

    /**
     * 针对View的延时策略
     */
    private static final int MSG_START_COMPUTE_SCROLL = 0;
    private static final int MSG_CONTINUE_COMPUTE_SCROLL = 1;
    private static final int MSG_STOP_COMPUTE_SCROLL = 2;

    /**
     * 当前计算次数
     */
    private int mCurDelayTimes = 0;
    /**
     * 10ms计算一次,总共计算20次
     */
    private static final int ALL_DELAY_TIMES = 60;

    public OverScrollDecorator(TwinklingRefreshLayout.CoContext processor, IDecorator decorator1) {
        super(processor, decorator1);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return decorator != null && decorator.dispatchTouchEvent(ev);
    }

    @Override
    public boolean interceptTouchEvent(MotionEvent ev) {
        return decorator != null && decorator.interceptTouchEvent(ev);
    }

    @Override
    public boolean dealTouchEvent(MotionEvent e) {
        return decorator != null && decorator.dealTouchEvent(e);
    }

    private boolean preventTopOverScroll = false;
    private boolean preventBottomOverScroll = false;
    private boolean checkOverScroll = false;

    @Override
    public void onFingerDown(MotionEvent ev) {
        if (decorator != null) {
            decorator.onFingerDown(ev);
        }
        preventTopOverScroll = ScrollingUtils.isViewToTop(cp.getTargetView(), cp.getTouchSlop());
        preventBottomOverScroll = ScrollingUtils.isViewToBottom(cp.getTargetView(), cp.getTouchSlop());
    }

    @Override
    public void onFingerUp(MotionEvent ev, boolean isFling) {
        if (decorator != null) {
            decorator.onFingerUp(ev, checkOverScroll && isFling);
        }
        checkOverScroll = false;
    }

    @Override
    public void onFingerScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY, float velocityX, float velocityY) {
        if (decorator != null) {
            decorator.onFingerScroll(e1, e2, distanceX, distanceY, velocityX, velocityY);
        }
    }

    @Override
    public void onFingerFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (decorator != null) {
            decorator.onFingerFling(e1, e2, velocityX, velocityY);
        }
        //fling时才触发OverScroll，获取速度并采用演示策略估算View是否滚动到边界
        if (!cp.enableOverScroll()) {
            return;
        }

        int dy = (int) (e2.getY() - e1.getY());
        if (dy < -cp.getTouchSlop() && preventBottomOverScroll) {
            return;//控件滚动在底部且向上fling
        }
        if (dy > cp.getTouchSlop() && preventTopOverScroll) {
            return;//控件滚动在顶部且向下fling
        }

        mVelocityY = velocityY;
        if (Math.abs(mVelocityY) >= OVER_SCROLL_MIN_VX) {
            mHandler.sendEmptyMessage(MSG_START_COMPUTE_SCROLL);
            checkOverScroll = true;
        } else {
            mVelocityY = 0;
            mCurDelayTimes = ALL_DELAY_TIMES;
        }
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int mTouchSlop = cp.getTouchSlop();
            switch (msg.what) {
                case MSG_START_COMPUTE_SCROLL:
                    mCurDelayTimes = -1;
                case MSG_CONTINUE_COMPUTE_SCROLL:
                    mCurDelayTimes++;

                    View mChildView = cp.getTargetView();

                    if (cp.allowOverScroll()) {
                        if (mVelocityY >= OVER_SCROLL_MIN_VX) {
                            if (ScrollingUtils.isViewToTop(mChildView, mTouchSlop)) {
                                cp.getAnimProcessor().animOverScrollTop(mVelocityY, mCurDelayTimes);
                                mVelocityY = 0;
                                mCurDelayTimes = ALL_DELAY_TIMES;
                            }
                        } else if (mVelocityY <= -OVER_SCROLL_MIN_VX) {
                            if (ScrollingUtils.isViewToBottom(mChildView, mTouchSlop)) {
                                cp.getAnimProcessor().animOverScrollBottom(mVelocityY, mCurDelayTimes);
                                mVelocityY = 0;
                                mCurDelayTimes = ALL_DELAY_TIMES;
                            }
                        }
                    }

                    //计算未超时，继续发送消息并循环计算
                    if (mCurDelayTimes < ALL_DELAY_TIMES) {
                        mHandler.sendEmptyMessageDelayed(MSG_CONTINUE_COMPUTE_SCROLL, 10);
                    }
                    break;
                case MSG_STOP_COMPUTE_SCROLL:
                    mCurDelayTimes = ALL_DELAY_TIMES;
                    break;
                default:
                    break;
            }
        }
    };
}
