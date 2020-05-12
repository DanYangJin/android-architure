package com.shouzhan.design.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.fshows.android.stark.utils.StringPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author danbin
 * @version PropertiesUtil.java, v 0.1 2020-05-07 3:42 PM danbin
 * 获取系统属性工具类
 */
public class PropertiesUtil {

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
    public static final String GET_SYSTEM_VERSION_INCREMENTAL = "ro.build.version.incremental";
    public static final String GET_SYSTEM_RO_SERIALNO = "ro.serialno";

    private static PropertiesUtil mInstance;

    private final Properties mProperties = new Properties();

    private final Pattern mPattern = Pattern.compile("\\[(.+)]: \\[(.+)]");

    public static PropertiesUtil getInstance() throws IOException {
        if (mInstance == null) {
            mInstance = new PropertiesUtil();
        }
        return mInstance;
    }

    /**
     * 获取序列号
     */
    @SuppressLint("HardwareIds")
    public static String getSerialNumber() {
        return getProperty(GET_SYSTEM_RO_SERIALNO, StringPool.EMPTY);
    }

    public static String getIncrementalVersion() {
        return getProperty(GET_SYSTEM_VERSION_INCREMENTAL, StringPool.EMPTY);
    }

    /**
     * 直接通过系统方法获取属性值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    @SuppressLint("PrivateApi")
    public static String getProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method method = clz.getMethod("get", String.class, String.class);
            return (String) method.invoke(clz, key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    private PropertiesUtil() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop").getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null && !readLine.equals(StringPool.NULL)) {
                    setProperty(readLine);
                } else {
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperty(String str) {
        if (!TextUtils.isEmpty(str)) {
            Matcher matcher = this.mPattern.matcher(str);
            if (matcher.find()) {
                this.mProperties.setProperty(matcher.group(1), matcher.group(2));
            }
        }
    }

    public String getProperty(String str) {
        return this.mProperties.getProperty(str);
    }

}
