package com.shouzhan.design.ui.mvp;

import com.shouzhan.design.base.BasePresenter;
import com.shouzhan.design.base.BaseView;

/**
 * @author danbin
 * @version MvpContract.java, v 0.1 2019-07-17 00:36 danbin
 */
public interface MvpContract {

    interface View extends BaseView {
        /**
         * 更新标题
         * @param title
         * */
        void updateTitleBar(String title);
    }

    interface Presenter extends BasePresenter<MvpContract.View> {
        /**
         * 点击切换标题按钮
         * */
        void switchTitle();
    }

}
