package com.shouzhan.design.model

/**
 * @author danbin
 * @version User.java, v 0.1 2019-02-26 上午11:53 danbin
 */
open class User : IUser {

    override var prop: Int = 29

    lateinit var name: String

    override fun sum(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    override fun toString(): String {
//        return super.toString()
//    }
}