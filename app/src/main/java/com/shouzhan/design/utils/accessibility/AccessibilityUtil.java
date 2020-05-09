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
     * @param exactMatch 是否准确的匹配文案
     */
    public static AccessibilityNodeInfo findAccessibilityNodeInfoByTexts(AccessibilityService accessibilityService, String str, int count, boolean exactMatch) {
        AccessibilityNodeInfo accessibilityNodeInfo;
        AccessibilityNodeInfo rootNodeInfo = findRootNodeInfo(accessibilityService);
        if (rootNodeInfo == null || TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split("\\|");
        if (split.length > 1) {
            accessibilityNodeInfo = null;
            for (String a2 : split) {
                accessibilityNodeInfo = findNodeInfoByText(rootNodeInfo, a2, exactMatch);
                if (accessibilityNodeInfo != null) {
                    break;
                }
            }
        } else {
            accessibilityNodeInfo = findNodeInfoByText(rootNodeInfo, str, exactMatch);
        }
        if (accessibilityNodeInfo != null || count <= 0) {
            return accessibilityNodeInfo;
        }
        SystemClock.sleep(500);
        return findAccessibilityNodeInfoByTexts(accessibilityService, str, count - 1, exactMatch);
    }

    private static AccessibilityNodeInfo findNodeInfoByText(AccessibilityNodeInfo accessibilityNodeInfo, String str, boolean exactMatch) {
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText;
        if (accessibilityNodeInfo == null || TextUtils.isEmpty(str) || (findAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str)) == null || findAccessibilityNodeInfosByText.size() <= 0) {
            return null;
        }
        for (AccessibilityNodeInfo next : findAccessibilityNodeInfosByText) {
            if (judgeNodeValid(next, str, exactMatch)) {
                return next;
            }
        }
        return null;
    }

    private static boolean judgeNodeValid(AccessibilityNodeInfo accessibilityNodeInfo, String str, boolean exactMatch) {
        if (findAllNodes(accessibilityNodeInfo) == null) {
            return false;
        }
        if (exactMatch) {
            return TextUtils.equals(str, accessibilityNodeInfo.getText());
        }
        return true;
    }

    /**
     * 寻找当前节点相关的全部节点，优先向下寻找子节点，其次向上寻找父节点
     * */
    public static AccessibilityNodeInfo findAllNodes(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo h;
        AccessibilityNodeInfo h2 = findClickableNodeInfo(accessibilityNodeInfo);
        if (h2 != null) {
            return h2;
        }
        do {
            accessibilityNodeInfo = accessibilityNodeInfo.getParent();
            if (accessibilityNodeInfo == null) {
                return null;
            }
            h = findClickableNodeInfo(accessibilityNodeInfo);
        } while (h == null);
        return h;
    }

    private static AccessibilityNodeInfo findClickableNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null) {
            return null;
        }
        if (accessibilityNodeInfo.isClickable()) {
            return accessibilityNodeInfo;
        }
        for (int i = 0; i < accessibilityNodeInfo.getChildCount(); i++) {
            AccessibilityNodeInfo h = findClickableNodeInfo(accessibilityNodeInfo.getChild(i));
            if (h != null) {
                return h;
            }
        }
        return null;
    }

    /**
     * 模拟点击事件
     * */
    public static boolean performClickAction(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo c = findAllNodes(accessibilityNodeInfo);
        if (c != null) {
            return c.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        return false;
    }

    /**
     * 模拟向后滚动事件
     * */
    public static boolean performScrollBackwardAction(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo a = findAccessibilityScrollNodeInfo(accessibilityNodeInfo);
        if (a == null) {
            return false;
        }
        boolean z = false;
        while (a.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)) {
            SystemClock.sleep(100);
            z = true;
        }
        return z;
    }


    public static AccessibilityNodeInfo findAccessibilityScrollNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        return findAccessibilityScrollNodeInfo(accessibilityNodeInfo, 30);
    }

    /**
     * 查找可滚动的节点
     * */
    public static AccessibilityNodeInfo findAccessibilityScrollNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo, int count) {
        int i2 = count - 1;
        if (accessibilityNodeInfo == null || i2 <= 0) {
            return null;
        }
        if (accessibilityNodeInfo.isScrollable() && !"android.widget.HorizontalScrollView".equals(accessibilityNodeInfo.getClassName().toString()) && !"com.color.widget.ColorViewPager".equals(accessibilityNodeInfo.getClassName().toString()) && !"android.support.v4.view.ViewPager".equals(accessibilityNodeInfo.getClassName().toString()) && !"android.widget.Spinner".equals(accessibilityNodeInfo.getClassName().toString())) {
            return accessibilityNodeInfo;
        }
        for (int i3 = 0; i3 < accessibilityNodeInfo.getChildCount(); i3++) {
            AccessibilityNodeInfo a = findAccessibilityScrollNodeInfo(accessibilityNodeInfo.getChild(i3), i2);
            if (a != null) {
                return a;
            }
        }
        return null;
    }

    /**
     * 模拟向前滚动事件
     * */
    public static boolean performScrollForwardAction(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo a = findAccessibilityScrollNodeInfo(accessibilityNodeInfo);
        if (a != null) {
            return a.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
        }
        return false;
    }

    private static AccessibilityNodeInfo findCheckableNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null) {
            return null;
        }
        if (accessibilityNodeInfo.isCheckable()) {
            return accessibilityNodeInfo;
        }
        for (int i = 0; i < accessibilityNodeInfo.getChildCount(); i++) {
            AccessibilityNodeInfo i2 = findCheckableNodeInfo(accessibilityNodeInfo.getChild(i));
            if (i2 != null) {
                return i2;
            }
        }
        return null;
    }

    /**
     * 模拟点击"最近"按键
     * */
    public static boolean performGlobalRecentsAction(AccessibilityService accessibilityService) {
        if (accessibilityService != null) {
            return accessibilityService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
        }
        return false;
    }

    /**
     * 模拟点击"返回"按键
     * */
    public static boolean performGlobalBackAction(AccessibilityService accessibilityService) {
        if (accessibilityService != null) {
            return accessibilityService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        }
        return false;
    }

    /**
     * 查找可滚动的节点
     * */
    public static boolean isScrollNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        return findAccessibilityScrollNodeInfo(accessibilityNodeInfo) != null;
    }

    public static AccessibilityNodeInfo findAccessibilityCheckableNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo c;
        if (accessibilityNodeInfo == null || (c = findAllNodes(accessibilityNodeInfo)) == null) {
            return null;
        }
        return findCheckableNodeInfo(c);
    }

    public static AccessibilityNodeInfo findAccessibilityNodeInfoByViewId(AccessibilityNodeInfo accessibilityNodeInfo, String viewId) {
        AccessibilityNodeInfo c;
        if (accessibilityNodeInfo == null || (c = findAllNodes(accessibilityNodeInfo)) == null) {
            return null;
        }
        return findNodeInfoByViewId(c, viewId);
    }

    public static AccessibilityNodeInfo findNodeInfoByViewId(AccessibilityNodeInfo accessibilityNodeInfo, String viewId) {
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId;
        if (accessibilityNodeInfo == null || (findAccessibilityNodeInfosByViewId = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(viewId)) == null || findAccessibilityNodeInfosByViewId.size() <= 0) {
            return null;
        }
        for (AccessibilityNodeInfo next : findAccessibilityNodeInfosByViewId) {
            if (findAllNodes(next) != null) {
                return next;
            }
        }
        return null;
    }

    public static AccessibilityNodeInfo findAccessibilityNodeInfoByViewIds(AccessibilityService accessibilityService, String str) {
        AccessibilityNodeInfo a = findRootNodeInfo(accessibilityService);
        String[] split = str.split("\\|");
        AccessibilityNodeInfo accessibilityNodeInfo = null;
        int i = 0;
        while (i < split.length && (accessibilityNodeInfo = findNodeInfoByViewId(a, split[i])) == null) {
            i++;
        }
        return accessibilityNodeInfo;
    }

}
