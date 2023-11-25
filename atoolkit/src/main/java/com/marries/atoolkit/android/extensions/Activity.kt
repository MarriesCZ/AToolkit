package com.marries.atoolkit.android.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.marries.atoolkit.android.coroutine.ACoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun AppCompatActivity.requestMain(
    errorCode: Int = -1, errorMsg: String = "", report: Boolean = false, block: suspend CoroutineScope.() -> Unit
) = lifecycleScope.launch(ACoroutineExceptionHandler(errorCode, errorMsg, report)) {
    block()
}

fun AppCompatActivity.requestIO(
    errorCode: Int = -1, errorMsg: String = "", report: Boolean = false, block: suspend CoroutineScope.() -> Unit
) = lifecycleScope.launch(Dispatchers.IO + ACoroutineExceptionHandler(errorCode, errorMsg, report)) {
    block()
}

fun AppCompatActivity.delayMain(
    delayTime: Long,
    errorCode: Int = -1,
    errorMsg: String = "",
    report: Boolean = false,
    block: suspend CoroutineScope.() -> Unit
) = lifecycleScope.launch(ACoroutineExceptionHandler(errorCode, errorMsg, report)) {
    withContext(Dispatchers.IO) {
        delay(delayTime)
    }
    block()
}