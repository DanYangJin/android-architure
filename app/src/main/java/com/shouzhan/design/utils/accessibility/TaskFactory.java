package com.shouzhan.design.utils.accessibility;

import android.content.Context;
import android.os.Build;

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

    public static Queue<SingleTask> getSystemSettingTask(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return null;
        }
        OSUtil.ROM_TYPE romType = OSUtil.getRomType();
        LinkedList<SingleTask> linkedList = new LinkedList<>();
        switch (romType) {
            case EMUI_ROM:
                linkedList.addAll(createEmUiTask(context));
                break;
            case MIUI_ROM:
                linkedList.add(null);
                break;
            default:
                break;
        }
        return linkedList;
    }

    private static Queue<SingleTask> createEmUiTask(Context context) {
        String prop = OSUtil.getProp();
        LinkedList<SingleTask> linkedList = new LinkedList<>();
        if (StringUtils.isNotEmpty(prop)) {
            String trim = prop.replace("EmotionUI", "").replace("_", "").trim();
            if (trim.contains(StringPool.DOT)) {
                trim = trim.substring(0, trim.indexOf("."));
            }
            int intValue = Integer.parseInt(trim);
            if (intValue == 3) {
                // TODO
            } else if (intValue == 4) {
                // TODO
            } else if (intValue == 5) {
                // TODO
            } else {
                linkedList.add(EmUiTask.openStartUpTask());
                linkedList.addAll(EmUiTask.openNotificationTask());
                if (intValue < 9) {
                    linkedList.addAll(EmUiTask.openNotificationLightTask());
                    linkedList.add(EmUiTask.openNotificationLockTask());
                    linkedList.add(EmUiTask.closePowerModeTask());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        linkedList.add(EmUiTask.ignoreHighPowerMode());
                    }
                } else {
                    // TODO
                }
            }
        }
        return linkedList;
    }

    public static class EmUiTask {

        public static Queue<SingleTask> openNotificationTask() {
            LinkedList<SingleTask> linkedList = new LinkedList<>();
            SingleTask addStep = new SingleTask().setTaskName("应用和通知").addStep("com.huawei.systemmanager/com.huawei.notificationmanager.ui.NotificationManagmentActivity", 4).addStep("付呗", 1).addStep("允许通知|允許通知", 2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                addStep.addStep("横幅|橫幅", 8).addStep("允许打扰|允許打擾", 8).addStep("锁屏通知|螢幕鎖定通知", 7).addStep("显示|顯示", 8).addStep("取消", 7);
                addStep.addStep("推送通知", 13).addStep("允许通知|允許通知", 2).addStep("横幅|橫幅", 2, false).addStep("允许打扰|优先显示|允許打擾", 8).addStep("锁屏通知|螢幕鎖定通知", 1).addStep("显示|顯示", 2).addStep("取消", 7).addStep("返回", 10);
            } else {
                addStep.addStep("横幅|橫幅", 2).addStep("允许打扰|优先显示|允許打擾", 8).addStep("锁屏通知|螢幕鎖定通知", 1).addStep("显示|顯示", 2).addStep("取消", 7);
            }
            linkedList.add(addStep);
            linkedList.add(new SingleTask().setTaskName("通知设置返回").addStep("返回", 10));
            return linkedList;
        }

//        public static SingleTask m40668b() {
//            return new SingleTask().setTaskName("应用和通知").addStep("com.huawei.systemmanager/com.huawei.notificationmanager.ui.NotificationManagmentActivity", 4).addStep("付呗", 1).addStep("允许通知|允許通知", 2).addStep("横幅|橫幅", 2).addStep("在锁屏上显示|顯示於鎖定畫面", 8).addStep("锁屏时", 9).addStep("优先显示", 8).addStep("返回", 10);
//        }

//        public static SingleTask m40669c() {
//            return new SingleTask().setTaskName("通知亮屏").addStep("com.huawei.systemmanager/com.huawei.notificationmanager.ui.NotificationManagmentActivity", 4).addStep("锁屏通知|更多通知設定", 7).addStep("显示所有通知|更多通知設定", 8).addStep("取消", 7).addStep("更多通知设置|更多通知設定", 7).addStep("通知亮屏提示|通知開啟螢幕", 8, false).addStep("返回", 10);
//        }
//
//        public static Queue<SingleTask> m40670d() {
//            LinkedList linkedList = new LinkedList();
//            linkedList.add(new SingleTask().setTaskName("设置搜索返回").addStep("android.settings.SETTINGS", 4).addStepById("com.android.settings:id/back", 15).addStep("返回", 10));
//            linkedList.add(new SingleTask().setTaskName("通知亮屏").addStep("android.settings.SETTINGS", 4).addStep("滚动到顶部", 5).addStep("通知和状态栏|通知中心|通知與狀態列", 1).addStep("通知亮屏提示|通知開啟螢幕", 8, false).addStep("返回", 10).addStep("返回", 10));
//            return linkedList;
//        }

//        public static SingleTask m40671e() {
//            return new SingleTask().setTaskName("锁屏通知").addStep("com.huawei.systemmanager/com.huawei.notificationmanager.ui.NotificationManagmentActivity", 4).addStepById("android:id/icon2", 15).addStep("设备锁定时|锁屏通知|螢幕鎖定通知", 7).addStep("显示所有通知内容|显示所有通知|更多通知設定", 8).addStep("确定|確定", 7).addStep("返回", 10);
//        }

        public static SingleTask openNotificationLockTask() {
            return new SingleTask().setTaskName("锁屏通知").addStep("com.huawei.systemmanager/com.huawei.notificationmanager.ui.NotificationManagmentActivity", 4).addStepById("androidhwext:id/action_menu_more_button", 15).addStep("锁屏通知|螢幕鎖定通知", 7).addStep("显示所有通知|更多通知設定", 8).addStep("确定|確定", 7).addStep("返回", 10);
        }

        public static Queue<SingleTask> openNotificationLightTask() {
            LinkedList<SingleTask> linkedList = new LinkedList<>();
            linkedList.add(new SingleTask().setTaskName("设置搜索返回").addStep("android.settings.SETTINGS", 4).addStepById("com.android.settings:id/back", 15).addStep("返回", 10));
            linkedList.add(new SingleTask().setTaskName("通知亮屏").addStep("android.settings.SETTINGS", 4).addStep("滚动到顶部", 5).addStep("应用和通知|通知和状态栏|通知中心|通知與狀態列", 1).addStep("通知和状态栏设置", 1).addStep("通知亮屏提示|通知開啟螢幕", 8, false).addStep("返回", 10).addStep("返回", 10).addStep("返回", 10));
            return linkedList;
        }

//        public static SingleTask m40674h() {
//            return new SingleTask().setTaskName("锁屏清理").addStep("com.huawei.systemmanager/com.huawei.systemmanager.optimize.process.ProtectActivity", 4).addStep("付呗", 3).addStep("返回", 10);
//        }

//        public static SingleTask m40675i() {
//            return new SingleTask().setTaskName("受保护应用").addStep("com.huawei.systemmanager/com.huawei.systemmanager.optimize.process.ProtectActivity", 4).addStep("付呗", 2).addStep("返回", 10);
//        }

//        public static SingleTask m40676j() {
//            return new SingleTask().setTaskName("省电模式").addStep("com.huawei.systemmanager/com.huawei.systemmanager.power.ui.HwPowerManagerActivity", 4).addStep("性能模式", 9).addStep("省电模式|省電模式", 9).addStep("超级省电|超級省電", 9).addStep("普通省电|普通省電", 8).addStep("更多电池设置|更多電池設定", 7).addStep("休眠时始终保持网络连接|休眠時一律保持網絡連線", 8).addStep("返回", 10);
//        }

//        public static SingleTask m40677k() {
//            return new SingleTask().setTaskName("电池优化").addStep("com.android.settings/com.android.settings.Settings$HighPowerApplicationsActivity", 4).addStep("不允许|不允許", 1).addStep("所有应用|所有應用", 1).addStep("付呗", 1).addStep("不允许|不允許", 2).addStep("确定|確定", 1).addStep("返回", 10);
//        }

        public static SingleTask closePowerModeTask() {
            return new SingleTask().setTaskName("省电模式").addStep("com.huawei.systemmanager/com.huawei.systemmanager.power.ui.HwPowerManagerActivity", 4).addStep("省电模式|省電模式", 9).addStep("超级省电|超級省電", 9).addStepById("android:id/icon2", 15).addStep("休眠时保持 WLAN 连接", 7).addStep("始终", 8).addStep("取消", 7).addStep("返回", 10);
        }

        public static SingleTask ignoreHighPowerMode() {
            return new SingleTask().setTaskName("忽略电池优化").addStep("com.android.settings/com.android.settings.Settings$HighPowerApplicationsActivity", 4).addStep("允许|允許", 1).addStep("所有应用|所有應用", 1).addStep("付呗", 1).addStep("允许|允許", 2).addStep("确定|確定", 1).addStep("返回", 10);
        }

//        public static SingleTask m40680n() {
//            return new SingleTask().setTaskName("关联启动").addStep("com.huawei.systemmanager/com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity", 4).addStep("应用关联启动管理|次要啟動管理", 1).addStep("付呗", 8).addStep("允许|允許", 7).addStep("返回", 10);
//        }

        public static SingleTask openStartUpTask() {
            return new SingleTask().setTaskName("自启动").addStep("com.huawei.systemmanager/com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity", 4).addStep("付呗", 2).addStep("sleep", 6).addStep("付呗", 3).addStep("允许自启动|允許自動啟動", 2).addStep("允许关联启动|允許關聯啟動", 2).addStep("允许后台活动|允許背景活動", 2).addStep("确定|確定", 1).addStep("返回", 10);
        }

//        public static SingleTask m40682p() {
//            return new SingleTask().setTaskName("自启动").addStep("com.huawei.systemmanager/com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity", 4).addStep("付呗", 2).addStep("允许|允許", 7).addStep("返回", 10);
//        }
    }

}
