package com.shouzhan.design.base

import com.google.gson.annotations.SerializedName

import java.util.List

/**
 * @author danbin
 */
class BasePageResult<T> {

    /**
     * 返回总数
     */
    @SerializedName("total")
    var total: Int = 0


    /**
     * 返回列表
     */
    @SerializedName("list")
    var list: List<T>? = null

}