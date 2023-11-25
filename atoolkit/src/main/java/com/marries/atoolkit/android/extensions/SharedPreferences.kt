package com.marries.atoolkit.android.extensions

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

inline fun <reified T> SharedPreferences.get(key: String, defaultVal: T) = when (defaultVal) {
    is Int -> getInt(key, defaultVal) as T
    is Long -> getLong(key, defaultVal) as T
    is Float -> getFloat(key, defaultVal) as T
    is String -> getString(key, defaultVal) as T
    is Boolean -> getBoolean(key, defaultVal) as T
    else -> throw IllegalArgumentException("Unrecognized default value $defaultVal")
}

inline fun <reified T> SharedPreferences.put(key: String, value: T) = edit().put(key, value).commit()

inline fun <reified T> Editor.put(key: String, value: T): Editor = when (value) {
    is Int -> putInt(key, value)
    is Long -> putLong(key, value)
    is Float -> putFloat(key, value)
    is String -> putString(key, value)
    is Boolean -> putBoolean(key, value)
    else -> throw UnsupportedOperationException("Unrecognized value $value")
}