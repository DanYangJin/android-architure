package com.shouzhan.design.callback;

import android.view.View;

/**
 * @author danbin
 * @version OnItemClickListener.java, v 0.1 2019-02-27 上午12:11 danbin
 */
public interface OnItemClickListener {

    /**
     * 点击事件
     *
     * @param view
     * @param position
     */
    void onItemClick(View view, int position);
}
