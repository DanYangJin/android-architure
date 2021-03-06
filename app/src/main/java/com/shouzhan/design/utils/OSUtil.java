package com.shouzhan.design.utils;

import android.content.Context;
import android.media.AudioManager;

import androidx.core.app.NotificationManagerCompat;

import com.fshows.android.stark.utils.StringPool;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.TestOnly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author danbin
 * @version OSUtil.java, v 0.1 2020-05-07 3:42 PM danbin
 * 获取OS信息
 */
public class OSUtil {

    public enum ROM_TYPE {
        /**
         * 华为
         */
        EMUI_ROM,
        /**
         * 小米
         */
        MIUI_ROM,
        /**
         * OPPO
         */
        COLOROS_ROM,
        /**
         * VIVO
         */
        FUNTOUCH_ROM,
        /**
         * 魅族
         */
        FLYME_ROM,
        /**
         * 其他
         */
        OTHER_ROM
    }

    public static final String FLYME_SUFFIX = "Flyme";
    public static final String EMUI_SUFFIX = "EmotionUI";

    public static final String MIUI_VERSION_6 = "V6";
    public static final String MIUI_VERSION_7 = "V7";
    public static final String MIUI_VERSION_8 = "V8";
    public static final String MIUI_VERSION_9 = "V9";

    public static final int VERSION_3 = 3;
    public static final int VERSION_4 = 4;
    public static final int VERSION_5 = 5;
    public static final int VERSION_9 = 9;


    public static ROM_TYPE getRomType() {
        try {
            if (StringUtils.isNotEmpty(getSystemProp(SysPropertyUtil.GET_EMUI_SYSTEM_VERSION))) {
                return ROM_TYPE.EMUI_ROM;
            }
            if (StringUtils.isEmpty(getSystemProp(SysPropertyUtil.GET_MIUI_SYSTEM_VERSION_CODE)) && StringUtils.isEmpty(getSystemProp(SysPropertyUtil.GET_MIUI_SYSTEM_VERSION_NAME))) {
                if (StringUtils.isEmpty(getSystemProp(SysPropertyUtil.GET_MIUI_SYSTEM_INTERNAL_STORAGE))) {
                    if (StringUtils.isNotEmpty(getSystemProp(SysPropertyUtil.GET_OPPO_SYSTEM_VERSION))) {
                        return ROM_TYPE.COLOROS_ROM;
                    }
                    if (StringUtils.isNotEmpty(getSystemProp(SysPropertyUtil.GET_VIVO_SYSTEM_DISPLAY_ID))) {
                        return ROM_TYPE.FUNTOUCH_ROM;
                    }
                    if (StringUtils.isEmpty(getSystemProp(SysPropertyUtil.GET_FLYME_SYSTEM_ICON)) && StringUtils.isEmpty(getSystemProp(SysPropertyUtil.GET_FLYME_SYSTEM_SETUP_WIZARD))) {
                        if (StringUtils.isEmpty(getSystemProp(SysPropertyUtil.GET_FLYME_SYSTEM_PUBILSHED))) {
                            String displayId = getSystemProp(SysPropertyUtil.GET_SYSTEM_DISPLAY_ID);
                            if (StringUtils.isNotEmpty(displayId)) {
                                if (displayId.contains(FLYME_SUFFIX)) {
                                    return ROM_TYPE.FLYME_ROM;
                                } else {
                                    return getRomTypeByManufacturer();
                                }
                            }
                            return ROM_TYPE.OTHER_ROM;
                        }
                    }
                    return ROM_TYPE.FLYME_ROM;
                }
            }
            return ROM_TYPE.MIUI_ROM;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ROM_TYPE.OTHER_ROM;
    }

    public static ROM_TYPE getRomTypeByManufacturer() {
        try {
            String manufacturer = android.os.Build.MANUFACTURER;
            switch (manufacturer) {
                case "HUAWEI":
                    return ROM_TYPE.EMUI_ROM;
                case "vivo":
                    return ROM_TYPE.FUNTOUCH_ROM;
                case "OPPO":
                    return ROM_TYPE.COLOROS_ROM;
                case "Xiaomi":
                    return ROM_TYPE.MIUI_ROM;
                case "Meizu":
                    return ROM_TYPE.FLYME_ROM;
                default:
                    return ROM_TYPE.OTHER_ROM;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ROM_TYPE.OTHER_ROM;
    }

    public static String getManufacturerInfo() {
        return null;
    }

    public static String getSystemProp(String str) {
        String prop;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + str);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            prop = input.readLine();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return prop;
    }

    public static boolean isFuntouchRom() {
        return getRomType() == ROM_TYPE.FUNTOUCH_ROM;
    }

    public static boolean isMiuiRom() {
        return getRomType() == ROM_TYPE.MIUI_ROM;
    }

    public static boolean isFlymeRom() {
        return getRomType() == ROM_TYPE.FLYME_ROM;
    }

    public static boolean isOtherRom() {
        return getRomType() == ROM_TYPE.OTHER_ROM;
    }

    /**
     * 获取系统版本信息
     */
    public static String getSystemVersion() {
        try {
            switch (getRomType()) {
                case EMUI_ROM:
                    return getSystemProp("ro.build.version.emui");
                case MIUI_ROM:
                    return getSystemProp("ro.miui.ui.version.name");
                case FLYME_ROM:
                    return getSystemProp("ro.build.display.id");
                case COLOROS_ROM:
                    return getSystemProp("ro.build.version.opporom");
                case FUNTOUCH_ROM:
                    return getSystemProp("ro.vivo.os.build.display.id");
                default:
                    return StringPool.EMPTY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return StringPool.EMPTY;
        }
    }

    /**
     * 判断通知是否开启
     */
    public static boolean isNotificationEnabled(Context context) {
        if (context != null) {
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        }
        return false;
    }

    /**
     * 判断是否处于静音模式
     * */
    public static boolean isMuteEnabled(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager == null || audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
            return true;
        }
        return false;
    }

    /**
     * 测试方法
     */
    @TestOnly
    public static String getPackageName() {
        return "com.ionicframework.lifecirclemerchantfront573168";
    }

}
