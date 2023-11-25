package com.marries.atoolkit.android

import android.util.Log
import com.marries.atoolkit.common.extensions.limitLength
import com.marries.atoolkit.common.extensions.toInt

val Any.TAG: String get() = javaClass.simpleName.limitLength(23)

/**
 * A wrapper class for Log!
 *
 * @author dexiang.deng
 * @since 2021/8/15
 */
object ALog {

    private var logPrinter: LogPrinter = ALogPrinter()

    /**
     * 初始化方法，设置 TAG 前的标识以及输出 Log 的等级
     *
     * @param markTag 输出 Log 的 TAG 前显示的标识，如：传入“TS_”, TAG 会显示为 "TS_TAG"
     * @param limitLevel 输出 Log 的等级限制，如：传入 LogLevel.DEBUG, 则不会输出 ALog.v
     */
    @JvmStatic
    fun init(markTag: String, limitLevel: LogLevel) {
        init(ALogPrinter(markTag, limitLevel))
    }

    /**
     * 使用自定义的 LogPrinter 来初始化方法
     *
     * @param logPrinter 继承 LogPrinter 的类
     */
    @JvmStatic
    fun init(logPrinter: LogPrinter) {
        this.logPrinter = logPrinter
    }

    /**
     * log V print only in the debug state, print a large number of logs.
     * It will print: tag: methodName: message: value
     *
     * @param tag Used to identify the source of a log message.
     * @param methodName The name of the method where the log is.
     * @param message The message you would like logged.
     * @param value Maybe you need print some notice message or object's status.
     */
    @JvmStatic
    fun v(tag: String, methodName: String, message: String, value: Any) {
        v(tag, methodName, concat(message, value))
    }

    /**
     * log V print only in the debug state, print a large number of logs.
     * It will print: tag: methodName: message: value[0]|value[1]|value[2]|...
     *
     * @param tag Used to identify the source of a log message.
     * @param methodName The name of the method where the log is.
     * @param message The message you would like logged.
     * @param value Maybe you need print some notice message or object's status.
     */
    @JvmStatic
    fun v(tag: String, methodName: String, message: String, value: Array<Any>?) {
        v(tag, methodName, concat(message, value?.contentToString() ?: ""))
    }

    /**
     * log V print only in the debug state, print a large number of logs.
     * It will print: tag: methodName: message: 101010101...
     *
     * @param tag Used to identify the source of a log message.
     * @param methodName The name of the method where the log is.
     * @param message The message you would like logged.
     * @param value Maybe you need print some notice message or object's status.
     */
    @JvmStatic
    fun v(tag: String, methodName: String, message: String, value: BooleanArray?) {
        v(tag, methodName, concat(message, booleansToBinary(value)))
    }

    /**
     * log V print only in the debug state, print a large number of logs.
     * It will print: tag: methodName: message
     *
     * @param tag Used to identify the source of a log message.
     * @param methodName The name of the method where the log is.
     * @param message The message you would like logged.
     */
    @JvmStatic
    fun v(tag: String, methodName: String, message: String) {
        v(tag, concatMethod(methodName, message))
    }

    /**
     * log V print only in the debug state, print a large number of logs.
     * It will print: tag: message
     *
     * @param tag Used to identify the source of a log message.
     * @param message The message you would like logged.
     */
    @JvmStatic
    fun v(tag: String, message: String) {
        logPrinter.log(LogLevel.VERBOSE, tag, message)
    }

    /**
     * log V print only in the debug state, print a large number of logs.
     * It will print: tag: message '\n' throwable
     *
     * @param tag Used to identify the source of a log message.
     * @param message The message you would like logged.
     * @param throwable An exception to log.
     */
    @JvmStatic
    fun v(tag: String, message: String, throwable: Throwable) {
        logPrinter.log(LogLevel.VERBOSE, tag, message, throwable)
    }

    /**
     * log D print some message, which not so important, but maybe necessary in some time.
     * It will print: tag: methodName: message: value
     *
     * @param tag Used to identify the source of a log message.
     * @param methodName The name of the method where the log is.
     * @param message The message you would like logged.
     * @param value Maybe you need print some notice message or object's status.
     */
    @JvmStatic
    fun d(tag: String, methodName: String, message: String, value: Any) {
        d(tag, methodName, concat(message, value))
    }

    /**
     * log D print some message, which not so important, but maybe necessary in some time.
     * It will print: tag: methodName: message: value[0]|value[1]|value[2]|...
     *
     * @param tag Used to identify the source of a log message.
     * @param methodName The name of the method where the log is.
     * @param message The message you would like logged.
     * @param value Maybe you need print some notice message or object's status.
     */
    @JvmStatic
    fun d(tag: String, methodName: String, message: String, value: Array<Any>?) {
        d(tag, methodName, concat(message, value?.contentToString() ?: ""))
    }

