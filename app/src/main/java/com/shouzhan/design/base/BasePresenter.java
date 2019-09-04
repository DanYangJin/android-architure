package com.shouzhan.design.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.annimon.stream.Stream;
import com.fshows.android.stark.utils.FsLogUtil;
import com.shouzhan.design.App;
import com.shouzhan.design.datasource.http.CommonCompositeDisposable;

/**
 * @author danbin
 * @version BasePresenter.java, v 0.1 2019-09-04 09:58 danbin
 */
public abstract class BasePresenter<C, V extends IBaseView> implements IBasePresenter, LifecycleObserver {
    private static final String TAG = BasePresenter.class.getSimpleName();

    protected CommonCompositeDisposable mDisposable = new CommonCompositeDisposable();
    protected C mContext;
    protected V mView;
    private Lifecycle mLifecycle;


    public BasePresenter(C context, V view) {
        this.mContext = context;
        this.mView = view;
        this.initLifecycle();
    }

    private void initLifecycle() {
        if (mContext instanceof FragmentActivity) {
            this.mLifecycle = ((FragmentActivity) mContext).getLifecycle();
        }
        if (mContext instanceof Fragment) {
            this.mLifecycle = ((Fragment) mContext).getLifecycle();
        }
        if (this.mLifecycle != null) {
            mLifecycle.addObserver(this);
        }
    }


    /**
     * 生成viewModel
     */
    protected <T extends BaseViewModel> T vmProviders(Class<T> modelClass) {
        T viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(App.getInstance()).create(modelClass);
        initObserve(viewModel);
        return viewModel;

    }

    @Override
    public boolean canUpdateUi() {
        return Stream.of(Lifecycle.State.STARTED, Lifecycle.State.RESUMED)
                .anyMatch(p -> p == mLifecycle.getCurrentState());
    }

    /**
     * 统一显示加载框
     */
    private void initObserve(BaseViewModel viewModel) {
        if (mContext instanceof FragmentActivity || mContext instanceof Fragment) {
            viewModel.mPageStatus.observe((LifecycleOwner) mContext, status -> {
                switch (status) {
                    case LOADING:
                        mView.showLoading();
                        break;
                    default:
                        break;
                }
            });
        }
    }


    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        FsLogUtil.debug(TAG, "onCreate: {}", getClass().getCanonicalName());
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        FsLogUtil.debug(TAG, "onStart: {}", getClass().getCanonicalName());
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        FsLogUtil.debug(TAG, "onResume: {}", getClass().getCanonicalName());
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        FsLogUtil.debug(TAG, "onPause: {}", getClass().getCanonicalName());
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        FsLogUtil.debug(TAG, "onStop: {}", getClass().getCanonicalName());
    }

    @Override
    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        FsLogUtil.debug(TAG, "onDestroy: {}", getClass().getCanonicalName());
        mDisposable.onUnSubscribe();
        mContext = null;
        mView = null;
    }

}
