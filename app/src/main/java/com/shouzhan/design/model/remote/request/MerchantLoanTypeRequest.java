package com.shouzhan.design.model.remote.request;

import com.google.gson.annotations.SerializedName;

/**
 * @author danbin
 * @version MerchantLoanTypeRequest.java, v 0.1 2019-03-26 上午12:09 danbin
 */
public class MerchantLoanTypeRequest {

    @SerializedName("merchant_id")
    private String merchantId;

    public MerchantLoanTypeRequest(String merchantId) {
        this.merchantId = merchantId;
    }


}
