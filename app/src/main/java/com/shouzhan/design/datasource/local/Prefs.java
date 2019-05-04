package com.shouzhan.design.datasource.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Map;
import java.util.Set;

/**
 * @author danbin
 * @version Prefs.java, v 0.1 2019-03-25 下午2:55 danbin
 */
public class Prefs {

    private static final String TAG = Prefs.class.getSimpleName();

    private static SharedPreferences sharedPreference;

    public static SharedPreferences getInstance() {
        return sharedPreference;
    }

    public static void init(Context context) {
        sharedPreference = PreferenceManager
                .getDefaultSharedPreferences(context);
    }

    private static void checkIfNull() {
        if (sharedPreference == null) {
            Log.e(TAG, "Warning,PreferenceManager init ERROR.");
        }
    }

    public static void addBooleanPreference(String key, boolean b) {
        checkIfNull();
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(key, b);
        editor.commit();
    }

    public static void addFloatPreference(String key, float f) {
        checkIfNull();
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(key, f);
        editor.commit();
    }

    public static void addIntPreference(String key, int i) {
        checkIfNull();
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(key, i);
        editor.commit();
    }

    public static void addLongPreference(String key, long l) {
        checkIfNull();
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putLong(key, l);
        editor.commit();
    }

    public static void addStringPreference(String key, String s) {
        checkIfNull();
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(key, s);
        editor.commit();
    }

    public static void addStringSetPreference(String key, Set<String> stringSet) {
        checkIfNull();
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putStringSet(key, stringSet);
        editor.commit();
    }

    public static Object getPreference(String key, Object defaultValue) {
        checkIfNull();
        Object res = sharedPreference.getAll().get(key);
        return res == null ? defaultValue : res;
    }

    public static boolean getBooleanPreference(String key, boolean defaultValue) {
        checkIfNull();
        try {
            Object object = sharedPreference.getAll().get(key);
            if (object == null) {
                return defaultValue;
            }
            return (Boolean) object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static String getStringPreference(String key, String defaultValue) {
        checkIfNull();
        String res = null;
        try {
            res = (String) sharedPreference.getAll().get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res != null) {
            return res.toString();
        }
        return defaultValue;
    }

    public static int getIntPreference(String key, int defaultValue) {
        checkIfNull();
        try {
            Object object = sharedPreference.getAll().get(key);
            if (object == null) {
                return defaultValue;
            }
            return (Integer) object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static Map<String, ?> getAllPreference() {
        checkIfNull();
        return sharedPreference.getAll();
    }

}