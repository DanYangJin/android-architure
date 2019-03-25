package com.shouzhan.design.datasource.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author danbin
 * @version RequestHandler.java, v 0.1 2019-03-25 下午2:59 danbin
 * 网络拦截处理
 */
public interface RequestHandler {

    /**
     * 处理请求参数
     *
     * @param request
     * @param chain
     * @return
     * @throws IOException
     */
    Request onBeforeRequest(Request request, Interceptor.Chain chain) throws IOException;

    /**
     * 处理相应参数
     *
     * @param response
     * @param chain
     * @return
     * @throws IOException
     */
    Response onAfterRequest(Response response, Interceptor.Chain chain) throws IOException;

    /**
     * 处理验签方法
     *
     * @return
     */
    String generationSign();


}