    /**
     * log D print some message, which not so important, but maybe necessary in some time.
     * It will print: tag: methodName: message: 101010101...
     *
     * @param tag Used to identify the source of a log message.
     * @param methodName The name of the method where the log is.
     * @param message The message you would like logged.
     * @param value Maybe you need print some notice message or object's status.
     */
    @JvmStatic
    fun d(tag: String, methodName: String, message: String, value: BooleanArray?) {
        d(tag, methodName, concat(message, booleansToBinary(value)))
    }

    /**
     * log D print some message, which not so important, but maybe necessary in some time.
     * It will print: tag: methodName: message
     *
     * @param tag Used to identify the source of a log message.
     * @param methodName The name of the method where the log is.
     * @param message The message you would like logged.
     */
    @JvmStatic
    fun d(tag: String, methodName: String, message: String) {
        d(tag, concatMethod(methodName, message))
    }

    /**
     * log D print some message, which not so important, but maybe necessary in some time.
     * It will print: tag: message
     *
     * @param tag Used to identify the source of a log message.
     * @param message The message you would like logged.
     */
    @JvmStatic
    fun d(tag: String, message: String) {
        logPrinter.log(LogLevel.DEBUG, tag, message)
    }

    /**
     * log D print some message, which not so important, but maybe necessary in some time.
     * It will print: tag: message '\n' throwable
     *
     * @param tag Used to identify the source of a log message.
     * @param message The message you would like logged.
     * @param throwable An exception to log.
     */
    @JvmStatic
    fun d(tag: String, message: String, throwable: Throwable) {
        logPrinter.log(LogLevel.DEBUG, tag, message, throwable)
    }

    /**
     * log I print some important message, show some key nodes and states.
     * It will print: tag: methodName: message: value
     *
     * @param tag Used to identify the source of a log message.
     * @param methodName The name of the method where the log is.
     * @param message The message you would like logged.
     * @param value Maybe you need print some notice message or object's status.
     */
    @JvmStatic
    fun i(tag: String, methodName: String, message: String, value: Any) {
        i(tag, methodName, concat(message, value))
    }

    /**
     * log I print some important message, show some key nodes and states.
     * It will print: tag: methodName: message: value[0]|value[1]|value[2]|...
     *
     * @param tag Used to identify the source of a log message.
     * @param methodName The name of the method where the log is.
     * @param message The message you would like logged.
     * @param value Maybe you need print some notice message or object's status.
     */
    @JvmStatic
    fun i(tag: String, methodName: String, message: String, value: Array<Any>?) {
        i(tag, methodName, concat(message, value?.contentToString() ?: ""))
    }

    /**
     * log I print some important message, show some key nodes and states.
     * It will print: tag: methodName: message: 101010101...
     *
     * @param tag Used to identify the source of a log message.
     * @param methodName The name of the method where the log is.
     * @param message The message you would like logged.
     * @param value Maybe you need print some notice message or object's status.
     */
    @JvmStatic
    fun i(tag: String, methodName: String, message: String, value: BooleanArray?) {
        i(tag, methodName, concat(message, booleansToBinary(value)))
    }

    /**
     * log I print some important message, show some key nodes and states.
     * It will print: tag: methodName: message
     *
     * @param tag Used to identify the source of a log message.
     * @param methodName The name of the method where the log is.
     * @param message The message you would like logged.
     */
    @JvmStatic
    fun i(tag: String, methodName: String, message: String) {
        i(tag, concatMethod(methodName, message))
    }

    /**
     * log I print some important message, show some key nodes and states.
     * It will print: tag: message
     *
     * @param tag Used to identify the source of a log message.
     * @param message The message you would like logged.
     */
    @JvmStatic
    fun i(tag: String, message: String) {
        logPrinter.log(LogLevel.INFO, tag, message)
    }

    /**
     * log I print some important message, show some key nodes and states.
     * It will print: tag: message '\n' throwable
     *
     * @param tag Used to identify the source of a log message.
     * @param message The message you would like logged.
     * @param throwable An exception to log.
     */
    @JvmStatic
    fun i(tag: String, message: String, throwable: Throwable) {
        logPrinter.log(LogLevel.INFO, tag, message, throwable)
    }

    /**
     * log W print some important message, which should be noticed.
     * It will print: tag: methodName: message: value
     *
     * @param tag Used to identify the source of a log message.
     * @param methodName The name of the method where the log is.
     * @param message The message you would like logged.
     * @param value Maybe you need print some notice message or object's status.
     */
    @JvmStatic
    fun w(tag: String, methodName: String, message: String, value: Any) {
        w(tag, methodName, concat(message, value))
    }

    /**
     * log W print some important message, which should be noticed.
     * It will print: tag: methodName: message
     *
     * @param tag Used to identify the source of a log message.
     * @param methodName The name of the method where the log is.
     * @param message The message you would like logged.
     */
    @JvmStatic
    fun w(tag: String, methodName: String, message: String) {
        w(tag, concatMethod(methodName, message))
    }

