package com.shouzhan.design.ui.home.model;

import android.util.Log;

/**
 * @author danbin
 * @version TestServiceImpl.java, v 0.1 2020-03-15 3:37 PM danbin
 */
public class TestServiceImpl implements TestService {

    @Override
    public void test1() {
        Log.e("xss", "test1test1test1");
    }

}
