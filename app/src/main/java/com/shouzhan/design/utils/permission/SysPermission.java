package com.shouzhan.design.utils.permission;

import android.net.Uri;
import android.os.Bundle;

/**
 * @author danbin
 * @version SysPermission.java, v 0.1 2020-05-12 10:55 AM danbin
 * 系统权限
 */
public class SysPermission {

    public String title;

    public String subTitle;

    public String status;

    public String packageName;

    public String className;

    public String action;

    public String category;

    public Bundle params;

    public Uri data;

    public SysPermission setTitle(String title) {
        this.title = title;
        return this;
    }

    public SysPermission setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public SysPermission setStatus(String status) {
        this.status = status;
        return this;
    }

    public SysPermission setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public SysPermission setClassName(String className) {
        this.className = className;
        return this;
    }

    public SysPermission setAction(String action) {
        this.action = action;
        return this;
    }

    public SysPermission setCategory(String category) {
        this.category = category;
        return this;
    }

    public SysPermission setParams(Bundle params) {
        this.params = params;
        return this;
    }

    public SysPermission setData(Uri data) {
        this.data = data;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getStatus() {
        return status;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public String getAction() {
        return action;
    }

    public String getCategory() {
        return category;
    }

    public Bundle getParams() {
        return params;
    }

    public Uri getData() {
        return data;
    }
}
