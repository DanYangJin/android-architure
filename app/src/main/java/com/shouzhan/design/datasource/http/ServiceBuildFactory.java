package com.shouzhan.design.datasource.http;

import com.shouzhan.design.datasource.http.java.JavaNetProvider;
import com.shouzhan.design.datasource.http.loan.LoanNetProvider;
import com.shouzhan.design.datasource.http.performance.PerformanceNetProvider;

/**
 * @author lijie on 2018/12/30
 */
public class ServiceBuildFactory {

    public static final int PHP_HOST = 0;
    public static final int JAVA_HOST = 1;
    public static final int JAVA_LOAN_HOST = 2;
    public static final int JAVA_PERFORMANCE_HOST = 3;

    public static ServiceBuildFactory instance;

    public static ServiceBuildFactory getInstance() {
        if (instance == null) {
            synchronized (ServiceBuildFactory.class) {
                if (instance == null) {
                    instance = new ServiceBuildFactory();
                }
            }
        }
        return instance;
    }

    public <T> T create(Class<T> tClass, String baseUrl, int type) {
        switch (type) {
            case PHP_HOST:
                break;
            case JAVA_HOST:
                Object mJavaHttps = HttpUtils.getInstance().get(baseUrl, new JavaNetProvider(), tClass);
                return (T) mJavaHttps;
            case JAVA_LOAN_HOST:
                Object mLoanHttps = HttpUtils.getInstance().get(baseUrl, new LoanNetProvider(), tClass);
                return (T) mLoanHttps;
            case JAVA_PERFORMANCE_HOST:
                Object mPerformanceHttps = HttpUtils.getInstance().get(baseUrl, new PerformanceNetProvider(), tClass);
                return (T) mPerformanceHttps;
            default:
                break;

        }
        return null;
    }

}
