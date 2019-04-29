package com.shouzhan.design.callback;

/**
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