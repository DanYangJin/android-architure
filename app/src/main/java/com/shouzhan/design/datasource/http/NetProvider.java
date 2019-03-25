package com.shouzhan.design.datasource.http;

import java.util.ArrayList;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * @author lijie on 2019/3/12
 * 配置网络参数
 */
interface NetProvider {

    /**
     * 公共请求头处理器
     */
    RequestHandler headerInterceptors();

    /**
     * 除了公共请求头之后的参数
     */
    ArrayList<Interceptor> configInterceptors();

    /**
     * 设置OkHttpClient
     */
    void configHttps(OkHttpClient.Builder builder);

    /**
     * 设置cookie
     */
    CookieJar configCookie();
    /**
     * 设置请求连接超时时间
     */
    long configConnectTimeoutSecs();

    /**
     * 设置请求读取超时时间
     */
    long configReadTimeoutSecs();

    /**
     * 设置请求写入超时时间
     */
    long configWriteTimeoutSecs();

    /**
     * 设置日志打印方式
     */
    boolean configLogEnable();

}