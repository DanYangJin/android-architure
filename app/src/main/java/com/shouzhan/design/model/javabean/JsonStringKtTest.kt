package com.shouzhan.design.model.javabean;

/**
 * @author danbin
 * @version JsonStringKtTest.kt, v 0.1 2020-01-08 10:08 danbin
 */
class JsonStringKtTest {

    /**
     * 是否需要修改个人资料
     */
    var set: String? = ""

    /**
     * oem电话
     * */
    var oemPhone: String? = ""

    constructor(set: String, oemPhone: String) {
        this.set = set
        this.oemPhone = oemPhone
    }

}

