package com.shouzhan.design.datasource.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author danbin
 * @version NetInterceptor.java, v 0.1 2019-03-25 下午4:51 danbin
 */
public class NetInterceptor implements Interceptor {

    private RequestHandler handler;

    public NetInterceptor(RequestHandler handler) {
        this.handler = handler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (handler != null) {
            //处理请求头
            request = handler.onBeforeRequest(request, chain);
        }

        Response response = chain.proceed(request);
        if (handler != null) {
            //处理结果
            return handler.onAfterRequest(response, chain);
        }
        return response;
    }

}
