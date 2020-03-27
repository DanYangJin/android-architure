package com.shouzhan.design.push;

import android.accessibilityservice.AccessibilityService;

/**
 * @author danbin
 * @version AccessibilityUtil.java, v 0.1 2020-03-27 4:05 PM danbin
 */
public class AccessibilityUtil {

    public static boolean performGlobalBack(AccessibilityService accessibilityService) {
        if (accessibilityService != null) {
            return accessibilityService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        }
        return false;
    }

}
