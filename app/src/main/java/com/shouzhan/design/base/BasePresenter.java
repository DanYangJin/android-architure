package com.shouzhan.design.base;

import android.view.View;

import androidx.annotation.CallSuper;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.annimon.stream.Stream;
import com.fshows.android.stark.utils.FsLogUtil;

/**
 * @author danbin
 * @version BasePresenter.java, v 0.1 2019-09-04 09:58 danbin
 */
public abstract class BasePresenter<C, V extends IBaseView, VB extends ViewDataBinding, VM extends BaseViewModel> implements IBasePresenter, LifecycleObserver, Presenter {
    private static final String TAG = BasePresenter.class.getSimpleName();

    protected Lifecycle mLifecycle;
    protected C mContext;
    protected V mView;
    protected VB mBinding;
    protected VM mViewModel;

    public BasePresenter(C context, V view, VB binding, VM viewModel) {
        this.mContext = context;
        this.mView = view;
        this.mBinding = binding;
        this.mBinding.setVariable(com.shouzhan.design.BR.presenter, this);
        this.mViewModel = viewModel;
        this.initLifecycle();
        this.initObserver();
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

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean canUpdateUi() {
        return Stream.of(Lifecycle.State.STARTED, Lifecycle.State.RESUMED)
                .anyMatch(p -> p == mLifecycle.getCurrentState());
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
        mContext = null;
        mView = null;
    }
}
