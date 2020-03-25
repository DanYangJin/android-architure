package com.shouzhan.design.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.fshows.android.stark.utils.FsLogUtil;
import com.shouzhan.design.R;
import com.shouzhan.design.base.BaseActivity;
import com.shouzhan.design.datasource.http.ApiService;
import com.shouzhan.design.service.CustomService;
import com.shouzhan.design.ui.home.model.MonitorHandler;
import com.shouzhan.design.ui.home.model.TestService;
import com.shouzhan.design.ui.home.model.TestServiceImpl;
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
        switch (view.getId()) {
            case R.id.proxy_btn:
                dealProxy();
                break;
            case R.id.permutations_btn:
                dealPermutations();
                break;
            case R.id.service_btn:
                dealService();
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_proxy;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.proxy_btn).setOnClickListener(this);
        findViewById(R.id.permutations_btn).setOnClickListener(this);
        findViewById(R.id.service_btn).setOnClickListener(this);
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

    private void dealProxy() {
        TestServiceImpl service = new TestServiceImpl();
        // JDK 动态代理
        TestService iService = (TestService)Proxy.newProxyInstance(service.getClass().getClassLoader(),
                service.getClass().getInterfaces(),
                new MonitorHandler(service));
        iService.test1();

        // Cglib 代理方法

        Intent intent = new Intent();

    }

    private void dealPermutations() {
        // 算法
//        int[] a = new int[] {1, 2, 3};
//        arrange(a, 0, 2);
//        int[] a = new int[] {10, -2, 5, 8, -4, 2, -3, 7, 12, -88, -23, 35};
//        setParted1(a, 0, a.length - 1);
        int ac = 'a' + 'c';
        Log.e("xss", "dealPermutations: " + ac);
    }


    public void arrange(int[] a, int start, int end) {
        if (start == end) {
            for (int i : a) {
                System.out.print(i);
            }
            System.out.println();
            return;
        }
        for (int i = start; i <= end; i++) {
            swap(a, i, start);
            arrange(a, start + 1, end);
            swap(a, i, start);
        }
    }

    public void swap(int[] arr, int i, int j) {
        int te = arr[i];
        arr[i] = arr[j];
        arr[j] = te;
    }

    public void dealService() {
        Intent intent  = new Intent(new Intent(mContext, CustomService.class));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        } else {
            startService(intent);
//        }
    }

    public void setParted1(int[] a, int left, int right) {
        if (left >= right || left == a.length || right == 0) {
            for (int i = 0; i < a.length; i++) {
                System.out.println(a[i]);
            }
            return;
        }
        while (a[left] < 0) {
            left++;
        }
        while (a[right] >= 0) {
            right--;
        }
        if (left >= right || left == a.length || right == 0) {
            for (int i = 0; i < a.length; i++) {
                System.out.println(a[i]);
            }
            return;
        }
        swap(a, left, right);
        left++;
        right--;
        setParted1(a, left, right);
    }

}
