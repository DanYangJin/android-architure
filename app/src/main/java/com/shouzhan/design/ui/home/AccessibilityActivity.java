package com.shouzhan.design.ui.home;

import android.view.View;

import com.shouzhan.design.R;
import com.shouzhan.design.base.BaseActivity;
import com.shouzhan.design.databinding.ActivityAccessibilityBinding;
import com.shouzhan.design.utils.accessibility.AccessibilityUtil;
import com.shouzhan.design.utils.accessibility.AudioDiagnosisManager;

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

    /**
     * TODO bugfix 拉起无用的页面导致多次操作返回
     * TODO bugfix 了解华为P系列系统白名单权限（应用速冻）
     * TODO bugfix 点击底部导航会导致异常case
     * */
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
    public void getData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioDiagnosisManager.getInstance().resetDiagnosisTask();
    }


}
