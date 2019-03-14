package com.shouzhan.design.compontent.recyclerview;

import android.support.v7.widget.GridLayoutManager;

/**
 * @author danbin
 */
public interface SpanSizeLookup {
    /**
     * 获取SpanSize
     *
     * @param gridLayoutManager
     * @param position
     * @return
     */
    int getSpanSize(GridLayoutManager gridLayoutManager, int position);
}