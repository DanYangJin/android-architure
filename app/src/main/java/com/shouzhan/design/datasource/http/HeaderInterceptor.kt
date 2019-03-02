package com.shouzhan.design.datasource.http

import com.shouzhan.design.extens.logE
import com.shouzhan.design.utils.Preference
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @author danbin
 * @version HeaderInterceptor.java, v 0.1 2019-03-03 上午2:54 danbin
 */
class HeaderInterceptor : Interceptor {

    private var accessToken: String? by Preference("accessToken", "")

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
        logE("HeaderInterceptor accessToken: $accessToken")
        builder.header("accessToken", accessToken)
        val request = builder.method(original.method(), original.body())
                .build()
        return chain.proceed(request)
    }

}
