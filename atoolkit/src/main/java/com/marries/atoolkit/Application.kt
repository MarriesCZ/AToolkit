@file:Suppress("unused")

package com.marries.atoolkit

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.core.content.pm.PackageInfoCompat
import com.marries.atoolkit.common.extensions.toBoolean

val application: Application get() = AppInitializer.instance

inline val packageName: String get() = application.packageName

inline val packageManager: PackageManager get() = application.packageManager

inline val appName: String
    get() = application.applicationInfo.loadLabel(packageManager).toString()

inline val packageInfo: PackageInfo
    get() = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)

inline val appVersionName: String get() = packageInfo.versionName

inline val appInfo: ApplicationInfo
    get() = packageManager.getApplicationInfo(packageName, 0)

inline val appVersionCode: Long
    get() = PackageInfoCompat.getLongVersionCode(packageInfo)

inline val isAppDebug: Boolean
    get() = (ApplicationInfo.FLAG_DEBUGGABLE and appInfo.flags).toBoolean()

inline val isSystemInDarkTheme: Boolean
    get() = application.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
