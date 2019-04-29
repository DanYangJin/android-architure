// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.shouzhan.design.compontent.loopview;

import android.os.Handler;
import android.os.Message;

/**
 * @author danbin
 * @version MessageHandler.java, v 0.1 2019-04-29 上午3:20 danbin
 */
final class MessageHandler extends Handler {
    public static final int WHAT_INVALIDATE_LOOP_VIEW = 1000;
    public static final int WHAT_SMOOTH_SCROLL = 2000;
    public static final int WHAT_ITEM_SELECTED = 3000;

    final LoopView loopview;

    MessageHandler(LoopView loopview) {
        this.loopview = loopview;
    }

    @Override
    public final void handleMessage(Message msg) {
        switch (msg.what) {
            case WHAT_INVALIDATE_LOOP_VIEW:
                loopview.invalidate();
                break;

            case WHAT_SMOOTH_SCROLL:
                loopview.smoothScroll(LoopView.ACTION.FLING);
                break;

            case WHAT_ITEM_SELECTED:
                loopview.onItemSelected();
                break;
            default:
                break;
        }
    }

}
