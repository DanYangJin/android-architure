package com.shouzhan.design.base;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.immersionbar.ImmersionBar;
import com.shouzhan.design.App;
import com.shouzhan.design.R;

/**
 * @author danbin
 * @version BaseLazyFragment.java, v 0.1 2019-02-26 下午10:49 danbin
 */
public abstract class BaseLazyFragment<VB extends ViewDataBinding> extends Fragment implements IBasePagePresenter, Presenter {

    private static final String TAG = BaseLazyFragment.class.getSimpleName();

    protected Context mContext;
    protected VB mBinding = null;

    /**
     * View 的初始化状态，只有初始化完毕才加载数据
     */
    private boolean isViewInitiated = false;

    /**
     * View 是否可见，只有可见时加载数据
     */
    private boolean isVisibleToUser = false;

    /**
     * 数据是否已经初始化，避免重复请求数据
     */
    private boolean isDataInitiated = false;

    /**
     * 友盟统计TAG
     *
     * @return String
     */
    protected String getUmengTag() {
        return "";
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        this.prepareFetchData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
            mBinding.setVariable(com.shouzhan.design.BR.presenter, this);
            mBinding.setLifecycleOwner(this);
            initImmersionBar();
        }
        ViewGroup parent = (ViewGroup) mBinding.getRoot().getParent();
        if (parent != null) {
            parent.removeView(mBinding.getRoot());
        }
        return mBinding.getRoot();
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary).init();
    }

    /**
     * 该方法会执行两次
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        if (!isViewInitiated) {
            initView();
            isViewInitiated = true;
        }
        prepareFetchData();
    }

    /**
     * 准备加载数据，只有在视图初始化完毕，Fragment 可见且数据未初始化的时候才去加载数据。
     */
    private void prepareFetchData() {
        if (isViewInitiated && isVisibleToUser && !isDataInitiated) {
            isDataInitiated = true;
            getData();
        }
    }

    @Override
    public void extraIntentData() { }

    /**
     * 获取ViewModel
     *
     * @param modelClass
     * @param <T>
     * @return
     */
    protected <T extends BaseViewModel> T vmProviders(@NonNull Class<T> modelClass) {
        T viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(App.getInstance()).create(modelClass);
        viewModel.getViewStatus().observe(this, viewStatus -> {
            assert viewStatus != null;
            switch (viewStatus) {
                case EMPTY:
                    showEmptyView();
                    break;
                case LOADING:
                    showLoadingView();
                    break;
                case ERROR:
                    showErrorView();
                    break;
                default:
                    break;
            }
        });
        return viewModel;
    }

    /**
     * 统一初始化Ui
     * */
    @Override
    public void initView() { }

    /**
     * 统一注册VM监听
     * */
    public void initObserver() { }

    /**
     * 获取到正确的数据，填充数据
     */
    protected void fillData() {
        hideLoadingView();
    }

    @Override
    public void getData() { }

    @Override
    public void showEmptyView() {
        hideLoadingView();
    }

    @Override
    public void showErrorView() {
        hideLoadingView();
    }

    @Override
    public void showLoadingView() { }

    @Override
    public void hideLoadingView() { }

    @Override
    public void onClick(View view) { }

}