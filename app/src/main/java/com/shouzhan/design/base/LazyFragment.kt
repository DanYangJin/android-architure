package com.shouzhan.design.base

import android.arch.lifecycle.Observer
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
import kotlinx.android.synthetic.main.layout_empty_view.*
import kotlinx.android.synthetic.main.layout_error_view.*


/**
 * @author danbin
 * @version LazyFragment.java, v 0.1 2019-02-26 下午10:49 danbin
 */
abstract class LazyFragment : Fragment(), BaseControllerPresenter {

    protected lateinit var mContext: Context
    private var mRootView: View? = null

    /**
     * View 的初始化状态，只有初始化完毕才加载数据
     */
    private var isViewInitiated: Boolean = false

    /**
     * View 是否可见，只有可见时加载数据
     */
    private var isVisibleToUser: Boolean = false

    /**
     * 数据是否已经初始化，避免重复请求数据
     */
    private var isDataInitiated: Boolean = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        logE("@@@setUserVisibleHint@@@===>${javaClass.simpleName} = $isVisibleToUser")
        this.isVisibleToUser = isVisibleToUser
        this.prepareFetchData()
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
        isViewInitiated.no {
            initView()
            isViewInitiated = true
        }
        prepareFetchData()
    }

    /**
     * 准备加载数据，只有在视图初始化完毕，Fragment 可见且数据未初始化的时候才去加载数据。
     * */
    private fun prepareFetchData() {
        logE("prepareFetchData: " +
                "isViewInitiated = $isViewInitiated, " +
                "isVisibleToUser = $isVisibleToUser, " +
                "isDataInitiated = $isDataInitiated")
        if (isViewInitiated && isVisibleToUser && !isDataInitiated) {
            isDataInitiated = true
            getData()
        }
    }

    /**
     * 获取ViewModel
     * @param modelClass
     * @param <T>
     * @return
     */
    protected fun <T : BaseViewModel> vmProviders(@NonNull modelClass: Class<T>): T {
        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(App.getInstance()).create(modelClass)
        viewModel.observerPageStatus().observe(this, Observer {
            it?.let { pageStatus ->
                when (pageStatus) {
                    ControllerStatus.EMPTY -> showEmptyView()
                    ControllerStatus.ERROR -> showEmptyView()
                }
            }
        })
        return viewModel
    }

    override fun extraIntentData() {

    }

    override fun showLoadingView() {

    }

    override fun showEmptyView() {
        empty_rl?.let {
            it.visibility = View.VISIBLE
        }
        error_rl?.let {
            it.visibility = View.GONE
        }
    }

    override fun showErrorView() {
        empty_rl?.let {
            it.visibility = View.GONE
        }
        error_rl?.let {
            it.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        logE("@@@onDestroyView@@@")
        mRootView?.let {
            (it.parent as ViewGroup).removeView(mRootView)
        }
        super.onDestroyView()
    }

}