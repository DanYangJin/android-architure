package com.shouzhan.design.base;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author lijie on 2018/12/3
 */
public class BaseJavaResult<T> {

    /**
     * 返回错误码
     */
    @SerializedName("error_code")
    public String errorCode;

    /**
     * 请求是否处理成功,true-成功，false-失败
     */
    @SerializedName("success")
    public Boolean success;

    /**
     * 错误信息。success=true是没有返回错误信息的
     */
    @SerializedName("error_msg")
    public String errorMsg;

    /**
     * 返回数据体,json字符串。success=false是没有返回数据体的
     */
    @SerializedName("data")
    public T data;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
