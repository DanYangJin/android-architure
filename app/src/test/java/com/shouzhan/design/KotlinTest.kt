package com.shouzhan.design

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test

/**
 * @author danbin
 * @version KotlinTest.java, v 0.1 2019-06-16 01:11 danbin
 */
class KotlinTest {

    @Test
    fun kotlin_test() {
        GlobalScope.launch {
            delay(1000L)
            println("World!!!")
        }
        println("Hello, ")
        Thread.sleep(2000L)
    }

}