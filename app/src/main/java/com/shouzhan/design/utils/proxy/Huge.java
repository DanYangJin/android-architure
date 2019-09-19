package com.shouzhan.design.utils.proxy;

import com.fshows.android.stark.utils.FsLogUtil;

/**
 * @author danbin
 * @version Huge.java, v 0.1 2019-09-19 09:13 danbin
 */
public class Huge implements Star {

    private static final String TAG = Huge.class.getSimpleName();

    @Override
    public void sing(String song) {
        FsLogUtil.error(TAG, "胡歌演唱： " + song);
    }

    @Override
    public String act(String teleplay) {
        FsLogUtil.error(TAG, "胡歌决定出演电视剧： " + teleplay);
        return "胡歌答应出演电视剧： " + teleplay;
    }

}
