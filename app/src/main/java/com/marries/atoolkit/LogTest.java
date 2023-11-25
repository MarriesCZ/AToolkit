package com.marries.atoolkit;

import com.marries.atoolkit.android.ALog;

public class LogTest {

    private static final String TAG = "LogTest";

    public void testLog() {
        ALog.v(TAG, "testLog");
        ALog.v(TAG, "msg", new Exception());
        ALog.v(TAG, "testLog", "key");
        ALog.v(TAG, "testLog", "key", "value");
        ALog.v(TAG, "testLog", "key1, key2, key3", new boolean[] {true, false, false});
        ALog.v(TAG, "testLog", "key1, key2, key3", new Boolean[] {true, false, false});
        ALog.v(TAG, "testLog", "key1, key2, key3", new Object[] {1, "second", true});

        ALog.d(TAG, "testLog");
        ALog.d(TAG, "msg", new Exception());
        ALog.d(TAG, "testLog", "msg");
        ALog.d(TAG, "testLog", "key", "value");
        ALog.d(TAG, "testLog", "key1, key2, key3", new boolean[] {true, true, false});
        ALog.d(TAG, "testLog", "key1, key2, key3", new Boolean[] {true, true, false});
        ALog.d(TAG, "testLog", "key1, key2, key3", new Object[] {1, "second", true});

        ALog.i(TAG, "testLog");
        ALog.i(TAG, "msg", new Exception());
        ALog.i(TAG, "testLog", "msg");
        ALog.i(TAG, "testLog", "key", true);
        ALog.i(TAG, "testLog", "key1, key2, key3", new boolean[] {true, false, false});
        ALog.i(TAG, "testLog", "key1, key2, key3", new Boolean[] {true, false, false});
        ALog.i(TAG, "testLog", "key1, key2, key3", new Object[] {1, "second", true});

        ALog.w(TAG, "testLog");
        ALog.w(TAG, "msg", new Exception());
        ALog.w(TAG, "testLog", "msg");
        ALog.w(TAG, "testLog", "key", "value");

        ALog.e(TAG, "testLog");
        ALog.e(TAG, "msg", new Exception());
        ALog.e(TAG, "testLog", "msg");
        ALog.e(TAG, "testLog", "msg", new Exception());

        ALog.wtf(TAG, "wtf");
    }

    private boolean get(boolean i, boolean j) {
        return i && j;
    }
}
