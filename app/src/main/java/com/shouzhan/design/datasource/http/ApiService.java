package com.shouzhan.design.datasource.http;

import com.shouzhan.design.model.javabean.MerchantLoanTypeInfo;
import com.shouzhan.design.model.remote.request.LoginRequest;
import com.shouzhan.design.model.remote.request.MerchantLoanTypeRequest;
import com.shouzhan.design.model.remote.result.BasePageResult;
import com.shouzhan.design.model.remote.result.BaseResult;
import com.shouzhan.design.model.remote.result.JavaBaseResult;
import com.shouzhan.design.model.remote.result.LoginResult;
import com.shouzhan.design.model.remote.result.UserListResult;
import com.shouzhan.design.utils.HttpConstants;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author danbin
 * @version ApiService.java, v 0.1 2019-01-23 上午5:36 danbin
 */
public interface ApiService {

    class Builder {
        public static ApiService getPhpServer() {
            return ServiceBuildFactory.getInstance().create(ApiService.class, HttpConstants.HOST, ServiceBuildFactory.PHP_HOST);
        }

        public static ApiService getJavaServer() {
            return ServiceBuildFactory.getInstance().create(ApiService.class, HttpConstants.JAVA_HOST, ServiceBuildFactory.JAVA_HOST);
        }

        public static ApiService getJavaLoanServer() {
            return ServiceBuildFactory.getInstance().create(ApiService.class, HttpConstants.JAVA_LOAN_HOST, ServiceBuildFactory.JAVA_LOAN_HOST);
        }

        public static ApiService getJavaPerformanceServer() {
            return ServiceBuildFactory.getInstance().create(ApiService.class, HttpConstants.JAVA_PERFORMANCE_HOST, ServiceBuildFactory.JAVA_PERFORMANCE_HOST);
        }
    }

    /**
     * 登录接口
     *
     * @param request
     * @return
     */
    @POST("user/login")
    Observable<BaseResult<LoginResult>> login(@Body LoginRequest request);

    /**
     * 获取用户信息接口
     *
     * @param page
     * @param pageSize
     * @param searchName
     * @return
     */
    @GET("user/getUserList")
    Observable<BaseResult<BasePageResult<UserListResult>>> getUserList(@Query("page") int page,
                                                                       @Query("pageSize") int pageSize,
                                                                       @Query("searchName") String searchName);

    /**
     * 判断商户贷类型
     *
     * @param request
     * @return
     */
    @POST("com.fshows.lifecircle.loan.query.confirm.status")
    Observable<JavaBaseResult<MerchantLoanTypeInfo>> getMerchantLoansType(@Body MerchantLoanTypeRequest request);

}
