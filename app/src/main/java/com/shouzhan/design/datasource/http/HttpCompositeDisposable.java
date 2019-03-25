package com.shouzhan.design.datasource.http;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lijie on 2018/12/3
 */
public class HttpCompositeDisposable {

    private CompositeDisposable mCompositeDisposable;

    public HttpCompositeDisposable() {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
    }

    /**
     * RxJava取消注册，以避免内存泄露
     */
    public void onUnSubscribe() {
        mCompositeDisposable.dispose();
    }

    /**
     * @param observable
     * @param observer
     */
    public void addSubscribe(Observable observable, DisposableObserver observer) {
        mCompositeDisposable.add(observer);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }

    /**
     * 添加订阅
     */
    public void addDisposable(Disposable mDisposable) {
        mCompositeDisposable.add(mDisposable);
    }

}
