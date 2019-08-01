package com.shouzhan.design.base;

import android.arch.lifecycle.MutableLiveData;
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

    public MutableLiveData<ControllerStatus> pageStatus = new MutableLiveData<>();

    public MutableLiveData<ControllerStatus> observerPageStatus() {
        return pageStatus;
    }

    private HttpCompositeDisposable mHttpDisposable = new HttpCompositeDisposable();

    @Override
    protected void onCleared() {
        mHttpDisposable.onUnSubscribe();
        super.onCleared();
    }

    public void addSubscribe(Observable observable, DisposableObserver observer) {
        mHttpDisposable.addSubscribe(observable, observer);
    }

    public void addDisposable(Disposable disposable) {
        mHttpDisposable.addDisposable(disposable);
    }

    public HttpCompositeDisposable getHttpDisposable() {
        return mHttpDisposable;
    }

}
