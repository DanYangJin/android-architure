package com.shouzhan.design.datasource.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.shouzhan.design.model.db.User;

import java.util.List;

/**
 * @author danbin
 * @version UserDao.java, v 0.1 2019-07-18 23:03 danbin
 */
@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("select * from t_user")
    List<User> getUserList();

    @Query("delete from t_user")
    void deleteAll();

    @Update
    void updateUsers(User... users);


}
