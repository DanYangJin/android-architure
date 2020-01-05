package com.shouzhan.design.compontent.recyclerview;

import androidx.recyclerview.widget.GridLayoutManager;

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