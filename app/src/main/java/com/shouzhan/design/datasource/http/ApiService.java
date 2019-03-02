package com.shouzhan.design.datasource.http;

import com.shouzhan.design.model.remote.request.LoginRequest;
import com.shouzhan.design.model.remote.result.BasePageResult;
import com.shouzhan.design.model.remote.result.BaseResult;
import com.shouzhan.design.model.remote.result.LoginResult;
import com.shouzhan.design.model.remote.result.UserListResult;

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


    @POST("/user/login")
    Observable<BaseResult<LoginResult>> login(@Body LoginRequest request);

    @GET("/user/getUserList")
    Observable<BaseResult<BasePageResult<UserListResult>>> getUserList(@Query("page") int page,
                                                                       @Query("pageSize") int pageSize,
                                                                       @Query("searchName") String searchName);

}
