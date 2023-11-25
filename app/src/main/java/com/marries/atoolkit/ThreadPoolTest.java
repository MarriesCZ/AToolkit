package com.marries.atoolkit;

import com.marries.atoolkit.android.ALog;
import com.marries.atoolkit.common.thread.AThreadPool;

public class ThreadPoolTest {

    private static final String TAG = "ThreadPoolTest";

    public void testThreadPool() {
        ALog.d(TAG, "testThreadPool", "Begin", System.currentTimeMillis());
        Runnable runnable = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ALog.d(TAG, "testThreadPool", "111", System.currentTimeMillis());
        };
        for (int i = 0; i <= 10; ++i) {
            int finalI = i;
            AThreadPool.addRunnable(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ALog.d(TAG, "testThreadPool", finalI + "", System.currentTimeMillis());
            });
            AThreadPool.addRunnable(runnable);
        }
        AThreadPool.removeRunnable(runnable);
        AThreadPool.removeRunnable(runnable);
    }
}
