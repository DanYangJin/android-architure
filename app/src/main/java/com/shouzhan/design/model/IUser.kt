package com.shouzhan.design.model

/**
 * @author danbin
 * @version IUser.java, v 0.1 2019-02-26 下午4:17 danbin
 */
interface IUser {

    var prop: Int

    fun sum(): String

    fun sum1(): String? {
        return null
    }
}