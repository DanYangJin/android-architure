package com.shouzhan.design.base

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shouzhan.design.App
import com.shouzhan.design.extens.logE
import com.shouzhan.design.extens.no
import com.shouzhan.design.extens.yes


/**
 * @author danbin
 * @version LazyFragment.java, v 0.1 2019-02-26 下午10:49 danbin
 */
abstract class LazyFragment : Fragment(), BaseViewPresenter {

    protected var isInitView = false
    private var isFirstVisible = true
    private var mRootView: View? = null

    protected lateinit var mContext: Context

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        logE("@@@setUserVisibleHint@@@===>${javaClass.simpleName} = $isVisibleToUser")
        (isVisibleToUser && isInitView && isFirstVisible).yes {
            getData()
            isInitView = false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        logE("@@@onCreateView@@@")
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false)
        }
        return mRootView
    }

    /**
     * 该方法会执行两次
     * */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        logE("@@@onActivityCreated@@@")
        mContext = activity ?: throw Exception("activity 为null")
        isInitView.no {
            initView()
            isInitView = true
        }
        userVisibleHint.yes {
            getData()
            isFirstVisible = false
        }
    }

    /**
     * 获取ViewModel
     * @param modelClass
     * @param <T>
     * @return
     */
    protected fun <T : BaseViewModel> vmProviders(@NonNull modelClass: Class<T>): T {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(App.getInstance()).create(modelClass)
    }

    override fun onResume() {
        super.onResume()
        logE("@@@onResume@@@")
    }

    override fun extraIntentData() {
        logE("@@@extraIntentData@@@")
    }

    override fun showLoadingView() {
        logE("@@@showLoadingView@@@")
    }

    override fun showEmptyView() {
        logE("@@@showEmptyView@@@")
    }

    override fun showErrorView() {
        logE("@@@showErrorView@@@")
    }

    override fun onDestroyView() {
        logE("@@@onDestroyView@@@")
        (mRootView!!.parent as ViewGroup).removeView(mRootView)
        super.onDestroyView()
    }


}