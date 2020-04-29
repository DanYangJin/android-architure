package com.shouzhan.design.utils.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * @author danbin
 * @version AccessibilityUtil.java, v 0.1 2020-04-28 9:31 AM danbin
 */
public class AccessibilityUtil {


    /**
     * 打开辅助功能
     */
    public static void openAccessibilitySetting(Context context) {
        try {
            Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Intent intent2 = new Intent();
                intent2.setClassName("com.android.settings", "com.android.settings.Settings/AccessibilitySettingsActivity");
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * 是否开通辅助功能
     */
    public static boolean isAccessibilitySettingsOn(Context context) {
        int i;
        String string;
        String str = context.getPackageName() + "/" + AudioSettingAccessibilityService.class.getCanonicalName();
        try {
            i = Settings.Secure.getInt(context.getContentResolver(), "accessibility_enabled");
        } catch (Settings.SettingNotFoundException e) {
            i = 0;
        }
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(':');
        if (i == 1 && (string = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)) != null) {
            simpleStringSplitter.setString(string);
            while (simpleStringSplitter.hasNext()) {
                if (simpleStringSplitter.next().equalsIgnoreCase(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取根节点
     */
    public static AccessibilityNodeInfo findRootNodeInfo(AccessibilityService accessibilityService) {
        if (accessibilityService != null) {
            AccessibilityNodeInfo accessibilityNodeInfo = null;
            int i = 0;
            while (true) {
                try {
                    accessibilityNodeInfo = accessibilityService.getRootInActiveWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (accessibilityNodeInfo != null) {
                    return accessibilityNodeInfo;
                }
                Log.e("xss", "找根节点" + i + "次");
                SystemClock.sleep(500);
                if (i >= 10) {
                    break;
                }
                i++;
            }
        }
        return null;
    }

    /**
     * 根据Text搜索所有符合条件的节点，模糊搜索方式
     *
     * @param accessibilityService
     * @param str 查找文案
     * @param count 遍历次数
     */
    public static AccessibilityNodeInfo findAccessibilityNodeInfoByText(AccessibilityService accessibilityService, String str, int count) {
        AccessibilityNodeInfo accessibilityNodeInfo;
        AccessibilityNodeInfo rootNodeInfo = findRootNodeInfo(accessibilityService);
        if (rootNodeInfo == null || TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split("\\|");
        if (split.length > 1) {
            accessibilityNodeInfo = null;
            for (String a2 : split) {
                accessibilityNodeInfo = findNodeInfoByText(rootNodeInfo, a2);
                if (accessibilityNodeInfo != null) {
                    break;
                }
            }
        } else {
            accessibilityNodeInfo = findNodeInfoByText(rootNodeInfo, str);
        }
        if (accessibilityNodeInfo != null || count <= 0) {
            return accessibilityNodeInfo;
        }
        SystemClock.sleep(500);
        return findAccessibilityNodeInfoByText(accessibilityService, str, count - 1);
    }

    private static AccessibilityNodeInfo findNodeInfoByText(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText;
        if (accessibilityNodeInfo == null || TextUtils.isEmpty(str) || (findAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str)) == null || findAccessibilityNodeInfosByText.size() <= 0) {
            return null;
        }
        for (AccessibilityNodeInfo next : findAccessibilityNodeInfosByText) {
            if (judgeNodeValid(next, str)) {
                return next;
            }
        }
        return null;
    }

    private static boolean judgeNodeValid(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        Log.e("xss", "judgeNodeValid: " + accessibilityNodeInfo.getText());
//        if (findChildNodes(accessibilityNodeInfo) == null) {
//            return false;
//        }
//        if (z) {
//            return TextUtils.equals(str, accessibilityNodeInfo.getText());
//        }
        return true;
    }

//    public static AccessibilityNodeInfo findChildNodes(AccessibilityNodeInfo accessibilityNodeInfo) {
//        AccessibilityNodeInfo h;
//        AccessibilityNodeInfo h2 = findChildNodeInfo(accessibilityNodeInfo);
//        if (h2 != null) {
//            return h2;
//        }
//        do {
//            accessibilityNodeInfo = accessibilityNodeInfo.getParent();
//            if (accessibilityNodeInfo == null) {
//                return null;
//            }
//            h = findChildNodeInfo(accessibilityNodeInfo);
//        } while (h == null);
//        return h;
//    }
//
//    private static AccessibilityNodeInfo findChildNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
//        if (accessibilityNodeInfo == null) {
//            return null;
//        }
//        if (accessibilityNodeInfo.isClickable()) {
//            return accessibilityNodeInfo;
//        }
//        for (int i = 0; i < accessibilityNodeInfo.getChildCount(); i++) {
//            AccessibilityNodeInfo h = findChildNodeInfo(accessibilityNodeInfo.getChild(i));
//            if (h != null) {
//                return h;
//            }
//        }
//        return null;
//    }


}
