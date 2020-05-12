package com.shouzhan.design.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.fshows.android.stark.utils.FsLogUtil;
import com.shouzhan.design.R;
import com.shouzhan.design.base.BaseActivity;
import com.shouzhan.design.databinding.ActivityAccessibilityBinding;
import com.shouzhan.design.utils.accessibility.AccessibilityUtil;
import com.shouzhan.design.utils.accessibility.AudioDiagnosisManager;
import com.shouzhan.design.utils.accessibility.AudioSettingConstants;
import com.shouzhan.design.utils.permission.SysPermission;
import com.shouzhan.design.utils.permission.SysPermissionFactory;

import java.util.List;

/**
 * @author danbin
 * @version AccessibilityActivity.java, v 0.1 2020-03-26 3:19 PM danbin
 * 辅助功能Demo
 */
public class AccessibilityActivity extends BaseActivity<ActivityAccessibilityBinding> {

    private List<SysPermission> mSysPermissions;

    @Override
    public int getLayoutId() {
        return R.layout.activity_accessibility;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open_accessibility_setting_btn:
                AccessibilityUtil.openAccessibilitySetting(mContext);
                break;
            case R.id.accessibility_open_permission_btn:
                AudioDiagnosisManager.getInstance().startDiagnosisTask(this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioDiagnosisManager.getInstance().resetDiagnosisTask();
    }

    @Override
    public void getData() {
        mSysPermissions = SysPermissionFactory.getSysPermissions(mContext);
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, "size: " + mSysPermissions.size());
        SysPermission sysPermission = mSysPermissions.get(3);
        Button openPermissionBtn = findViewById(R.id.manual_open_permission_btn);
        openPermissionBtn.setText(sysPermission.title);
        openPermissionBtn.setOnClickListener(v -> startActivity(SysPermissionFactory.getIntent(sysPermission)));
    }

}
