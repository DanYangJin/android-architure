package com.shouzhan.design.utils.accessibility;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.fshows.android.stark.utils.CommonRegex;
import com.fshows.android.stark.utils.FsLogUtil;
import com.fshows.android.stark.utils.StringPool;
import com.fshows.android.stark.utils.StringUtil;
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
                linkedList.addAll(createEmuiTask(context));
                break;
            case MIUI_ROM:
                linkedList.addAll(createMiuiTask(context));
                break;
            case COLOROS_ROM:
                linkedList.addAll(createColorsTask(context));
                break;
            default:
                break;
        }
        return linkedList;
    }

    private static Queue<SettingTask> createEmuiTask(Context context) {
        String emuiVerion = OSUtil.getSystemVersion();
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, "createEmuiTask: " + emuiVerion);
        LinkedList<SettingTask> linkedList = new LinkedList<>();
        if (StringUtils.isNotEmpty(emuiVerion)) {
            String trim = emuiVerion.replace(OSUtil.EMUI_SUFFIX, StringPool.EMPTY).replace("_", "").trim();
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

    private static Queue<SettingTask> createMiuiTask(Context context) {
        float floatValue;
        LinkedList<SettingTask> linkedList = new LinkedList<>();
        linkedList.add(new SettingTask().setTaskName("自启动").addStep("miui.intent.action.OP_AUTO_START", 4).addStep("滚动到顶部", 5).addStep("sleep", 6).addStep("付呗", 1).addStep("允许系统唤醒|允許系統喚醒", 2).addStep("允许被其他应用唤醒|允許被其他應用喚醒", 2).addStep("返回", 10));
        SettingTask taskName = new SettingTask().setTaskName("通知设置");
        taskName.addStep("android.settings.APPLICATION_DETAILS_SETTINGS", 4, Uri.parse("package:" + OSUtil.getPackageName())).addStep("通知管理|通知管理", 1).addStep("允许通知|允許通知", 2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            taskName.addStep("悬浮通知|懸浮通知", 8).addStep("锁屏通知|螢幕鎖定時通知", 8);
            taskName.addStep("推送通知", 13).addStep("允许通知|允許通知", 2).addStep("悬浮通知|懸浮通知", 8).addStep("锁屏通知|螢幕鎖定時通知", 8).addStep("在锁定屏幕上|在螢幕鎖定畫面上", 7).addStep("显示通知及其内容|顯示通知及內容", 7).addStep("返回", 10);
            linkedList.add(taskName);
            linkedList.add(new SettingTask().setTaskName("通知设置返回").addStep("返回", 10).addStep("返回", 10));
        } else {
            taskName.addStep("优先|優先", 2).addStep("悬浮通知|懸浮通知", 2).addStep("锁屏通知|螢幕鎖定時通知", 2).addStep("返回", 10).addStep("返回", 10);
            linkedList.add(taskName);
        }
        linkedList.add(new SettingTask().setTaskName("锁屏断开数据/锁屏清理内存").addStep("com.miui.securitycenter/com.miui.powercenter.PowerSettings", 4).addStep("锁屏后断开数据|锁屏断开数据|鎖螢幕後斷開數據|鎖屏斷開行動數據", 1).addStep("从不|從不", 1).addStep("锁屏后清理内存|锁屏清理内存|鎖螢幕後清理內存|鎖屏清理記憶體", 1).addStep("从不|從不", 1).addStep("返回", 10));
        linkedList.add(new SettingTask().setTaskName("设置搜索返回").addStep("android.settings.SETTINGS", 4).addStepById("miui:id/search_btn_cancel|miui:id/search_text_cancel", 15).addStep("返回", 10));
        String incremental = OSUtil.getSystemIncrementalVersion();
        float f = 0.0f;
        try {
            if (CommonRegex.isNumber(String.valueOf(incremental.charAt(0)))) {
                floatValue = Float.parseFloat(incremental.substring(0, StringUtil.findPositionIndex(incremental,
                        StringPool.DOT, 2)));
            } else {
                floatValue = Float.parseFloat(incremental.substring(1, StringUtil.findPositionIndex(incremental,
                        StringPool.DOT, 2)));
            }
            f = floatValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, "floatValue: " + f);
        SettingTask addStep = new SettingTask().setTaskName("通知亮屏").addStep("android.settings.SETTINGS", 4).addStep("滚动到顶部", 5);
        double d = f;
        if (d < 11.0d) {
            addStep.addStep("锁屏、密码和指纹|鎖屏、密碼和指紋", 1).addStep("锁屏来通知时亮屏|通知來時螢幕亮起", 8, false).addStep("锁屏高级设置|螢幕鎖進階設定", 7).addStep("在锁定屏幕上|屏幕锁定时|在螢幕鎖定畫面上|萤幕锁定时", 7).addStep("显示通知及其内容|顯示通知及內容", 7).addStep("返回", 10).addStep("返回", 10).addStep("返回", 10);
        }
        linkedList.add(addStep);
        linkedList.add(new SettingTask().setTaskName("省电模式").addStep("com.miui.securitycenter/com.miui.powercenter.PowerCenter", 4).addStep("省电模式|省電模式", 7).addStep("省电模式|省電模式", 9).addStep("返回", 10));
        linkedList.add(new SettingTask().setTaskName("省电策略").addStep("com.miui.powerkeeper/com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity", 4).addStep("滚动到顶部", 5).addStep("sleep", 6).addStep("付呗", 1).addStep("省电策略|省電策略", 7).addStep("无限制|無限制", 1).addStep("返回", 10));
        if (d > 10.0d) {
            SettingTask addStep2 = new SettingTask().setTaskName("省电模式").addStep("android.settings.SETTINGS", 4).addStep("滚动到顶部", 5);
            if (d >= 11.0d) {
                addStep2.addStep("电池与性能", 1).addStep("省电模式|省電模式", 9).addStep("超级省电|超級省電", 9).addStep("返回", 10).addStep("返回", 10);
            } else {
                addStep2.addStep("电量和性能|電量和性能", 1).addStep("省电优化|省電最佳化", 7).addStep("省电模式|省電模式", 9).addStep("返回", 10).addStep("返回", 10).addStep("返回", 10);
            }
            linkedList.add(addStep2);
            linkedList.add(new SettingTask().setTaskName("电池优化").addStep("android.settings.SETTINGS", 4).addStep("滚动到顶部", 5).addStep(d >= 11.0d ? "密码、隐私与安全" : "更多设置|更多設定", 1).addStep("系统安全|系統安全", 1).addStep("特殊应用权限|特殊應用程式存取權", 13, false).addStep("电池优化|電池效能最佳化", 7).addStep("未优化|未套用最佳化設定", 1).addStep("所有应用|所有應用程式", 1).addStep("付呗", 1).addStep("不优化|不要最佳化", 1).addStep("完成", 1).addStep("返回", 10).addStep("返回", 10));
            linkedList.add(new SettingTask().setTaskName("电池优化返回").addStep("返回", 10).addStep("返回", 10).addStep("返回", 10));
        }
        linkedList.add(new SettingTask().setTaskName("后台锁定").addStep("com.miui.securitycenter/com.miui.securityscan.ui.settings.SettingsActivity", 4).addStep("优化加速|優化加速", 1).addStep("锁定任务|鎖定任務", 1).addStep("付呗", 2).addStep("返回", 10).addStep("返回", 10));
        return linkedList;
    }

    /**
     * TODO bugfix耗电保护/应用速冻
     * */
    private static Queue<SettingTask> createColorsTask(Context context) {
        LinkedList<SettingTask> linkedList = new LinkedList<>();
        linkedList.add(new SettingTask().setTaskName("耗电保护").addStep("com.coloros.oppoguardelf/com.coloros.powermanager.fuelgaue.PowerConsumptionActivity", 4).addStep("省电|低电量模式|省電|低電量模式", 9).addStep("智能耗电保护｜智能耗電保護", 9, false).addStep("自定义耗电保护|自定義耗電保護", 13).addStep("付呗", 1).addStep("允许后台运行|允許後台運行", 2, true, true).addStep("返回", 10).addStep("返回", 10));
//        linkedList.add(new SettingTask().setTaskName("耗电保护").addStep("com.coloros.oppoguardelf/com.coloros.powermanager.fuelgaue.PowerConsumptionActivity", 4).addStep("耗电保护|其他|電力保護", 1).addStep("付呗", 1).addStep("后台冻结|背景凍結|後台凍結", 3).addStep("异常耗电自动优化|检测到异常时自动优化|異常耗電自動最佳化|檢測到異常時，自動最佳化", 3).addStep("深度睡眠", 9).addStep("返回", 10).addStep("返回", 10));
//        linkedList.add(new SettingTask().setTaskName("耗电保护").addStep("android.settings.SETTINGS", 4).addStep("电池|電池", 1).addStep("省电|低电量模式|省電|低電量模式", 9).addStep("智能耗电保护|智能耗電保護|智慧電力保護", 9, null, null, false).addStep("自定义耗电保护|自定義耗電保護", 13).addStep("付呗", 1).addStep("允许后台运行|允許後台運行", 2, true, true).addStep("返回", 10).addStep("返回", 10).addStep("返回", 10).addStep("返回", 10));
        linkedList.add(new SettingTask().setTaskName("应用速冻").addStep("com.coloros.oppoguardelf/com.coloros.powermanager.fuelgaue.PowerConsumptionActivity", 4).addStep("应用速冻|應用速凍", 13).addStep("付呗", 3).addStep("返回", 10));
//        linkedList.add(new SettingTask().setTaskName("应用速冻").addStep("android.settings.SETTINGS", 4).addStep("电池|電池", 1).addStep("应用速冻|應用速凍", 13).addStep("付呗", 3).addStep("返回", 10).addStep("返回", 10).addStep("返回", 10));
        SettingTask addStep = new SettingTask().setTaskName("通知管理").addStep("android.settings.SETTINGS", 4).addStep("通知与状态栏|通知與狀態欄", 1).addStep("来锁屏通知时点亮屏幕|出現鎖定螢幕通知時，螢幕亮起", 8).addStep("通知管理", 1).addStep("付呗", 1).addStep("允许通知|允許通知", 2).addStep("在锁屏上显示|顯示於鎖定畫面", 8).addStep("在屏幕顶部显示|顯示在熒幕頂部", 8).addStep("优先打扰", 8);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            addStep.addStep("推送通知", 13).addStep("允许通知|允許通知", 2).addStep("在屏幕顶部显示|顯示在熒幕頂部", 8).addStep("勿扰时允许提醒", 8).addStep("返回", 10);
            linkedList.add(addStep);
            linkedList.add(new SettingTask().setTaskName("通知设置返回").addStep("返回", 10).addStep("返回", 10).addStep("返回", 10).addStep("返回", 10));
        } else {
            addStep.addStep("返回", 10).addStep("返回", 10).addStep("返回", 10).addStep("返回", 10);
            linkedList.add(addStep);
        }
        linkedList.add(new SettingTask().setTaskName("自启动").addStep("com.coloros.safecenter/com.coloros.privacypermissionsentry.PermissionTopActivity", 4).addStep("自启动管理|自動啟動管理", 1).addStep("付呗", 2).addStep("返回", 10));
        linkedList.add(new SettingTask().setTaskName("关联启动").addStep("com.coloros.safecenter/com.coloros.privacypermissionsentry.PermissionTopActivity", 4).addStep("关联启动管理|關聯啟動管理", 13).addStep("付呗", 2).addStep("返回", 10));
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
            switch (romType) {
                case COLOROS_ROM:
                    linkedList.add(openColorsFloatWindowTask(context));
                    break;
                case MIUI_ROM:
                    linkedList.add(openMiuiFloatWindowTask(context));
                    break;
                default:
                    linkedList.add(openFloatWindowTask(context));
                    break;
            }
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
                    linkedList.add(openColorsFloatWindowTask(context));
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
            switch (romType) {
                case COLOROS_ROM:
                    linkedList.add(openColorsFloatWindowTask(context));
                    break;
                case MIUI_ROM:
                    linkedList.add(openMiuiFloatWindowTask(context));
                    break;
                default:
                    linkedList.add(openFloatWindowTask(context));
                    break;
            }
            return true;
        }
    }

    private static SettingTask accessibilityBackTask() {
        return new SettingTask().setTaskName("返回").addStep("返回", 10).addStep("返回", 10);
    }

    public static SettingTask openMiuiFloatWindowTask(Context context) {
        SettingTask task = new SettingTask().setTaskName("悬浮窗权限").setTaskId(10000);
        openMiuiPermissionsEditorTask(context, task);
        task.addStep("显示悬浮窗|顯示浮動資訊框", 1).addStep("允许|允許", 1).addStep("返回", 10);
        return task;
    }

    /**
     * TODO bugfix把"例子"修改为付呗
     * */
    public static SettingTask openColorsFloatWindowTask(Context context) {
        return new SettingTask().setTaskName("悬浮窗权限").setTaskId(10000).addStep("com.coloros.safecenter/com.coloros.privacypermissionsentry.PermissionTopActivity", 4).addStep("悬浮窗管理", 1).addStep("例子", 2).addStep("返回", 10);
    }

    public static SettingTask openFloatWindowTask(Context context) {
        SettingTask task = new SettingTask().setTaskName("悬浮窗权限").setTaskId(10000);
        return task.addStep("android.settings.action.MANAGE_OVERLAY_PERMISSION", 4, null, Uri.parse("package:" + context.getPackageName()), true).addStep("允许显示在其他应用的上层|允许在其他应用的上层显示|允许出现在其他应用上|在其他应用上层显示|付呗|允許顯示在其他應用的上層|允許在其他應用的上層顯示|允許出現在其他應用上|在其他應用上層顯示", 8).addStep("返回", 10);
    }

    private static void openMiuiPermissionsEditorTask(Context context, SettingTask task) {
        Bundle bundle = new Bundle();
        bundle.putString("extra_pkgname", context.getPackageName());
        String prop = OSUtil.getSystemProp(OSUtil.GET_MIUI_SYSTEM_VERSION_NAME);
        if (StringUtils.isNotEmpty(prop)) {
            if (prop.equals(OSUtil.MIUI_VERSION_6) || prop.equals(OSUtil.MIUI_VERSION_7)) {
                task.addStep("com.miui.securitycenter/com.miui.permcenter.permissions.AppPermissionsEditorActivity", 4, bundle);
            } else {
                task.addStep("miui.intent.action.APP_PERM_EDITOR", 4, bundle);
            }
        } else {
            task.addStep("miui.intent.action.APP_PERM_EDITOR", 4, bundle);
        }
    }

}
