package com.shouzhan.design.datasource.http.loan;

import com.shouzhan.design.datasource.http.NetProvider;
import com.shouzhan.design.datasource.http.RequestHandler;

import java.util.ArrayList;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * @author danbin
 * @version LoanNetProvider.java, v 0.1 2019-03-25 下午4:28 danbin
 */
public class LoanNetProvider implements NetProvider {

    @Override
    public RequestHandler headerInterceptors() {
        return new LoanRequestHandler();
    }

    @Override
    public ArrayList<Interceptor> configInterceptors() {
        return null;
    }

    @Override
    public void configHttps(OkHttpClient.Builder builder) {

    }

    @Override
    public CookieJar configCookie() {
        return null;
    }

    @Override
    public long configConnectTimeoutSecs() {
        return 0;
    }

    @Override
    public long configReadTimeoutSecs() {
        return 0;
    }

    @Override
    public long configWriteTimeoutSecs() {
        return 0;
    }

    @Override
    public boolean configLogEnable() {
        return true;
    }

}
