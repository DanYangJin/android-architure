package com.shouzhan.design.compontent.recyclerview;

import android.view.View;

/**
 * @author danbin
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
