package com.shouzhan.design.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @author danbin
 * @version TestService.java, v 0.1 2020-03-17 10:33 AM danbin
 */
public class CustomService extends IntentService {


    public CustomService() {
        super("CustomService");
    }

    public CustomService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createStartForeground();
    }

    /**
     * 兼容Android8.0+
     */
    private void createStartForeground() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("InnerService_001", "InnerService",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(getApplicationContext(), "InnerService_001").build();
            startForeground(-1001, notification);
        } else {
            startForeground(-1001, new Notification());
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("xss", "onHandleIntent");
        try {
            Thread.sleep(6000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
