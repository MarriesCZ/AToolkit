package com.marries.atoolkit.android.extensions

import com.marries.atoolkit.android.ALog
import com.marries.atoolkit.android.TAG
import com.marries.atoolkit.common.reflect.AReflect

object Settings {

    fun getProperty(key: String, defValue: String) = try {
        AReflect.withClass("android.os.SystemProperties").construct().invoke("get", key, defValue).get()
    } catch (e: Exception) {
        defValue
    }

    fun setProperty(key: String, value: String) {
        try {
            AReflect.withClass("android.os.SystemProperties").construct().invoke("set", key, value)
        } catch (e: Exception) {
            ALog.e(TAG, "setProperty fail!", e)
        }
    }
}