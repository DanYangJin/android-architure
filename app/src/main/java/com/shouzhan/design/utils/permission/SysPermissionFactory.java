package com.shouzhan.design.utils.permission;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;

import com.shouzhan.design.utils.OSUtil;
import com.shouzhan.design.utils.accessibility.AudioSettingConstants;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danbin
 * @version SysPermissionFactory.java, v 0.1 2020-05-12 10:55 AM danbin
 * 系统白名单工具类
 */
public class SysPermissionFactory {

    public static List<SysPermission> getSysPermissions(Context context) {
        ArrayList<SysPermission> arrayList = new ArrayList<>();
        arrayList.add(sysNotificationPermission(context));
        switch (OSUtil.getRomType()) {
            case EMUI_ROM:
                arrayList.addAll(createEmuiPermission());
                break;
            case MIUI_ROM:
                arrayList.addAll(createMiuiPermission(context));
                break;
            case COLOROS_ROM:
                arrayList.addAll(createColorsPermission());
                break;
            case FUNTOUCH_ROM:
                arrayList.addAll(createFuntouchPermission());
                break;
            default:
                break;
        }
        ignoreBatteryOptimization(context, arrayList);
        return filterSysPermission(context, arrayList);
    }

    public static SysPermission sysNotificationPermission(Context context) {
        SysPermission sysPermission = new SysPermission().setTitle("允许系统通知").setSubTitle("请调整为\"开启\"状态");
        if (OSUtil.isNotificationEnabled(context)) {
            sysPermission.setStatus("已开启");
        } else {
            sysPermission.setStatus("去开启");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sysPermission.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            Bundle bundle = new Bundle();
            bundle.putString("android.provider.extra.APP_PACKAGE", context.getPackageName());
            bundle.putInt("android.provider.extra.CHANNEL_ID", context.getApplicationInfo().uid);
            sysPermission.setParams(bundle);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sysPermission.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            Bundle bundle2 = new Bundle();
            bundle2.putString("app_package", context.getPackageName());
            bundle2.putInt("app_uid", context.getApplicationInfo().uid);
            sysPermission.setParams(bundle2);
        } else {
            sysPermission.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            sysPermission.setCategory("android.intent.category.DEFAULT");
            sysPermission.setData(Uri.parse("package:" + context.getPackageName()));
        }
        return sysPermission;
    }

