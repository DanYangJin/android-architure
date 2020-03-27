package com.shouzhan.design.push;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.apache.commons.lang3.StringUtils;

import java.util.Queue;

/**
 * @author danbin
 * @version AutoSettingVoiceService.java, v 0.1 2020-03-26 3:32 PM danbin
 */
public class AutoSettingVoiceService extends AccessibilityService {

    public static final String ACTION_PHONE_SETTING_TASK = "action_phone_setting_task";
    public static final String ACTION_PHONE_SETTING_FINISH = "action_phone_setting_finish";

    private BroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter;

    private Queue<Step> mTaskSteps;
    private HandlerThread mHandlerThread;
    private Handler mHandler;

    public AutoSettingVoiceService() {
        Log.e("xss", "AutoSettingVoiceService");
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("action_phone_setting_task");
        mIntentFilter.addAction("action_phone_setting_finish");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (StringUtils.equals(action, ACTION_PHONE_SETTING_TASK)) {
                    Log.e("xss", "1111");
                    handleTask((Task) intent.getSerializableExtra("task"));
                } else if (StringUtils.equals(action, ACTION_PHONE_SETTING_FINISH)) {
                    Log.e("xss", "2222");
                }
            }
        };
    }

    private void handleTask(Task task) {
        boolean isExist = true;
        mTaskSteps = task != null ? task.getStepQueue() : null;
        if (mTaskSteps == null || mTaskSteps.isEmpty()) {
            return;
        }
        Step peek = mTaskSteps.peek();
        if (peek == null) {
            return;
        }
        int action = peek.getAction();
        if (action == Step.STEP_ACTION_BACK) {
            AccessibilityUtil.performGlobalBack(this);
        } else if (action == Step.STEP_ACTION_JUMP) {
            this.mTaskSteps.poll();
            String actionValue = peek.getActionValue();
            Bundle params = peek.getParams();
            Uri data = peek.getData();
            String[] split = actionValue.split("\\|");
            if (split.length > 1) {
                int i = 0;
                while (true) {
                    if (i >= split.length) {
                        isExist = false;
                        break;
                    } else if (skipSetting(split[i], params, data)) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (!isExist) {
                    // 无法启动对应页面
                }
            } else if (!skipSetting(actionValue, params, data)) {
                // 无法启动对应页面
            }
        }
    }

    private boolean skipSetting(String actionValue, Bundle bundle, Uri uri) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (uri != null) {
            intent.setData(uri);
        }
//        intent.setFlags(1149239296);
        String[] split = actionValue.split("/");
        if (split.length > 1) {
            intent.setComponent(new ComponentName(split[0], split[1]));
        } else {
            intent.setAction(actionValue);
            if ("android.settings.SETTINGS".equals(actionValue)) {
//                intent.setFlags(67108864);
            }
        }
        if (intent.resolveActivityInfo(getPackageManager(), PackageManager.MATCH_DEFAULT_ONLY) != null) {
//            Activity b = AppManager.m47754a().mo56385b();
//            if (b != null) {
//                try {
//                    b.startActivity(intent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else {
            startActivity(intent);
            accessibilityEvent();
//            }
            return true;
        }
        return false;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("xss", "onCreate");
        this.mHandlerThread = new HandlerThread("auto_setting");
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mReceiver, this.mIntentFilter);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e("xss", "onAccessibilityEvent!!!");
        CharSequence packageName = event.getPackageName();
        Log.e("xss", "onAccessibilityEvent: " + packageName + " , " + getPackageName());
        if (packageName != null && !packageName.toString().equals(getPackageName()) && event.getEventType() == 32) {
//            this.mHandler.post(AutoSettingVoiceService.this::accessibilityEvent);
        }
    }

    private void accessibilityEvent() {
        SystemClock.sleep(4000);
        AccessibilityNodeInfo accessibilityNodeInfo = null;
        Step peek = this.mTaskSteps.peek();
        if (peek == null) {
            return;
        }
        if (peek.getAction() == Step.STEP_ACTION_SCROLL_TOP) {

        } else if (peek.getAction() == Step.STEP_ACTION_SLEEP) {

        } else if (peek.getAction() == Step.STEP_ACTION_BACK) {
            this.mTaskSteps.poll();
            AccessibilityUtil.performGlobalBack(this);
            SystemClock.sleep(1000);
        } else {
            if (peek.getAction() == Step.STEP_ACTION_CLICK_ID || peek.getAction() == Step.STEP_ACTION_TRY_CLICK_ID) {

            } else {

            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.e("xss", "onInterrupt!!!");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        // 辅助功能已开启
        Log.e("xss", "onServiceConnected");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("xss", "onDestroy");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mReceiver);
    }

}
