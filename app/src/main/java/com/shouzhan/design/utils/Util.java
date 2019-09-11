package com.shouzhan.design.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fshows.android.stark.utils.FsLogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.RequestBody;
import okio.Buffer;

/**
 * @author danbin
 * @version Utils.java, v 0.1 2019-03-26 上午12:30 danbin
 */
public class Util {

    private static final String TAG = Util.class.getSimpleName();

    /**
     * 获取常规POST请求参数
     *
     * @param body
     */
    public static String bodyToString(RequestBody body) {
        Buffer buffer = new Buffer();
        try {
            body.writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.readUtf8();
    }

    /**
     * 格式化JSONString
     */
    public static String toPrettyFormat(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String prettyJson = gson.toJson(json);
        prettyJson = prettyJson
                .replace("%2C", ",");
        return "{}".equals(prettyJson) ? "" : prettyJson;
    }

    /**
     * 打印屏幕分辨率信息
     * */
    public static void printDeviceInfo(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        FsLogUtil.error(TAG, "screenWidth: " + screenWidth);
        FsLogUtil.error(TAG, "screenHeight: " + screenHeight);
        int densityDpi = dm.densityDpi;
        float density = dm.density;
        FsLogUtil.error(TAG, "densityDpi: " + densityDpi);
        FsLogUtil.error(TAG, "density: " + density);
        float scaledDensity = dm.scaledDensity;
        FsLogUtil.error(TAG, "scaledDensity: " + scaledDensity);
//        float xdpi = dm.xdpi;
//        FsLogUtil.error(TAG, "xdpi: " + xdpi);
        Configuration config = context.getResources().getConfiguration();
        int smallestScreenWidthDp = config.smallestScreenWidthDp;
        FsLogUtil.error(TAG, "smallestScreenWidthDp: " + smallestScreenWidthDp);
        double screenInch = Math.sqrt(Math.pow(screenWidth, 2) + Math.pow(screenHeight, 2)) / densityDpi;
        FsLogUtil.error(TAG, "screenInch: " + screenInch);
    }

    /**
     * 打印控件相关信息
     *
     * @param params
     */
    public static String dumpLayoutParams(ViewGroup.LayoutParams params) {
        if (null == params) {
            return "null";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{width: ");
        if (ViewGroup.LayoutParams.MATCH_PARENT == params.width) {
            builder.append("match_parent");
        } else if (ViewGroup.LayoutParams.WRAP_CONTENT == params.width) {
            builder.append("wrap_content");
        } else {
            builder.append(params.width);
        }

        builder.append(", height: ");
        if (ViewGroup.LayoutParams.MATCH_PARENT == params.height) {
            builder.append("match_parent");
        } else if (ViewGroup.LayoutParams.WRAP_CONTENT == params.height) {
            builder.append("wrap_content");
        } else {
            builder.append(params.height);
        }
        builder.append("}");
        FsLogUtil.error(TAG, "dumpLayoutParams: " + builder.toString());
        return builder.toString();
    }


    public static void dumpTextSize(TextView textView) {
        FsLogUtil.error(TAG, "dumpTextSize: " + textView.getTextSize());
    }

    public static void formatNanoTime() {
        long now = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
        FsLogUtil.error(TAG, "formatNanoTime: " + sdf.format(new Date(now)));
    }

    public static void convertDpi() {
        final String rLine = "<dimen name=\"test_view_dp_width\">200dp</dimen>";
        final String UNITS = "sp|dip|dp|px";
        final String REGULAR = String.format("\\>[\\s]*[-]?[0-9]+[.]?[0-9]*(%s)[\\s]*\\<", UNITS);
        Pattern p = Pattern.compile(REGULAR);
        String wLine;
        FsLogUtil.error(TAG, "rLine: " + rLine);
        Matcher m = p.matcher(rLine);
        if (m.find()) {
            String g = m.group();
            FsLogUtil.error(TAG, "匹配后内容: " + g);
            String unitName = getUnitName(g, UNITS.split("[|]"));
            double value = getUnitValue(g);
            double dimen = value * 1080 / 750 / 2.5;
            String text = String.format(">%.2f%s<", dimen, unitName);
            wLine = rLine.replaceAll(REGULAR, text);
        } else {
            wLine = rLine;
        }
        FsLogUtil.error(TAG, "wLine: " + wLine);
    }

    private static String getUnitName(String value, final String[] units) {
        String name = "";
        String regular = "";
        for (int i = 0; i < units.length; i++) {
            if (i > 0) {
                regular += "|";
            }
            regular += units[i];
        }
        if (units.length > 0) {
            regular = "(" + regular;
            regular = regular + ")";
            Pattern p = Pattern.compile(regular);
            Matcher m = p.matcher(value);
            if (m.find()) {
                name = m.group();
            }
        }
        return name;
    }

    private static double getUnitValue(String text) {
        final String REG = "[-]?[0-9]+[.]?[0-9]*";
        Pattern p = Pattern.compile(REG);
        Matcher m = p.matcher(text);
        if (!m.find()) {
            throw new IllegalArgumentException("getUnitValue. must content dimen value. text: " + text);
        }
        return Double.parseDouble(m.group());
    }

}

