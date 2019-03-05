package com.shouzhan.design.compontent.lrecyclerview;

import android.view.View;

/**
 * @author danbin
 */
public interface OnItemLongClickListener {
    /**
     * 长按事件
     *
     * @param view
     * @param position
     */
    void onItemLongClick(View view, int position);
}
