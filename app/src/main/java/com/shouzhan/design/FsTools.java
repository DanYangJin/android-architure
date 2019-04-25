package com.shouzhan.design;

/**
 * @author danbin
 * @version FsTools.java, v 0.1 2019-04-24 下午10:10 danbin
 */
public class FsTools {

    static {
        System.loadLibrary("FsTools");
    }

    /**
     * 获取不同环境下的请求KEY
     *
     * @param type    构建环境
     * @return
     */
    public native static String getKeyFromJNI(int type);

}
