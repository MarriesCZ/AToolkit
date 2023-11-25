package com.marries.atoolkit.android.coroutine

import com.marries.atoolkit.android.ALog
import com.marries.atoolkit.android.TAG
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

class ACoroutineExceptionHandler(
    private val errorCode: Int, private val errorMsg: String = "", private val report: Boolean = false
) : CoroutineExceptionHandler {
    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        ALog.e(TAG, "handleException", "error: $errorCode", exception)
    }
}