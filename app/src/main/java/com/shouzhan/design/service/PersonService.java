package com.shouzhan.design.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.aidl.IPerson;

/**
 * @author danbin
 * @version PersonService.java, v 0.1 2020-03-12 4:46 PM danbin
 */
public class PersonService extends Service {

    private Binder mBinder = new IPerson.Stub() {
        @Override
        public String getName() {
            return "PersonService";
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}
