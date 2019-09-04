package com.shouzhan.design.ui.mvp;

import android.view.KeyEvent;

import com.shouzhan.design.base.IBasePresenter;
import com.shouzhan.design.base.IBaseView;

/**
 * @author danbin
 * @version MvpContract.java, v 0.1 2019-07-17 00:36 danbin
 */
public interface MvpContract {

    interface View extends IBaseView {
        /**
         * 更新标题
         *
         * @param title
         */
        void updateTitleBar(String title);
    }

    interface Presenter extends IBasePresenter {
        /**
         * 点击切换标题按钮
         */
        void switchTitle();

        /**
         * 按键事件
         *
         * @param keyCode
         * @param keyEvent
         * @return
         */
        boolean onKeyUp(int keyCode, KeyEvent keyEvent);
    }

}
