package com.shouzhan.design.datasource.http;

import com.google.gson.Gson;
import com.shouzhan.design.BuildConfig;
import com.shouzhan.design.datasource.http.performance.PerformanceNetProvider;
import com.shouzhan.design.utils.HttpConstants;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author danbin
 * @version HttpUtils.java, v 0.1 2019-03-25 下午2:55 danbin
 */
public class HttpUtils {

    private HashMap<String, NetProvider> mProviderMap = new HashMap<>();
    private HashMap<String, Retrofit> mRetrofitMap = new HashMap<>();
    private HashMap<String, OkHttpClient> mClientMap = new HashMap<>();

    public volatile static HttpUtils instance;

    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 根据不同请求URL, 得到不同的请求Retrofit
     */
    Retrofit getRetrofit(String baseUrl, NetProvider provider) throws Exception {
        if (StringUtils.isEmpty(baseUrl)) {
            throw new IllegalAccessException("baseUrl can not be null");
        }

        if (mRetrofitMap.get(baseUrl) != null) {
            return mRetrofitMap.get(baseUrl);
        }
        NetProvider netProvider = provider;
        if (provider == null) {
            netProvider = mProviderMap.get(baseUrl);
            if (netProvider == null) {
                netProvider = new PerformanceNetProvider();
            }
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient(baseUrl, netProvider))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        mRetrofitMap.put(baseUrl, retrofit);
        mProviderMap.put(baseUrl, provider);
        return retrofit;
    }

    private OkHttpClient getOkHttpClient(String baseUrl, NetProvider provider) {
        if (StringUtils.isEmpty(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
        if (mClientMap.get(baseUrl) != null) {
            return mClientMap.get(baseUrl);
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (provider.configConnectTimeoutSecs() != 0L) {
            builder.connectTimeout(provider.configConnectTimeoutSecs(), TimeUnit.SECONDS);
        } else {
            builder.callTimeout(HttpConstants.CONNECT_TIME_OUT_MILLS, TimeUnit.SECONDS);
        }
        if (provider.configReadTimeoutSecs() != 0L) {
            builder.readTimeout(provider.configReadTimeoutSecs(), TimeUnit.SECONDS);
        } else {
            builder.readTimeout(HttpConstants.READ_TIME_OUT_MILLS, TimeUnit.SECONDS);
        }
        if (provider.configWriteTimeoutSecs() != 0L) {
            builder.writeTimeout(provider.configWriteTimeoutSecs(), TimeUnit.SECONDS);
        } else {
            builder.writeTimeout(HttpConstants.READ_TIME_OUT_MILLS, TimeUnit.SECONDS);
        }
        RequestHandler handler = provider.headerInterceptors();
        if (handler != null) {
            builder.addInterceptor(new NetInterceptor(handler));
        }

        ArrayList<Interceptor> interceptors = provider.configInterceptors();
        if (!empty(interceptors)) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        if (provider.configLogEnable()) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }
            builder.addInterceptor(loggingInterceptor);
        }

        OkHttpClient client = builder.build();
        mClientMap.put(baseUrl, client);
        return client;
    }

    private boolean empty(ArrayList<Interceptor> interceptors) {
        return interceptors == null || interceptors.isEmpty();
    }

    /**
     * 获取请求句柄
     *
     * @param baseUrl
     * @param provider
     * @param service
     * @return
     */
    public <T> T get(String baseUrl, NetProvider provider, Class<T> service) {
        try {
            return getRetrofit(baseUrl, provider).create(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
