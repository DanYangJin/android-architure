package com.shouzhan.design.datasource.http.loan;

import com.shouzhan.design.base.BaseJavaResult;

import io.reactivex.observers.DisposableObserver;

/**
 * @author lijie on 2018/12/3
 */
public abstract class LoanApiCallback<T> extends DisposableObserver<BaseJavaResult<T>> {

    @Override
    public void onNext(BaseJavaResult<T> javaBaseResponse) {
        if (javaBaseResponse.success) {
            onSuccess(javaBaseResponse.data);
        } else {
            onFailure(javaBaseResponse.errorMsg, javaBaseResponse.errorCode);
        }
        onFinish();
    }

    /**
     * 请求失败模型
     *
     * @param errorMsg
     * @param errorCode
     */
    protected void onFailure(String errorMsg, String errorCode){

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
        onFinish();
    }
}
