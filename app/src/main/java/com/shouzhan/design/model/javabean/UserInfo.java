package com.shouzhan.design.model.javabean;

import androidx.databinding.BaseObservable;

/**
 * @author danbin
 * @version User.java, v 0.1 2019-07-14 23:51 danbin
 */
public class UserInfo extends BaseObservable {

    private String name;
    private int age;

    public UserInfo() { }

    public UserInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
