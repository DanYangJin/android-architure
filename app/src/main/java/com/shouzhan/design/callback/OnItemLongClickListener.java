package com.shouzhan.design.callback;

import android.view.View;

/**
 * @author danbin
 * @version OnItemLongClickListener.java, v 0.1 2019-02-27 上午12:11 danbin
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
