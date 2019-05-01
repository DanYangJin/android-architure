package com.shouzhan.design.base;

import android.arch.lifecycle.ViewModel;

import com.shouzhan.design.datasource.http.HttpCompositeDisposable;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * @author danbin
 * @version BaseViewModel.java, v 0.1 2019-02-21 下午5:12 danbin
 */
public class BaseViewModel extends ViewModel {

    private static final String TAG = BaseViewModel.class.getSimpleName();

    private HttpCompositeDisposable mHttpDisposable = new HttpCompositeDisposable();

    @Override
    protected void onCleared() {
        mHttpDisposable.onUnSubscribe();
        super.onCleared();
    }

    /**
     * @param observable
     * @param observer
     */
    public void addSubscribe(Observable observable, DisposableObserver observer) {
        mHttpDisposable.addSubscribe(observable, observer);
    }

    /**
     * @param disposable
     */
    public void addDisposable(Disposable disposable) {
        mHttpDisposable.addDisposable(disposable);
    }

    public HttpCompositeDisposable getHttpDisposable() {
        return mHttpDisposable;
    }

}
