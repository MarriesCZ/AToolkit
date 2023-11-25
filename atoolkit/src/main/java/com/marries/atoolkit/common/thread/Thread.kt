package com.marries.atoolkit.common.thread

import android.os.Handler
import android.os.Looper

val mainHandler by lazy { Handler(Looper.getMainLooper()) }

fun runOnUIThread(runnable: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        runnable()
    } else {
        mainHandler.post(runnable)
    }
}

fun runOnUIThread(delayMillis: Long = 0L, runnable: () -> Unit) = mainHandler.postDelayed(runnable, delayMillis)