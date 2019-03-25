package com.shouzhan.design.repository;

import com.shouzhan.design.datasource.http.ApiService;
import com.shouzhan.design.model.javabean.MerchantLoanTypeInfo;
import com.shouzhan.design.model.remote.request.MerchantLoanTypeRequest;
import com.shouzhan.design.model.remote.result.JavaBaseResult;

import io.reactivex.Observable;

/**
 * @author danbin
 * @version MerchantLoanRepository.java, v 0.1 2019-02-21 下午5:59 danbin
 */
public class MerchantLoanRepository {

    public ApiService mApiService;

    public MerchantLoanRepository() {
        this.mApiService = ApiService.Builder.getJavaLoanServer();
    }

    public Observable<JavaBaseResult<MerchantLoanTypeInfo>> getMerchantLoansType(String uid) {
        MerchantLoanTypeRequest request = new MerchantLoanTypeRequest(uid);
        return mApiService.getMerchantLoansType(request);
    }


}
