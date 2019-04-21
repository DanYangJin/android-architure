package com.shouzhan.design.compontent.recyclerview;

import android.graphics.Rect;

/**
 * @author danbin
 */
public interface IPinnedHeaderDecoration {

    /**
     * 获取PinnedHeader区域
     *
     * @return
     */
    Rect getPinnedHeaderRect();

    /**
     * 获取PinnedHeader位置
     *
     * @return
     */
    int getPinnedHeaderPosition();

}
