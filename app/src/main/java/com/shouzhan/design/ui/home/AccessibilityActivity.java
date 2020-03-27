package com.shouzhan.design.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.shouzhan.design.R;
import com.shouzhan.design.base.BaseActivity;
import com.shouzhan.design.databinding.ActivityAccessibilityBinding;
import com.shouzhan.design.push.Task;

/**
 * @author danbin
 * @version AccessibilityActivity.java, v 0.1 2020-03-26 3:19 PM danbin
 * 辅助功能Demo
 */
public class AccessibilityActivity extends BaseActivity<ActivityAccessibilityBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_accessibility;
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.accessibility_btn:
                skipAccessibilityActivity(this);
                break;
            case R.id.mock_btn:
                mockOperate();
                break;
            default:
                break;
        }
    }

    private void mockOperate() {
        Task task = new Task().setTaskName("锁屏清理")
                .addStep("com.huawei.systemmanager/com.huawei.systemmanager.optimize.process.ProtectActivity", 4)
                .addStep("返回", 10);
        Intent intent = new Intent();
        intent.setAction("action_phone_setting_task");
        intent.putExtra("task", task);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    public void skipAccessibilityActivity(Context context) {
        try {
            Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                Intent intent2 = new Intent();
                intent2.setClassName("com.android.settings", "com.android.settings.Settings$AccessibilitySettingsActivity");
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

}
