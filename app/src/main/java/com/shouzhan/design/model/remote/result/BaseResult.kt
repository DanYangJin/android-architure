package com.shouzhan.design.model.remote.result;

import com.google.gson.annotations.SerializedName;

/**
 * @author danbin
 */
class BaseResult<T> {

    /**
     * 返回状态码
     */
    @SerializedName("resultCode")
    var resultCode: Int = 0

    /**
     * 返回结果信息
     */
    @SerializedName("resultMessage")
    var resultMessage: String? = null

    /**
     * 返回数据体
     */
    @SerializedName("data")
    var data: T? = null

}