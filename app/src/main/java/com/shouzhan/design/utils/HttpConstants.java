package com.shouzhan.design.utils;

import com.fshows.android.stark.utils.Prefs;
import com.shouzhan.design.BuildConfig;

/**
 * @author danbin
 * @version HttpConstants.java, v 0.1 2019-03-25 下午1:52 danbin
 */
public class HttpConstants {

    /**
     * 正式服务器域名
     */
    public static final String[] HOSTS = {
            "https://merchantapp-admin-test.51fubei.com/",
            "https://merchantapp-admin-beta.51fubei.com/",
            "https://merchantapp-admin.51fubei.com/"};

    /**
     * 0.测试环境, 1.预发布环境, 2.正式环境
     */
    public static final String HOST = HOSTS[getEnvConfig()];

    /**
     * JAVA环境服务器域名
     */
    public static final String[] JAVA_HOSTS = {
            "http://lifecircle-overlord-test.51fubei.com:8086/",
            "https://lifecircle-overlord-beta.51fubei.com/",
            "https://lifecircle-overlord.51fubei.com/"};

    /**
     * JAVA -- 商户福利
     * 0.测试环境, 1.预发布环境, 2.正式环境
     */
    public static final String JAVA_HOST = JAVA_HOSTS[getEnvConfig()];

    /**
     * JAVA--商户贷
     * 环境服务器域名
     */
    public static final String[] JAVA_LOAN_HOSTS = {
            "http://lifecircle-loan-test.51youdian.com/",
            "http://lifecircle-loan-beta.51youdian.com/",
            "http://lifecircle-loan.51youdian.com/"};

    /**
     * JAVA -- 商户贷
     * 0.测试环境, 1.预发布环境, 2.正式环境
     */
    public static final String JAVA_LOAN_HOST = JAVA_LOAN_HOSTS[getEnvConfig()];

    /**
     * JAVA--绩效考核系统
     * 环境服务器域名
     */
    public static final String[] JAVA_PERFORMANCE_HOSTS = {
            "http://performance.wxy-zc-forever.com:9090/",
            "http://performance.wxy-zc-forever.com:9090/",
            "http://performance.wxy-zc-forever.com:9090/"};

    /**
     * JAVA -- 绩效考核系统
     * 0.测试环境, 1.预发布环境, 2.正式环境
     */
    public static final String JAVA_PERFORMANCE_HOST = JAVA_PERFORMANCE_HOSTS[getEnvConfig()];

    public static final String[] KEYS = {
            "0226dee8829c64a16c53a3029f8ddb69",
            "0226dee8829c64a16c53a3029f8ddb69",
            "8bcd9eff9ddfe72935f915c0ff6b036a"
    };

    public static final String KEY = KEYS[getEnvConfig()];

    /**
     * 环境切换
     */
    private static int getEnvConfig() {
        if (BuildConfig.DEBUG) {
            return Prefs.getIntPreference(PrefConstants.ENV_CONFIG, BuildConfig.SERVER);
        } else {
            return BuildConfig.SERVER;
        }
    }

    /**
     * 统一请求时间
     */
    public static final long CONNECT_TIME_OUT_MILLS = 10 * 1000L;
    public static final long READ_TIME_OUT_MILLS = 10 * 1000L;

    /**
     * 请求配置
     */
    public static final String APP_ID = "20181201010101";

    /**
     * 请求参数
     */
    public static final String RQ_APP_ID = "appid";
    public static final String RQ_METHOD = "method";
    public static final String RQ_VERSION = "version";
    public static final String RQ_CONTENT = "content";
    public static final String RQ_SIGN = "sign";

    public static final String HEADER_PERFORMANCE_ACCESS_TOKEN = "access_token";
    public static final String HEADER_ACCEPT_CODING = "Accept-Encoding";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_ACCESS_TOKEN = "access-token";

    public static final String STRING_CONTENT_TYPE = "application/json";
    public static final String ACCEPT_CODING = "gzip,deflate,sdch";

}
