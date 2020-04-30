package com.shouzhan.design.utils.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


/**
 * @author danbin
 * @version AccessibilityService.java, v 0.1 2020-04-28 9:24 AM danbin
 */
public class AudioSettingAccessibilityService extends AccessibilityService {

    private BroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter = new IntentFilter("action_phone_setting_task");

    public AudioSettingAccessibilityService() {
        Log.e("xss", "AudioSettingAccessibilityService");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                new Handler().post(() -> {
                    AccessibilityUtil.findAccessibilityNodeInfoByText(AudioSettingAccessibilityService.this, "开关", 2);
                });
            }
        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("xss", "AudioSettingAccessibilityService onCreate");
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e("xss", "AudioSettingAccessibilityService onAccessibilityEvent");
    }

    @Override
    public void onInterrupt() {
        Log.e("xss", "onInterrupt");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("xss", "AudioSettingAccessibilityService onDestroy");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

}
