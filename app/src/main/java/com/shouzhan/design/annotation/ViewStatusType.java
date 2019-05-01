package com.shouzhan.design.annotation;

/**
 * @author danbin
 * @version ViewStatusType.java, v 0.1 2019-04-27 下午10:17 danbin
 */
public @interface ViewStatusType {

    /**
     * 空视图
     * */
    int EMPTY_DATA_ERROR = 0;

    /**
     * 无网络视图
     * */
    int NO_NETWORK_ERROR = 1;
    /**
     * 正在刷新
     * */
    int REFRESHING = 2;

}
