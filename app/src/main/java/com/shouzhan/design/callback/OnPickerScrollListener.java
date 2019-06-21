package com.shouzhan.design.callback;

/**
 * @author danbin
 * @version OnPickerScrollListener.java, v 0.1 2019-02-27 上午12:11 danbin
 * 滚动时右边Text的回调方法
 */
public interface OnPickerScrollListener {
    /**
     * 数据回调
     *
     * @param dataString
     */
    void selectData(String dataString);
}