package com.marries.atoolkit;

import android.app.Application;
import com.marries.atoolkit.android.ALog;
import com.marries.atoolkit.android.LogLevel;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ALog.init("TS_", ApplicationKt.isAppDebug() ? LogLevel.VERBOSE : LogLevel.DEBUG);
    }
}
