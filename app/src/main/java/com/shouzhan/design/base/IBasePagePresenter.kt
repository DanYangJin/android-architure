package com.shouzhan.design.base;

import android.support.annotation.LayoutRes

/**
 * @author danbin
 * @version IBasePagePresenter.java, v 0.1 2019-01-23 上午5:26 danbin
 * 控制器通用Presenter
 */
interface IBasePagePresenter {

    /**
     * 获取布局
     * */
    @LayoutRes
    fun getLayoutId(): Int

    /**
     * 初始化视图层
     * */
    fun initView()

    /**
     * 获取页面之间传递数据
     */
    fun extraIntentData()

    /**
     * 统一获取数据入口
     * */
    fun getData()

    /**
     * 设置状态栏
     * */
    fun initImmersionBar()

    /**
     * 显示加载View
     * */
    fun showLoadingView()

    /**
     * 隐藏加载View
     * */
    fun hideLoadingView()

    /**
     * 显示空视图
     * */
    fun showEmptyView()

    /**
     * 显示错误视图
     * */
    fun showErrorView()

}

enum class ViewStatus {
    EMPTY,
    ERROR,
    LOADING
}