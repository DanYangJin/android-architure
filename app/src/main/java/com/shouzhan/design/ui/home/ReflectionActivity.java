package com.shouzhan.design.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.fshows.android.stark.utils.FsLogUtil;
import com.shouzhan.design.R;
import com.shouzhan.design.base.BaseActivity;
import com.shouzhan.design.databinding.ActivityReflectionBinding;
import com.shouzhan.design.datasource.http.ApiService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * @author danbin
 * @version ReflectionActivity.java, v 0.1 2019-02-27 上午12:11 danbin
 */
public class ReflectionActivity extends BaseActivity<ActivityReflectionBinding> {

    private static final String TAG = ReflectionActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reflectApiService(ApiService.class);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reflection;
    }

    @Override
    public void getData() {
    }

    private void reflectApiService(final Class<ApiService> service) {
        Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
                new InvocationHandler() {

                    @Override public Object invoke(Object proxy, Method method, @javax.annotation.Nullable Object[] args)
                            throws Throwable {
                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        }
                        FsLogUtil.error(TAG, "reflectApiService: ");
                        return null;
                    }
                });
    }
}
