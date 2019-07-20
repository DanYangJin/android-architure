package com.shouzhan.design.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

/**
 * @author danbin
 * @version User.java, v 0.1 2019-07-18 23:01 danbin
 */
@Entity(tableName = "t_user")
public class User {

    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "id_")
    private int id;

    private String name;
    private String password;
    private String school;

    public User(int id, String name, String password, String school) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.school = school;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}

