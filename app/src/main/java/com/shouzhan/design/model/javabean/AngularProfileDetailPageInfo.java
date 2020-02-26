package com.shouzhan.design.model.javabean;

/**
 * @author danbin
 * @version `AngularProfileDetailPageInfo.java`.java, v 0.1 2020-01-08 10:08 danbin
 */
public class AngularProfileDetailPageInfo {

    /**
     * 是否需要修改个人资料
     */
    private String isSet;

//        /**
//         * oem电话
//         * */
//        val oemPhone: String?


    public AngularProfileDetailPageInfo(String isSet) {
        this.isSet = isSet;
    }

    public String getIsSet() {
        return isSet;
    }

    public void setIsSet(String isSet) {
        this.isSet = isSet;
    }

}

