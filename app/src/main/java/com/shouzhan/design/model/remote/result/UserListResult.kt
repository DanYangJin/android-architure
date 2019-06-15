package com.shouzhan.design.model.remote.result

import com.google.gson.annotations.SerializedName
import com.shouzhan.design.base.BaseRecyclerViewType

/**
 * @author danbin
 * @version UserListResult.java, v 0.1 2019-03-03 上午1:36 danbin
 */
class UserListResult : BaseRecyclerViewType {

    @SerializedName("headImg")
    var headImg: String? = null
    @SerializedName("username")
    var username: String? = null
    @SerializedName("gender")
    var gender: Int = 0

    override fun getViewType(): Int {
        return gender
    }

}