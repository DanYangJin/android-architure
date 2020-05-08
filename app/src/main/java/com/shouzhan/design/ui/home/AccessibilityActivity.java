package com.shouzhan.design.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.shouzhan.design.R;
import com.shouzhan.design.base.BaseActivity;
import com.shouzhan.design.databinding.ActivityAccessibilityBinding;
import com.shouzhan.design.utils.accessibility.AccessibilityUtil;
import com.shouzhan.design.utils.accessibility.AudioSettingAccessibilityService;
import com.shouzhan.design.utils.accessibility.FloatWindowView;
import com.shouzhan.design.utils.accessibility.SingleTask;
import com.shouzhan.design.utils.accessibility.TaskFactory;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author danbin
 * @version AccessibilityActivity.java, v 0.1 2020-03-26 3:19 PM danbin
 * 辅助功能Demo
 */
public class AccessibilityActivity extends BaseActivity<ActivityAccessibilityBinding> {

    private Queue<SingleTask> mSingleTasks = new LinkedList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_accessibility;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver();
    }

    private void registerReceiver() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction("action_phone_setting");
        mFilter.addAction("action_phone_setting_task_done");
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, mFilter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open_accessibility_setting_btn:
                AccessibilityUtil.openAccessibilitySetting(mContext);
                break;
            case R.id.accessibility_start_up_btn:
                sendTaskBroadcast(createStartUpTask());
                break;
            case R.id.accessibility_start_notification_btn:
                sendTaskBroadcast(createNotificationTask());
                break;
            case R.id.accessibility_close_power_btn:
                sendTaskBroadcast(createPowerUpTask());
                break;
            case R.id.accessibility_ignore_power_btn:
                sendTaskBroadcast(createIgnorePowerTask());
                break;
            case R.id.accessibility_setting_permission_btn:
                getData();
                executeNextTask();
                break;
            default:
                break;
        }
    }

    private void sendTaskBroadcast(SingleTask task) {
        Intent intent = new Intent(AudioSettingAccessibilityService.ACTION_PHONE_SETTING_TASK);
        intent.putExtra("task", task);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    private SingleTask createStartUpTask() {
        return new SingleTask().setTaskName("自启动").addStep("com.huawei.systemmanager/com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity", 4).addStep("付呗", 2).addStep("sleep", 6).addStep("付呗", 3).addStep("允许自启动|允許自動啟動", 2).addStep("允许关联启动|允許關聯啟動", 2).addStep("允许后台活动|允許背景活動", 2).addStep("确定|確定", 1).addStep("返回", 10);
    }

    private SingleTask createNotificationTask() {
        SingleTask task = new SingleTask().setTaskName("应用和通知").addStep("com.huawei.systemmanager/com.huawei.notificationmanager.ui.NotificationManagmentActivity", 4).addStep("付呗", 1).addStep("允许通知|允許通知", 2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            task.addStep("横幅|橫幅", 8).addStep("允许打扰|允許打擾", 8).addStep("锁屏通知|螢幕鎖定通知", 7).addStep("显示|顯示", 8).addStep("取消", 7);
            task.addStep("推送通知", 13).addStep("允许通知|允許通知", 2).addStep("横幅|橫幅", 2, false).addStep("允许打扰|优先显示|允許打擾", 8).addStep("锁屏通知|螢幕鎖定通知", 1).addStep("显示|顯示", 2).addStep("取消", 7).addStep("返回", 10);
        } else {
            task.addStep("横幅|橫幅", 2).addStep("允许打扰|优先显示|允許打擾", 8).addStep("锁屏通知|螢幕鎖定通知", 1).addStep("显示|顯示", 2).addStep("取消", 7);
        }
        return task;
    }

    private SingleTask createPowerUpTask() {
        return new SingleTask().setTaskName("省电模式").addStep("com.huawei.systemmanager/com.huawei.systemmanager.power.ui.HwPowerManagerActivity", 4).addStep("省电模式|省電模式", 9).addStep("超级省电|超級省電", 9).addStepById("android:id/icon2", 15).addStep("休眠时保持 WLAN 连接", 7).addStep("始终", 8).addStep("取消", 7).addStep("返回", 10);
    }

    private SingleTask createIgnorePowerTask() {
        return new SingleTask().setTaskName("忽略电池优化").addStep("com.android.settings/com.android.settings.Settings$HighPowerApplicationsActivity", 4).addStep("允许|允許", 1).addStep("所有应用|所有應用", 1).addStep("付呗", 1).addStep("允许|允許", 2).addStep("确定|確定", 1);
    }

    @Override
    public void getData() {
        mSingleTasks.clear();
        mSingleTasks = TaskFactory.getSystemSettingTask();
        FloatWindowView.getInstance().build();
    }

    private void executeNextTask() {
        if (this.mSingleTasks == null || this.mSingleTasks.isEmpty()) {
            FloatWindowView.getInstance().remove();
            startActivity(new Intent(this, CommonH5Activity.class));
            return;
        }
        SingleTask singleTask = this.mSingleTasks.poll();
        if (singleTask == null) {
            return;
        }
        sendTaskBroadcast(singleTask);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (AudioSettingAccessibilityService.ACTION_PHONE_SETTING_TASK_DONE.equals(action)) {
                executeNextTask();
            }
        }
    };

}
