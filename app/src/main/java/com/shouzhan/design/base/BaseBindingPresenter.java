package com.shouzhan.design.base;

import android.view.View;

/**
 * @author danbin
 * @version BaseBindingPresenter.java, v 0.1 2019-01-24 上午11:02 danbin
 */
public interface BaseBindingPresenter extends View.OnClickListener{

    /**
     * 点击按钮事件
     *
     * @param view
     */
    @Override
    void onClick(View view);

}
