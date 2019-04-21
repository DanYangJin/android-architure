package com.shouzhan.design.datasource.http.performance;

import android.util.Log;

import com.shouzhan.design.base.BaseResult;

import io.reactivex.observers.DisposableObserver;

/**
 * @author lijie on 2018/12/3
 */
public abstract class PerformanceApiCallback<T> extends DisposableObserver<BaseResult<T>> {

    private static final String TAG = PerformanceApiCallback.class.getSimpleName();
    private static final int SUCCESS_CODE = 200;

    @Override
    protected void onStart() {

    }

    @Override
    public void onNext(BaseResult<T> baseResult) {
        if (baseResult.getResultCode() == SUCCESS_CODE) {
            onSuccess(baseResult.getData());
        } else {
            onFailure(baseResult.getResultMessage(), baseResult.getResultCode());
        }
        onFinish();
    }

    /**
     * 请求失败模型
     *
     * @param resultMsg
     * @param resultCode
     */
    public void onFailure(String resultMsg, int resultCode) {
        Log.e(TAG, "onFailure: resultMsg= " + resultMsg + " resultCode= " + resultCode);
    }

    /**
     * 请求成功模型
     *
     * @param data
     */
    protected abstract void onSuccess(T data);

    /**
     * 统一处理网络请求结束，如取消加载弹框
     */
    protected void onFinish() {

    }

    @Override
    public void onError(Throwable e) {
        onFinish();
    }

    @Override
    public void onComplete() {

    }
}
