package com.shouzhan.design.datasource.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.shouzhan.design.model.db.User;

/**
 * @author danbin
 * @version UserRoomDatabase.java, v 0.1 2019-07-18 23:07 danbin
 */
@Database(entities = {User.class}, version = 1)
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public static UserRoomDatabase instance;

    public static UserRoomDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (UserRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), UserRoomDatabase.class
                            , "user_database").build();
                }
            }
        }
        return instance;
    }
}