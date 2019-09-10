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

}