    /**
     * log W print some important message, which should be noticed.
     * It will print: tag: message
     *
     * @param tag Used to identify the source of a log message.
     * @param message The message you would like logged.
     */
    @JvmStatic
    fun w(tag: String, message: String) {
        logPrinter.log(LogLevel.WARN, tag, message)
    }

    /**
     * log W print some important message, which should be noticed.
     * It will print: tag: message '\n' throwable
     *
     * @param tag Used to identify the source of a log message.
     * @param message The message you would like logged.
     * @param throwable An exception to log.
     */
    @JvmStatic
    fun w(tag: String, message: String, throwable: Throwable) {
        logPrinter.log(LogLevel.WARN, tag, message, throwable)
    }

    /**
     * log E print when some error occurs, if you forget to try catch it, process may crash.
     * It will print: tag: methodName: message
     *
     * @param tag Used to identify the source of a log message.
     * @param methodName The name of the method where the log is.
     * @param message The message you would like logged.
     */
    @JvmStatic
    fun e(tag: String, methodName: String, message: String) {
        e(tag, concatMethod(methodName, message))
    }

    /**
     * log E print when some error occurs, if you forget to try catch it, process may crash.
     * It will print: tag: message
     *
     * @param tag Used to identify the source of a log message.
     * @param message The message you would like logged.
     */
    @JvmStatic
    fun e(tag: String, message: String) {
        logPrinter.log(LogLevel.ERROR, tag, message)
    }

    /**
     * log E print when some error occurs, if you forget to try catch it, process may crash.
     * It will print: tag: methodName: message '\n' throwable
     *
     * @param tag Used to identify the source of a log message.
     * @param methodName The name of the method where the log is.
     * @param message The message you would like logged.
     * @param throwable An exception to log.
     */
    @JvmStatic
    fun e(tag: String, methodName: String, message: String, throwable: Throwable) {
        e(tag, concatMethod(methodName, message), throwable)
    }

    /**
     * log E print when some error occurs, if you forget to try catch it, process may crash.
     * It will print: tag: message '\n' throwable
     *
     * @param tag Used to identify the source of a log message.
     * @param message The message you would like logged.
     * @param throwable An exception to log.
     */
    @JvmStatic
    fun e(tag: String, message: String, throwable: Throwable) {
        logPrinter.log(LogLevel.ERROR, tag, message, throwable)
    }

    /**
     * Log wtf just print where you should never run! WHAT THE FUCKER!
     * It will print: tag: message
     *
     * @param tag Used to identify the source of a log message.
     * @param message The message you would like logged.
     */
    @JvmStatic
    fun wtf(tag: String, message: String) {
        logPrinter.log(LogLevel.WTF, tag, message)
    }

    private fun concat(message: String, value: Any) = "$message: $value"

    private fun concatMethod(methodName: String, message: String) = "[$methodName] $message"

    /**
     * Converts a boolean array to a binary number string.
     *
     * @param booleans boolean array.
     * @return a binary number string.
     */
    private fun booleansToBinary(booleans: BooleanArray?) = if (booleans == null || booleans.isEmpty()) {
        ""
    } else {
        StringBuilder().apply {
            booleans.forEach { boolean -> this.append(boolean.toInt()) }
        }.toString()
    }
}

enum class LogLevel {
    VERBOSE, DEBUG, INFO, WARN, ERROR, WTF
}

interface LogPrinter {
    fun log(level: LogLevel, tag: String, message: String)
    fun log(level: LogLevel, tag: String, message: String, thr: Throwable)
}

open class ALogPrinter(
    private val markTag: String = "", private val limitLevel: LogLevel = LogLevel.VERBOSE
) : LogPrinter {
    override fun log(level: LogLevel, tag: String, message: String) {
        if (level < limitLevel) return
        when (level) {
            LogLevel.VERBOSE -> Log.v("$markTag$tag", message)
            LogLevel.DEBUG -> Log.d("$markTag$tag", message)
            LogLevel.INFO -> Log.i("$markTag$tag", message)
            LogLevel.WARN -> Log.w("$markTag$tag", message)
            LogLevel.ERROR -> Log.e("$markTag$tag", message)
            LogLevel.WTF -> Log.wtf("$markTag$tag", message)
        }
    }

    override fun log(level: LogLevel, tag: String, message: String, thr: Throwable) {
        if (level < limitLevel) return
        when (level) {
            LogLevel.VERBOSE -> Log.v("$markTag$tag", message, thr)
            LogLevel.DEBUG -> Log.d("$markTag$tag", message, thr)
            LogLevel.INFO -> Log.i("$markTag$tag", message, thr)
            LogLevel.WARN -> Log.w("$markTag$tag", message, thr)
            LogLevel.ERROR -> Log.e("$markTag$tag", message, thr)
            LogLevel.WTF -> Log.wtf("$markTag$tag", message, thr)
        }
    }
}