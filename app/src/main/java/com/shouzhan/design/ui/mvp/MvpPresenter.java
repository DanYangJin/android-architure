package com.shouzhan.design.ui.mvp;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;

import com.jakewharton.rxbinding3.view.RxView;
import com.shouzhan.design.R;
import com.shouzhan.design.base.BasePresenter;
import com.shouzhan.design.utils.SoundPoolUtil;

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
        mSoundPool.play(1);
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void init() {
        mDisposable.addDisposable(
                RxView.clicks(mRootView.findViewById(R.id.switch_btn))
                        .subscribe(o -> switchTitle()));
    }

    @Override
    public boolean canUpdateUi() {
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_NUMPAD_0:
                mSoundPool.play(0);
                break;
            case KeyEvent.KEYCODE_NUMPAD_1:
                mSoundPool.play(1);
                break;
            case KeyEvent.KEYCODE_NUMPAD_2:
                mSoundPool.play(2);
                break;
            case KeyEvent.KEYCODE_NUMPAD_3:
                mSoundPool.play(3);
                break;
            case KeyEvent.KEYCODE_NUMPAD_4:
                mSoundPool.play(4);
                break;
            case KeyEvent.KEYCODE_NUMPAD_5:
                mSoundPool.play(5);
                break;
            case KeyEvent.KEYCODE_NUMPAD_6:
                mSoundPool.play(6);
                break;
            case KeyEvent.KEYCODE_NUMPAD_7:
                mSoundPool.play(7);
                break;
            case KeyEvent.KEYCODE_NUMPAD_8:
                mSoundPool.play(8);
                break;
            case KeyEvent.KEYCODE_NUMPAD_9:
                mSoundPool.play(9);
                break;
            default:
                break;
        }
        return false;
    }

}
