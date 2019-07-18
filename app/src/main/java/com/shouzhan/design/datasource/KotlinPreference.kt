package com.shouzhan.design.datasource

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.shouzhan.design.App
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

const val SHARED_NAME = "default_sp"

class Preference<T>(private val key: String, private val default: T) : ReadWriteProperty<Any?, T> {
    companion object {
        val preference: SharedPreferences by lazy {
            App.getInstance().applicationContext.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        }
        lateinit var name: String
        fun clear() {
            preference.edit().clear().apply()
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getSharePreference(key, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        return putSharePreference(key, value)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getSharePreference(key: String, default: T): T = with(preference) {
        val value: Any = when (default) {
            is Long -> getLong(key, default)
            is String -> getString(key, default)
            is Int -> getInt(key, default)
            is Boolean -> getBoolean(key, default)
            is Float -> getFloat(key, default)
            else -> throw IllegalArgumentException("This type of data can not be saved! ")
        }
        value as T
    }

    @SuppressLint("CommitPrefEdits")
    private fun <T> putSharePreference(key: String, default: T) = with(preference.edit()) {
        when (default) {
            is Long -> putLong(key, default)
            is String -> putString(key, default)
            is Int -> putInt(key, default)
            is Boolean -> putBoolean(key, default)
            is Float -> putFloat(key, default)
            else -> throw IllegalArgumentException("This type of data can not be saved! ")
        }.apply()
    }
}