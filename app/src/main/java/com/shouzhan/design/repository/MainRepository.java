package com.shouzhan.design.repository;

import android.content.Context;
import android.os.AsyncTask;
import com.shouzhan.design.datasource.db.UserDao;
import com.shouzhan.design.datasource.db.UserRoomDatabase;
import com.shouzhan.design.model.db.User;

import java.util.List;

/**
 * @author danbin
 * @version MainRespository.java, v 0.1 2019-07-18 23:19 danbin
 */
public class MainRepository {

    private UserDao mUserDao;

    private List<User> allUser;

    public MainRepository(Context context) {
        new InitThread(context.getApplicationContext()).start();
    }

    public List<User> getAllUser() {
        return allUser;
    }

    public void deleteAll() {
        new DeleteAsyncTask(mUserDao).execute();
    }

    public void update(User user) {
        new UpdateAsyncTask(mUserDao).execute(user);
    }

    public void insert(User user) {
        new InsertAsyncTask(mUserDao).execute(user);
    }

    private class InitThread extends Thread {
        Context context;

        InitThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            UserRoomDatabase db = UserRoomDatabase.getInstance(context);
            mUserDao = db.userDao();
            allUser = mUserDao.getUserList();
        }
    }

    private class UpdateAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        UpdateAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.updateUsers(params[0]);
            return null;
        }
    }

    private class InsertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        InsertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {

        private UserDao mAsyncTaskDao;

        DeleteAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }


}
