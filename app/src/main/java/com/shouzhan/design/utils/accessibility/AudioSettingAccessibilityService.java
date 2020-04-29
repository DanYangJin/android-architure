package com.shouzhan.design.utils.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * @author danbin
 * @version AccessibilityService.java, v 0.1 2020-04-28 9:24 AM danbin
 */
public class AudioSettingAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e("xss", "onAccessibilityEvent");
        String pkgName = event.getPackageName().toString();
        int eventType = event.getEventType();
        Log.e("xss", "onAccessibilityEvent: " + pkgName + " , " + eventType);
        AccessibilityUtil.findAccessibilityNodeInfoByText(this, "开关", 2);
    }

    @Override
    public void onInterrupt() {
        Log.e("xss", "onInterrupt");
    }

}