    public static void ignoreBatteryOptimization(Context context, List<SysPermission> list) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !((PowerManager) context.getSystemService(Context.POWER_SERVICE)).isIgnoringBatteryOptimizations(context.getPackageName())) {
            SysPermission sysPermission = new SysPermission().setTitle("电池优化").setSubTitle("请调整为\"忽略\"状态").setAction("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
            list.add(sysPermission.setData(Uri.parse("package:" + context.getPackageName())));
        }
    }

    private static List<SysPermission> createEmuiPermission() {
        ArrayList<SysPermission> arrayList = new ArrayList<>();
        arrayList.add(new SysPermission().setTitle("\"付呗\"自启动").setSubTitle("请调整为\"允许\"状态").setPackageName("com.huawei.systemmanager").setClassName("com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"));
        arrayList.add(new SysPermission().setTitle("手机省电模式").setSubTitle("请调整为\"关闭\"状态").setPackageName("com.huawei.systemmanager").setClassName("com.huawei.systemmanager.power.ui.HwPowerManagerActivity"));
        arrayList.add(new SysPermission().setTitle("锁屏清理").setSubTitle("请调整为\"不清理\"状态").setPackageName("com.huawei.systemmanager").setClassName("com.huawei.systemmanager.optimize.process.ProtectActivity"));
        return arrayList;
    }

    private static List<SysPermission> createMiuiPermission(Context context) {
        ArrayList<SysPermission> arrayList = new ArrayList<>();
        arrayList.add(new SysPermission().setTitle("\"付呗\"自启动").setSubTitle("请调整为\"允许\"状态").setPackageName("com.miui.securitycenter").setClassName("com.miui.permcenter.autostart.AutoStartManagementActivity"));
        Bundle bundle = new Bundle();
        bundle.putString(AudioSettingConstants.PACKAGE_NAME, context.getPackageName());
        arrayList.add(new SysPermission().setTitle("手机省电策略").setSubTitle("请调整为\"无限制\"状态").setPackageName("com.miui.powerkeeper").setClassName("com.miui.powerkeeper.ui.HiddenAppsConfigActivity").setParams(bundle));
        return arrayList;
    }

    private static List<SysPermission> createColorsPermission() {
        ArrayList<SysPermission> arrayList = new ArrayList<>();
        String colorsVersion = OSUtil.getSystemVersion();
        boolean isHighVersion = false;
        if (StringUtils.isNotEmpty(colorsVersion) && colorsVersion.length() > 2) {
            try {
                if (Integer.parseInt(colorsVersion.substring(1, 2)) >= 3) {
                    isHighVersion = true;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (isHighVersion) {
            arrayList.add(new SysPermission().setTitle("\"付呗\"自启动").setSubTitle("请调整为\"允许\"状态").setPackageName("com.coloros.safecenter").setClassName("com.coloros.safecenter.startupapp.StartupAppListActivity"));
            arrayList.add(new SysPermission().setTitle("手机省电模式").setSubTitle("请调整为\"关闭\"状态").setPackageName("com.coloros.oppoguardelf").setClassName("com.coloros.powermanager.fuelgaue.PowerConsumptionActivity"));
            arrayList.add(new SysPermission().setTitle("手机耗电保护").setSubTitle("请调整为\"关闭\"状态").setPackageName("com.coloros.oppoguardelf").setClassName("com.coloros.powermanager.fuelgaue.PowerUsageModelActivity"));
        } else {
            arrayList.add(new SysPermission().setTitle("\"付呗\"自启动").setSubTitle("请调整为\"允许\"状态").setPackageName("com.color.safecenter").setClassName("com.color.safecenter.permission.startup.StartupAppListActivity"));
            arrayList.add(new SysPermission().setTitle("手机省电模式").setSubTitle("请调整为\"关闭\"状态").setPackageName("com.color.safecenter").setClassName("com.color.powermanager.PowerMgrActivity"));
            arrayList.add(new SysPermission().setTitle("手机耗电保护").setSubTitle("请调整为\"关闭\"状态").setPackageName("com.coloros.oppoguardelf").setClassName("com.coloros.oppoguardelf.MonitoredPkgActivity"));
        }
        return arrayList;
    }

    private static List<SysPermission> createFuntouchPermission() {
        ArrayList<SysPermission> arrayList = new ArrayList<>();
        arrayList.add(new SysPermission().setTitle("\"付呗\"自启动").setSubTitle("请调整为\"允许\"状态").setPackageName("com.vivo.permissionmanager").setClassName("com.vivo.permissionmanager.activity.PurviewTabActivity"));
        arrayList.add(new SysPermission().setTitle("手机高耗电").setSubTitle("请调整为\"允许\"状态").setPackageName("com.iqoo.powersaving").setClassName("com.iqoo.powersaving.PowerSavingManagerActivity"));
        arrayList.add(new SysPermission().setTitle("手机勿扰模式").setSubTitle("请调整为\"关闭\"状态").setPackageName("com.android.settings").setClassName("com.android.settings.Settings$VivoZenModeSettingsActivity"));
        arrayList.add(new SysPermission().setTitle("\"付呗\"通知锁屏显示").setSubTitle("请调整为\"允许\"状态").setPackageName("com.android.systemui").setClassName("com.vivo.systemui.statusbar.notification.settings.StatusbarSettingActivity"));
        return arrayList;
    }

    private static List<SysPermission> filterSysPermission(Context context, List<SysPermission> list) {
        ArrayList<SysPermission> arrayList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            PackageManager packageManager = context.getPackageManager();
            for (SysPermission next : list) {
                if (isIntentExist(packageManager, next)) {
                    arrayList.add(next);
                }
            }
        }
        return arrayList;
    }

    private static boolean isIntentExist(PackageManager packageManager, SysPermission sysPermission) {
        return getIntent(sysPermission).resolveActivityInfo(packageManager, PackageManager.MATCH_DEFAULT_ONLY) != null;
    }

    public static Intent getIntent(SysPermission sysPermission) {
        Intent intent = new Intent();
        if (StringUtils.isNotEmpty(sysPermission.getAction())) {
            intent.setAction(sysPermission.getAction());
        }
        if (StringUtils.isNotEmpty(sysPermission.getCategory())) {
            intent.addCategory(sysPermission.getCategory());
        }
        if (StringUtils.isNotEmpty(sysPermission.getPackageName()) && StringUtils.isNotEmpty(sysPermission.getClassName())) {
            intent.setComponent(new ComponentName(sysPermission.getPackageName(),
                    sysPermission.getClassName()));
        }
        if (sysPermission.getParams() != null) {
            intent.putExtras(sysPermission.getParams());
        }
        if (sysPermission.getData() != null) {
            intent.setData(sysPermission.getData());
        }
        return intent;
    }


}
