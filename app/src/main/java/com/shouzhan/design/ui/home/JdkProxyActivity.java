package com.shouzhan.design.ui.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.fshows.android.stark.utils.FsLogUtil;
import com.shouzhan.design.R;
import com.shouzhan.design.base.BaseActivity;
import com.shouzhan.design.datasource.http.ApiService;
import com.shouzhan.design.utils.proxy.Huge;
import com.shouzhan.design.utils.proxy.Star;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * @author danbin
 * @version JdkProxyActivity.java, v 0.1 2019-09-19 09:06 danbin
 */
public class JdkProxyActivity extends BaseActivity {

    private static final String TAG = JdkProxyActivity.class.getSimpleName();

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_proxy;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eagerlyValidateMethods(ApiService.class);
        Star star = getTestProxy();
        star.sing("逍遥叹");
        ApiService apiService = createApiServiceProxy(ApiService.class);
        apiService.login(null);
    }

    @SuppressWarnings("unchecked")
    private <T> T createApiServiceProxy(final Class<T> service) {
        validateServiceInterface(service);
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                (proxy, method, args) -> {
                    FsLogUtil.error(TAG, "invoke method: " + method);
                    return null;
                });
    }

    <T> void validateServiceInterface(Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }

    private void eagerlyValidateMethods(Class<?> service) {
        for (Method method : service.getDeclaredMethods()) {
            Annotation[] methodAnnotations = method.getAnnotations();
            Type[] parameterTypes = method.getGenericParameterTypes();
            Annotation[][] parameterAnnotationsArray = method.getParameterAnnotations();

            for (Annotation annotation : methodAnnotations) {
                FsLogUtil.error(TAG, "Annotations: " + annotation.toString());
            }

            int parameterCount = parameterAnnotationsArray.length;

            for (int p = 0; p < parameterCount; p++) {
                Annotation[] parameterAnnotations = parameterAnnotationsArray[p];
                for (Annotation annotation : parameterAnnotations) {
                    FsLogUtil.error(TAG, "parameterAnnotations: " + annotation.toString());
                }
            }

            for (Type type : parameterTypes) {
                FsLogUtil.error(TAG, "parameterTypes: " + type.toString());
            }
        }
    }

    private Star getTestProxy() {
        Star hg = new Huge();
        return (Star) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                hg.getClass().getInterfaces(),
                (proxy, method, args) -> {

                    if (method.getName().equals("sing")) {
                        FsLogUtil.error(TAG, "我是胡歌代理1，找胡歌唱歌找我");
                        return method.invoke(hg, args);
                    }
                    if (method.getName().equals("act")) {
                        FsLogUtil.error(TAG, "我是胡歌代理1，找胡歌演电视剧找我");
                        return method.invoke(hg, args);
                    }
                    return null;
                });
    }

    @Override
    public void getData() {

    }

}
