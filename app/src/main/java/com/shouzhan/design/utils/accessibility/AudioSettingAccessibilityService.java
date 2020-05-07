package com.shouzhan.design.utils.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.apache.commons.lang3.StringUtils;

import java.util.Queue;


/**
 * @author danbin
 * @version AccessibilityService.java, v 0.1 2020-04-28 9:24 AM danbin
 */
public class AudioSettingAccessibilityService extends AccessibilityService {

    public static final String ACTION_PHONE_SETTING_TASK = "action_phone_setting_task";
    public static final String ACTION_PHONE_SETTING_TASK_DONE = "action_phone_setting_task_done";

    private BroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter = new IntentFilter("action_phone_setting_task");

    private SingleTask mSingleTask;
    private Queue<SingleStep> mSingleSteps;
    private boolean isTaskStart = false;
    private boolean isAccessibilityTaskStart = false;

    public AudioSettingAccessibilityService() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (ACTION_PHONE_SETTING_TASK.equals(intent.getAction())) {
                    executeSingleTask((SingleTask) intent.getSerializableExtra("task"));
                }
            }
        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, mIntentFilter);
    }

    /**
     * 执行设置任务
     */
    private void executeSingleTask(SingleTask task) {
        this.mSingleTask = task;
        boolean done = true;
        this.isTaskStart = true;
        this.isAccessibilityTaskStart = false;
        this.mSingleSteps = task != null ? task.getStepQueue() : null;
        if (mSingleSteps == null || mSingleSteps.isEmpty()) {
            return;
        }
        SingleStep singleStep = mSingleSteps.peek();
        if (singleStep == null || singleStep.getAction() != SingleStep.STEP_ACTION_JUMP) {
            return;
        }
        switch (singleStep.getAction()) {
            case SingleStep.STEP_ACTION_BACK:
                // 返回操作
                this.isAccessibilityTaskStart = false;
                SystemClock.sleep(1000);
                executeBack();
                break;
            case SingleStep.STEP_ACTION_RECENTS:
                // 最近操作
                SystemClock.sleep(1000);
                mSingleSteps.poll();
                AccessibilityUtil.performGlobalRecentsAction(this);
                break;
            default:
                mSingleSteps.poll();
                String actionValue = singleStep.getActionValue();
                Bundle params = singleStep.getParams();
                Uri data = singleStep.getData();
                String[] split = actionValue.split("\\|");
                if (split.length > 1) {
                    int i = 0;
                    while (true) {
                        if (i >= split.length) {
                            done = false;
                            break;
                        } else if (openSystemSettingPage(split[i], params, data)) {
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (!done) {
                        executeTaskDone();
                    }
                } else if (!openSystemSettingPage(actionValue, params, data)) {
                    executeTaskDone();
                }
                break;
        }
    }

    /**
     * 执行任务完毕
     */
    private void executeTaskDone() {
        isTaskStart = false;
        sendTaskStatusBroadcast(ACTION_PHONE_SETTING_TASK_DONE, null);
    }

    private boolean openSystemSettingPage(String actionValue, Bundle bundle, Uri uri) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (uri != null) {
            intent.setData(uri);
        }
        String[] split = actionValue.split("/");
        if (split.length > 1) {
            intent.setComponent(new ComponentName(split[0], split[1]));
        } else {
            intent.setAction(actionValue);
            if (Settings.ACTION_SETTINGS.equals(actionValue)) {
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        }
        if (intent.resolveActivityInfo(getPackageManager(), PackageManager.MATCH_DEFAULT_ONLY) != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return false;
    }

    private void executeBack() {
        SingleStep poll = this.mSingleSteps.poll();
        if (poll == null) {
            executeTaskDone();
            return;
        }
        if (poll.getAction() == SingleStep.STEP_ACTION_BACK) {
            AccessibilityUtil.performGlobalBackAction(this);
        }
        SystemClock.sleep(1000);
        executeBack();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.e("xss", "AudioSettingAccessibilityService onAccessibilityEvent");
        CharSequence packageName = accessibilityEvent.getPackageName();
        if (packageName != null && !StringUtils.isEmpty(packageName.toString()) && !packageName.toString().equals(getPackageName()) && accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (!this.isTaskStart) {
                return;
            }
            if (this.mSingleSteps == null || this.mSingleSteps.isEmpty()) {
                this.isTaskStart = false;
            } else if (!this.isAccessibilityTaskStart) {
                new Handler().post(AudioSettingAccessibilityService.this::executeAccessibilityTask);
            }
        }

    }

    public void executeAccessibilityTask() {
        AccessibilityNodeInfo accessibilityNodeInfo;
        SingleStep peek = this.mSingleSteps.peek();
        if (peek == null) {
            executeTaskDone();
            return;
        }
        this.isAccessibilityTaskStart = true;
        if (peek.getAction() == SingleStep.STEP_ACTION_SCROLL_TOP) {
            this.mSingleSteps.poll();
            AccessibilityUtil.performScrollBackwardAction(AccessibilityUtil.findRootNodeInfo((AccessibilityService) this));
            executeAccessibilityTask();
        } else if (peek.getAction() == SingleStep.STEP_ACTION_SLEEP) {
            this.mSingleSteps.poll();
            SystemClock.sleep(1000);
            executeAccessibilityTask();
        } else if (peek.getAction() == SingleStep.STEP_ACTION_BACK) {
            this.mSingleSteps.poll();
            AccessibilityUtil.performGlobalBackAction(this);
            SystemClock.sleep(1000);
            executeAccessibilityTask();
        } else {
            String actionValue = peek.getActionValue();
            if (peek.getAction() == SingleStep.STEP_ACTION_CLICK_ID || peek.getAction() == SingleStep.STEP_ACTION_TRY_CLICK_ID) {
                accessibilityNodeInfo = AccessibilityUtil.findAccessibilityNodeInfoByViewIds(this, peek.getViewId());
            } else {
                accessibilityNodeInfo = AccessibilityUtil.findAccessibilityNodeInfoByTexts(this, actionValue, 1, peek.isExactMatch());
            }
            if (accessibilityNodeInfo != null) {
                this.mSingleSteps.poll();
                switch (peek.getAction()) {
                    case SingleStep.STEP_ACTION_CLICK:
                    case SingleStep.STEP_ACTION_TRY_CLICK:
                    case SingleStep.STEP_ACTION_TRY_CLICK_JUMP:
                    case SingleStep.STEP_ACTION_CLICK_ID:
                    case SingleStep.STEP_ACTION_TRY_CLICK_ID:
                        AccessibilityUtil.performClickAction(accessibilityNodeInfo);
                        break;
                    case SingleStep.STEP_ACTION_OPEN:
                    case SingleStep.STEP_ACTION_TRY_OPEN:
                        performOpenOrCloseAction(accessibilityNodeInfo, true, peek.isBoundMatch());
                        break;
                    case SingleStep.STEP_ACTION_CLOSE:
                    case SingleStep.STEP_ACTION_TRY_CLOSE:
                        performOpenOrCloseAction(accessibilityNodeInfo, false, peek.isBoundMatch());
                        break;
                    case SingleStep.STEP_ACTION_TRY_OPEN_ID:
                        performOpenOrCloseIdAction(accessibilityNodeInfo, peek.getViewId(), true);
                        break;
                    case SingleStep.STEP_ACTION_TRY_CLOSE_ID:
                        performOpenOrCloseIdAction(accessibilityNodeInfo, peek.getViewId(), false);
                        break;
                    default:
                        break;
                }
                SystemClock.sleep(500);
                executeAccessibilityTask();
            } else if ((peek.getAction() == SingleStep.STEP_ACTION_CLICK || peek.getAction() == SingleStep.STEP_ACTION_OPEN || peek.getAction() == SingleStep.STEP_ACTION_CLOSE || peek.getAction() == SingleStep.STEP_ACTION_CLICK_ID) && !AccessibilityUtil.isScrollNodeInfo(AccessibilityUtil.findRootNodeInfo(this))) {
                SystemClock.sleep(1000);
                executeAccessibilityTask();
            } else {
                SystemClock.sleep(500);
                if (!AccessibilityUtil.performScrollForwardAction(AccessibilityUtil.findRootNodeInfo(this))) {
                    if (peek.getAction() == SingleStep.STEP_ACTION_TRY_CLICK || peek.getAction() == SingleStep.STEP_ACTION_TRY_OPEN || peek.getAction() == SingleStep.STEP_ACTION_TRY_CLOSE || peek.getAction() == SingleStep.STEP_ACTION_TRY_CLICK_ID || peek.getAction() == SingleStep.STEP_ACTION_TRY_OPEN_ID || peek.getAction() == SingleStep.STEP_ACTION_SLIDE_OPEN) {
                        this.mSingleSteps.poll();
                        AccessibilityUtil.performScrollBackwardAction(AccessibilityUtil.findRootNodeInfo(this));
                    } else {
                        executeTaskDone();
                        return;
                    }
                }
                SystemClock.sleep(500);
                executeAccessibilityTask();
            }
        }
    }

    private boolean performOpenOrCloseAction(AccessibilityNodeInfo accessibilityNodeInfo, boolean status, boolean boundMatch) {
        boolean isChecked;
        AccessibilityNodeInfo allNodes = AccessibilityUtil.findAllNodes(accessibilityNodeInfo);
        AccessibilityNodeInfo checkableNodeInfo = AccessibilityUtil.findAccessibilityCheckableNodeInfo(allNodes);
        if (checkableNodeInfo == null) {
            checkableNodeInfo = AccessibilityUtil.findAccessibilityCheckableNodeInfo(accessibilityNodeInfo);
        }
        if (allNodes == null || checkableNodeInfo == null) {
            return false;
        }
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        accessibilityNodeInfo.getBoundsInScreen(rect);
        checkableNodeInfo.getBoundsInScreen(rect2);
        if (boundMatch) {
            isChecked = Math.abs(rect2.bottom - rect.bottom) > 100 || checkableNodeInfo.isChecked();
        } else {
            isChecked = checkableNodeInfo.isChecked();
        }
        sendTaskStatusBroadcast("action_phone_setting", "" + isChecked);
        if (isChecked != status) {
            return checkableNodeInfo.isClickable() ? checkableNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK) : allNodes.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        return true;
    }

    private boolean performOpenOrCloseIdAction(AccessibilityNodeInfo accessibilityNodeInfo, String viewId, boolean status) {
        AccessibilityNodeInfo allNodes = AccessibilityUtil.findAllNodes(accessibilityNodeInfo);
        AccessibilityNodeInfo nodeInfoByViewId = AccessibilityUtil.findAccessibilityNodeInfoByViewId(allNodes, viewId);
        if (allNodes == null || nodeInfoByViewId == null) {
            return false;
        }
        boolean isEnabled = nodeInfoByViewId.isEnabled();
        sendTaskStatusBroadcast("action_phone_setting", "" + isEnabled);
        if (isEnabled != status) {
            return nodeInfoByViewId.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        return true;
    }

    private void sendTaskStatusBroadcast(String action, String value) {
        if (this.mSingleTask == null) {
            return;
        }
        Intent intent = new Intent(action);
        if (StringUtils.isNotEmpty(value)) {
            intent.putExtra("value", value);
        }
        intent.putExtra("taskId", this.mSingleTask.getTaskId());
        intent.putExtra("taskName", this.mSingleTask.getTaskName());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
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
