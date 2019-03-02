package com.shouzhan.design.datasource.http;


import com.shouzhan.design.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author danbin
 * @version RetrofitUtils.java, v 0.1 2019-01-23 上午5:36 danbin
 */
public class RetrofitUtils {

    private static Retrofit baseRetrofit;
    private static ApiService fubeiService;

    private static Retrofit getBaseRetrofit() {
        if (baseRetrofit == null) {
            freshNetClient();
        }
        return baseRetrofit;
    }

    private static void freshNetClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY :
                HttpLoggingInterceptor.Level.NONE);
        OkHttpClient mHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15 * 1000L, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000L, TimeUnit.MILLISECONDS)
                .writeTimeout(20 * 1000L, TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new HeaderInterceptor())
                .build();
        baseRetrofit = new Retrofit.Builder()
                .baseUrl("http://performance.wxy-zc-forever.com:9090")
                .client(mHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static void freshNetService() {
        fubeiService = getBaseRetrofit().create(ApiService.class);
    }

    public static ApiService getService() {
        if (fubeiService == null) {
            freshNetService();
        }
        return fubeiService;
    }

}