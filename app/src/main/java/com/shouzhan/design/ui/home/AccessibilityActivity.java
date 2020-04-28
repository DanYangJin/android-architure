package com.shouzhan.design.ui.home;

import android.view.View;

import com.shouzhan.design.R;
import com.shouzhan.design.base.BaseActivity;
import com.shouzhan.design.databinding.ActivityAccessibilityBinding;

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
                break;
            case R.id.mock_btn:
                break;
            default:
                break;
        }
    }

}
