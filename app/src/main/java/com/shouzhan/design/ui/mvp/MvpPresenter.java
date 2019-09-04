package com.shouzhan.design.ui.mvp;

import android.content.Context;
import android.view.View;

import com.jakewharton.rxbinding3.view.RxView;
import com.shouzhan.design.R;
import com.shouzhan.design.base.BasePresenter;
import com.shouzhan.design.utils.SoundPoolUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author danbin
 * @version MvpPresenter.java, v 0.1 2019-07-17 00:40 danbin
 */
public class MvpPresenter extends BasePresenter<Context, MvpContract.View> implements MvpContract.Presenter {

    private SoundPoolUtil mSoundPool;
    private View mRootView;

    public MvpPresenter(Context context, View rootView, MvpContract.View view) {
        super(context, view);
        this.mRootView = rootView;
        this.mSoundPool = new SoundPoolUtil(context);
        this.init();
    }

    @Override
    public void switchTitle() {
        mView.updateTitleBar("飞飞飞");
        mSoundPool.play(1);
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void init() {
        mDisposable.addDisposable(
                RxView.clicks(mRootView.findViewById(R.id.switch_btn))
                        .throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(o -> switchTitle()));
    }

    @Override
    public boolean canUpdateUi() {
        return false;
    }

}
