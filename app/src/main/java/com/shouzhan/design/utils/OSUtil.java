package com.shouzhan.design.utils;

import android.annotation.SuppressLint;

import com.fshows.android.stark.utils.StringPool;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

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

    public static final String GET_EMUI_SYSTEM_VERSION = "ro.build.version.emui";
    public static final String GET_MIUI_SYSTEM_VERSION_CODE = "ro.miui.ui.version.code";
    public static final String GET_MIUI_SYSTEM_VERSION_NAME = "ro.miui.ui.version.name";
    public static final String GET_MIUI_SYSTEM_INTERNAL_STORAGE = "ro.miui.internal.storage";
    public static final String GET_OPPO_SYSTEM_VERSION = "ro.build.version.opporom";
    public static final String GET_VIVO_SYSTEM_DISPLAY_ID = "ro.vivo.os.build.display.id";
    public static final String GET_FLYME_SYSTEM_ICON = "persist.sys.use.flyme.icon";
    public static final String GET_FLYME_SYSTEM_SETUP_WIZARD = "ro.meizu.setupwizard.flyme";
    public static final String GET_FLYME_SYSTEM_PUBILSHED = "ro.flyme.published";
    public static final String GET_SYSTEM_DISPLAY_ID = "ro.build.display.id";

    public static final String FLYME_SUFFIX = "Flyme";
    public static final String EMUI_SUFFIX = "EmotionUI";

    public static final int VERSION_3 = 3;
    public static final int VERSION_4 = 4;
    public static final int VERSION_5 = 5;
    public static final int VERSION_9 = 9;

    public static ROM_TYPE getRomType() {
        try {
            if (StringUtils.isNotEmpty(getSystemProp(GET_EMUI_SYSTEM_VERSION))) {
                return ROM_TYPE.EMUI_ROM;
            }
            if (StringUtils.isEmpty(getSystemProp(GET_MIUI_SYSTEM_VERSION_CODE)) && StringUtils.isEmpty(getSystemProp(GET_MIUI_SYSTEM_VERSION_NAME))) {
                if (StringUtils.isEmpty(getSystemProp(GET_MIUI_SYSTEM_INTERNAL_STORAGE))) {
                    if (StringUtils.isNotEmpty(getSystemProp(GET_OPPO_SYSTEM_VERSION))) {
                        return ROM_TYPE.COLOROS_ROM;
                    }
                    if (StringUtils.isNotEmpty(getSystemProp(GET_VIVO_SYSTEM_DISPLAY_ID))) {
                        return ROM_TYPE.FUNTOUCH_ROM;
                    }
                    if (StringUtils.isEmpty(getSystemProp(GET_FLYME_SYSTEM_ICON)) && StringUtils.isEmpty(getSystemProp(GET_FLYME_SYSTEM_SETUP_WIZARD))) {
                        if (StringUtils.isEmpty(getSystemProp(GET_FLYME_SYSTEM_PUBILSHED))) {
                            String displayId = getSystemProp(GET_SYSTEM_DISPLAY_ID);
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

    public static String getSystemProp() {
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
        }
        return StringPool.EMPTY;
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

    public static String getSystemProperty(String key, String defaultValue) {
        try {
            @SuppressLint("PrivateApi")
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method method = clz.getMethod("get", String.class, String.class);
            return (String) method.invoke(clz, key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }


}
