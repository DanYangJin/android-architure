package com.shouzhan.design.ui.mvp;

import android.content.Context;
import android.view.KeyEvent;

import com.shouzhan.design.base.BasePresenter;
import com.shouzhan.design.databinding.ActivityMvpBinding;
import com.shouzhan.design.utils.SoundPoolUtil;

/**
 * @author danbin
 * @version MvpPresenter.java, v 0.1 2019-07-17 00:40 danbin
 */
public class MvpPresenter extends BasePresenter<Context, MvpContract.View, ActivityMvpBinding, MvpViewModel> implements MvpContract.Presenter {

    private SoundPoolUtil mSoundPool;

    public MvpPresenter(Context context, ActivityMvpBinding binding, MvpContract.View view, MvpViewModel viewModel) {
        super(context, view, binding, viewModel);
        this.mSoundPool = new SoundPoolUtil(context);
        this.initView();
    }

    @Override
    public void switchTitle() {
        mView.updateTitleBar("更新标题啦");
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initView() {
//        mDisposable.addDisposable(
//                RxView.clicks(mBinding.switchBtn)
//                        .subscribe(o -> switchTitle()));
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
