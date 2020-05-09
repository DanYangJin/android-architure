package com.shouzhan.design.utils.accessibility;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.fshows.android.stark.utils.StringPool;
import com.shouzhan.design.utils.OSUtil;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author danbin
 * @version TaskFactory.java, v 0.1 2020-05-07 5:13 PM danbin
 */
public class TaskFactory {

    public static Queue<SettingTask> getSettingTask(Context context) {
        return getSettingTask(context, false);
    }

    public static Queue<SettingTask> getSettingTask(Context context, boolean isOpen) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return null;
        }
        OSUtil.ROM_TYPE romType = OSUtil.getRomType();
        LinkedList<SettingTask> linkedList = new LinkedList<>();
        if (!isDrawOverlays(context, romType, linkedList, isOpen) && isOpen) {
            linkedList.add(accessibilityBackTask());
        }
        switch (romType) {
            case EMUI_ROM:
                linkedList.addAll(createEmUiTask());
                break;
            case MIUI_ROM:
                linkedList.add(null);
                break;
            case COLOROS_ROM:
                linkedList.addAll(null);
                break;
            default:
                break;
        }
        return linkedList;
    }

    private static Queue<SettingTask> createEmUiTask() {
        String prop = OSUtil.getSystemProp();
        LinkedList<SettingTask> linkedList = new LinkedList<>();
        if (StringUtils.isNotEmpty(prop)) {
            String trim = prop.replace(OSUtil.EMUI_SUFFIX, StringPool.EMPTY).replace("_", "").trim();
            if (trim.contains(StringPool.DOT)) {
                trim = trim.substring(0, trim.indexOf(StringPool.DOT));
            }
            int intValue = Integer.parseInt(trim);
            if (intValue == OSUtil.VERSION_3) {
                linkedList.add(EmUiTask.openEmui3ProtectTask());
            } else if (intValue == OSUtil.VERSION_4) {
                linkedList.add(EmUiTask.openEmui3ProtectTask());
                linkedList.add(EmUiTask.openEmui4StartupTask());
            } else if (intValue == OSUtil.VERSION_5) {
                linkedList.add(EmUiTask.openEmui5ProtectTask());
                linkedList.add(EmUiTask.openEmui4StartupTask());
                linkedList.add(EmUiTask.openRelativeStartUp());
                linkedList.addAll(EmUiTask.openEmui5NotificationLightTask());
                linkedList.add(EmUiTask.openEmui5NotificationLockTask());
                linkedList.add(EmUiTask.openEmui5NotificationTask());
                linkedList.add(EmUiTask.closeEmui5PowerModeTask());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    linkedList.add(EmUiTask.ignoreEmui5HighPowerMode());
                }
            } else {
                linkedList.add(EmUiTask.openStartUpTaskAfterEmui5());
                linkedList.addAll(EmUiTask.openNotificationTaskAfterEmui5());
                if (intValue < OSUtil.VERSION_9) {
                    linkedList.addAll(EmUiTask.openNotificationLightTaskBeforeEmui9());
                    linkedList.add(EmUiTask.openNotificationLockTaskBeforeEmui9());
                    linkedList.add(EmUiTask.closeEmui5PowerModeTask());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        linkedList.add(EmUiTask.ignoreEmui5HighPowerMode());
                    }
                } else {
                    linkedList.add(EmUiTask.closePowerModeTaskAfterEmui9());
                    linkedList.add(EmUiTask.openNotificationLightTaskAfterEmui9());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        linkedList.add(EmUiTask.closeHighPowerAfterEmui9());
                    }
                }
            }
        }
        return linkedList;
    }

    public static class EmUiTask {

        public static Queue<SettingTask> openNotificationTaskAfterEmui5() {
            LinkedList<SettingTask> linkedList = new LinkedList<>();
            SettingTask addStep = new SettingTask().setTaskName("应用和通知").addStep("com.huawei.systemmanager/com.huawei.notificationmanager.ui.NotificationManagmentActivity", 4).addStep("付呗", 1).addStep("允许通知|允許通知", 2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                addStep.addStep("横幅|橫幅", 8).addStep("允许打扰|允許打擾", 8).addStep("锁屏通知|螢幕鎖定通知", 7).addStep("显示|顯示", 8).addStep("取消", 7);
                addStep.addStep("推送通知", 13).addStep("允许通知|允許通知", 2).addStep("横幅|橫幅", 2, false).addStep("允许打扰|优先显示|允許打擾", 8).addStep("锁屏通知|螢幕鎖定通知", 1).addStep("显示|顯示", 2).addStep("取消", 7).addStep("返回", 10);
            } else {
                addStep.addStep("横幅|橫幅", 2).addStep("允许打扰|优先显示|允許打擾", 8).addStep("锁屏通知|螢幕鎖定通知", 1).addStep("显示|顯示", 2).addStep("取消", 7);
            }
            linkedList.add(addStep);
            linkedList.add(new SettingTask().setTaskName("通知设置返回").addStep("返回", 10));
            return linkedList;
        }

        public static SettingTask openEmui5NotificationTask() {
            return new SettingTask().setTaskName("应用和通知").addStep("com.huawei.systemmanager/com.huawei.notificationmanager.ui.NotificationManagmentActivity", 4).addStep("付呗", 1).addStep("允许通知|允許通知", 2).addStep("横幅|橫幅", 2).addStep("在锁屏上显示|顯示於鎖定畫面", 8).addStep("锁屏时", 9).addStep("优先显示", 8).addStep("返回", 10);
        }

        public static SettingTask openNotificationLightTaskAfterEmui9() {
            return new SettingTask().setTaskName("通知亮屏").addStep("com.huawei.systemmanager/com.huawei.notificationmanager.ui.NotificationManagmentActivity", 4).addStep("锁屏通知|更多通知設定", 7).addStep("显示所有通知|更多通知設定", 8).addStep("取消", 7).addStep("更多通知设置|更多通知設定", 7).addStep("通知亮屏提示|通知開啟螢幕", 8, false).addStep("返回", 10);
        }

        public static Queue<SettingTask> openEmui5NotificationLightTask() {
            LinkedList<SettingTask> linkedList = new LinkedList<>();
            linkedList.add(new SettingTask().setTaskName("设置搜索返回").addStep("android.settings.SETTINGS", 4).addStepById("com.android.settings:id/back", 15).addStep("返回", 10));
            linkedList.add(new SettingTask().setTaskName("通知亮屏").addStep("android.settings.SETTINGS", 4).addStep("滚动到顶部", 5).addStep("通知和状态栏|通知中心|通知與狀態列", 1).addStep("通知亮屏提示|通知開啟螢幕", 8, false).addStep("返回", 10).addStep("返回", 10));
            return linkedList;
        }

        public static SettingTask openEmui5NotificationLockTask() {
            return new SettingTask().setTaskName("锁屏通知").addStep("com.huawei.systemmanager/com.huawei.notificationmanager.ui.NotificationManagmentActivity", 4).addStepById("android:id/icon2", 15).addStep("设备锁定时|锁屏通知|螢幕鎖定通知", 7).addStep("显示所有通知内容|显示所有通知|更多通知設定", 8).addStep("确定|確定", 7).addStep("返回", 10);
        }

        public static SettingTask openNotificationLockTaskBeforeEmui9() {
            return new SettingTask().setTaskName("锁屏通知").addStep("com.huawei.systemmanager/com.huawei.notificationmanager.ui.NotificationManagmentActivity", 4).addStepById("androidhwext:id/action_menu_more_button", 15).addStep("锁屏通知|螢幕鎖定通知", 7).addStep("显示所有通知|更多通知設定", 8).addStep("确定|確定", 7).addStep("返回", 10);
        }

        public static Queue<SettingTask> openNotificationLightTaskBeforeEmui9() {
            LinkedList<SettingTask> linkedList = new LinkedList<>();
            linkedList.add(new SettingTask().setTaskName("设置搜索返回").addStep("android.settings.SETTINGS", 4).addStepById("com.android.settings:id/back", 15).addStep("返回", 10));
            linkedList.add(new SettingTask().setTaskName("通知亮屏").addStep("android.settings.SETTINGS", 4).addStep("滚动到顶部", 5).addStep("应用和通知|通知和状态栏|通知中心|通知與狀態列", 1).addStep("通知和状态栏设置", 1).addStep("通知亮屏提示|通知開啟螢幕", 8, false).addStep("返回", 10).addStep("返回", 10).addStep("返回", 10));
            return linkedList;
        }

        public static SettingTask openEmui5ProtectTask() {
            return new SettingTask().setTaskName("锁屏清理").addStep("com.huawei.systemmanager/com.huawei.systemmanager.optimize.process.ProtectActivity", 4).addStep("付呗", 3).addStep("返回", 10);
        }

        public static SettingTask openEmui3ProtectTask() {
            return new SettingTask().setTaskName("受保护应用").addStep("com.huawei.systemmanager/com.huawei.systemmanager.optimize.process.ProtectActivity", 4).addStep("付呗", 2).addStep("返回", 10);
        }

        public static SettingTask closePowerModeTaskAfterEmui9() {
            return new SettingTask().setTaskName("省电模式").addStep("com.huawei.systemmanager/com.huawei.systemmanager.power.ui.HwPowerManagerActivity", 4).addStep("性能模式", 9).addStep("省电模式|省電模式", 9).addStep("超级省电|超級省電", 9).addStep("普通省电|普通省電", 8).addStep("更多电池设置|更多電池設定", 7).addStep("休眠时始终保持网络连接|休眠時一律保持網絡連線", 8).addStep("返回", 10);
        }

        public static SettingTask closeHighPowerAfterEmui9() {
            return new SettingTask().setTaskName("电池优化").addStep("com.android.settings/com.android.settings.Settings$HighPowerApplicationsActivity", 4).addStep("不允许|不允許", 1).addStep("所有应用|所有應用", 1).addStep("付呗", 1).addStep("不允许|不允許", 2).addStep("确定|確定", 1).addStep("返回", 10);
        }

        public static SettingTask closeEmui5PowerModeTask() {
            return new SettingTask().setTaskName("省电模式").addStep("com.huawei.systemmanager/com.huawei.systemmanager.power.ui.HwPowerManagerActivity", 4).addStep("省电模式|省電模式", 9).addStep("超级省电|超級省電", 9).addStepById("android:id/icon2", 15).addStep("休眠时保持 WLAN 连接", 7).addStep("始终", 8).addStep("取消", 7).addStep("返回", 10);
        }

        public static SettingTask ignoreEmui5HighPowerMode() {
            return new SettingTask().setTaskName("忽略电池优化").addStep("com.android.settings/com.android.settings.Settings$HighPowerApplicationsActivity", 4).addStep("允许|允許", 1).addStep("所有应用|所有應用", 1).addStep("付呗", 1).addStep("允许|允許", 2).addStep("确定|確定", 1).addStep("返回", 10);
        }

        public static SettingTask openRelativeStartUp() {
            return new SettingTask().setTaskName("关联启动").addStep("com.huawei.systemmanager/com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity", 4).addStep("应用关联启动管理|次要啟動管理", 1).addStep("付呗", 8).addStep("允许|允許", 7).addStep("返回", 10);
        }

        public static SettingTask openStartUpTaskAfterEmui5() {
            return new SettingTask().setTaskName("自启动").addStep("com.huawei.systemmanager/com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity", 4).addStep("付呗", 2).addStep("sleep", 6).addStep("付呗", 3).addStep("允许自启动|允許自動啟動", 2).addStep("允许关联启动|允許關聯啟動", 2).addStep("允许后台活动|允許背景活動", 2).addStep("确定|確定", 1).addStep("返回", 10);
        }

        public static SettingTask openEmui4StartupTask() {
            return new SettingTask().setTaskName("自启动").addStep("com.huawei.systemmanager/com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity", 4).addStep("付呗", 2).addStep("允许|允許", 7).addStep("返回", 10);
        }

    }


    public static boolean isDrawOverlays(Context context, OSUtil.ROM_TYPE romType, LinkedList<SettingTask> linkedList, boolean isOpen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            if (Settings.canDrawOverlays(context)) {
                return false;
            }
            if (isOpen) {
                linkedList.add(accessibilityBackTask());
            }
            if (romType != OSUtil.ROM_TYPE.COLOROS_ROM) {
                linkedList.add(openFloatWindowTask(context));
                return true;
            }
            linkedList.add(openMiuiFloatWindowTask(context));
            return true;
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (isOpen) {
                linkedList.add(accessibilityBackTask());
            }
            switch (romType) {
                case MIUI_ROM:
                    linkedList.add(openMiuiFloatWindowTask(context));
                    return true;
                case COLOROS_ROM:
                    linkedList.add(openColorsFloatWindowTask());
                    return true;
                default:
                    return false;
            }
        } else if (Settings.canDrawOverlays(context)) {
            return false;
        } else {
            if (isOpen) {
                linkedList.add(accessibilityBackTask());
            }
            if (romType != OSUtil.ROM_TYPE.COLOROS_ROM) {
                linkedList.add(openFloatWindowTask(context));
                return true;
            }
            linkedList.add(openMiuiFloatWindowTask(context));
            return true;
        }
    }

    private static SettingTask accessibilityBackTask() {
        return new SettingTask().setTaskName("返回").addStep("返回", 10).addStep("返回", 10);
    }

    public static SettingTask openMiuiFloatWindowTask(Context context) {
        SettingTask task = new SettingTask().setTaskName("悬浮窗权限").setTaskId(10000);
        // TODO MiUi系统单独处理
        task.addStep("显示悬浮窗|顯示浮動資訊框", 1).addStep("允许|允許", 1).addStep("返回", 10);
        return task;
    }

    public static SettingTask openColorsFloatWindowTask() {
        return new SettingTask().setTaskName("悬浮窗权限").setTaskId(10000).addStep("com.coloros.safecenter/com.coloros.privacypermissionsentry.PermissionTopActivity", 4).addStep("悬浮窗管理", 1).addStep("收钱吧", 2).addStep("返回", 10);
    }

    public static SettingTask openFloatWindowTask(Context context) {
        SettingTask task = new SettingTask().setTaskName("悬浮窗权限").setTaskId(10000);
        return task.addStep("android.settings.action.MANAGE_OVERLAY_PERMISSION", 4, (Bundle) null, Uri.parse("package:" + context.getPackageName()), true).addStep("允许显示在其他应用的上层|允许在其他应用的上层显示|允许出现在其他应用上|在其他应用上层显示|收钱吧|允許顯示在其他應用的上層|允許在其他應用的上層顯示|允許出現在其他應用上|在其他應用上層顯示", 8).addStep("返回", 10);
    }


}
