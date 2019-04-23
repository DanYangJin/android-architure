package com.shouzhan.design.base;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.shouzhan.design.datasource.http.HttpCompositeDisposable;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * @author danbin
 * @version RxViewModel.java, v 0.1 2019-02-21 下午5:12 danbin
 */
public class BaseViewModel extends ViewModel {

    private static final String TAG = BaseViewModel.class.getSimpleName();

    private HttpCompositeDisposable mBasePresenter = new HttpCompositeDisposable();

    @Override
    protected void onCleared() {
        mBasePresenter.onUnSubscribe();
        super.onCleared();
    }

    /**
     * @param observable
     * @param observer
     */
    public void addSubscribe(Observable observable, DisposableObserver observer) {
        mBasePresenter.addSubscribe(observable, observer);
    }

    /**
     * @param disposable
     */
    public void addDisposable(Disposable disposable) {
        mBasePresenter.addDisposable(disposable);
    }

}
