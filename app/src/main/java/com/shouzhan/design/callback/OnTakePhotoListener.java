package com.shouzhan.design.callback;

/**
 * @author danbin
 * @version OnTakePhotoListener.java, v 0.1 2019-02-27 上午12:11 danbin
 */
public interface OnTakePhotoListener {
    /**
     * 回调图片地址
     *
     * @param path
     */
    void onTakePath(String path);
}