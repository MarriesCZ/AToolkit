package com.marries.atoolkit.android.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marries.atoolkit.android.coroutine.ACoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun ViewModel.requestMain(
    errorCode: Int = -1, errorMsg: String = "", report: Boolean = false, block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(ACoroutineExceptionHandler(errorCode, errorMsg, report)) {
    block()
}

fun ViewModel.requestIO(
    errorCode: Int = -1, errorMsg: String = "", report: Boolean = false, block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(
    Dispatchers.IO + ACoroutineExceptionHandler(
        errorCode, errorMsg, report
    )
) {
    block()
}

fun ViewModel.delayMain(
    errorCode: Int = -1,
    errorMsg: String = "",
    report: Boolean = false,
    delayTime: Long,
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(ACoroutineExceptionHandler(errorCode, errorMsg, report)) {
    withContext(Dispatchers.IO) {
        delay(delayTime)
    }
    block()
}