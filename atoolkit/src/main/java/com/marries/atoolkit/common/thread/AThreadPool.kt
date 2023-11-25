package com.marries.atoolkit.common.thread

import android.os.Handler
import android.os.HandlerThread
import com.marries.atoolkit.android.ALog
import com.marries.atoolkit.android.TAG
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

object AThreadPool {

    /**
     * cpu数量
     */
    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()

    /**
     * 核心线程数为手机CPU数量 +1
     */
    private val CORE_POOL_SIZE = CPU_COUNT + 1

    /**
     * 最大线程数为手机CPU数量 ×2 +1
     */
    private val MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1

    /**
     * 线程活跃时间，超时线程会被回收
     */
    private const val KEEP_ALIVE_TIME = 3L

    /**
     * 等待队列大小
     */
    private const val WORK_QUEUE_SIZE = 128

    /**
     * 线程池 Map，根据 tag 保存所有线程池对象
     */
    private val threadPoolMap = hashMapOf<String, ThreadPoolExecutor>()

    /**
     * 一个拥有单独线程中 Looper 的 Handler，用于执行定时任务
     */
    private val threadHandler: Handler by lazy {
        val handlerThread = HandlerThread(TAG)
        handlerThread.start()
        Handler(handlerThread.looper)
    }

    /**
     * 向现有线程池 Map 中添加一个线程池，指定其 tag并返回该线程池
     *
     * @param tag 线程池 tag
     * @param threadPoolExecutor 传入的线程池
     * @return 返回传入的线程池
     */
    @JvmStatic
    fun addThreadPool(tag: String, threadPoolExecutor: ThreadPoolExecutor) =
        threadPoolExecutor.apply { threadPoolMap[tag] = this }

    /**
     * 根据 tag 在线程池 Map 中获取线程池，如果没有就创建一个默认线程池放入其中
     *
     * @param tag 线程池 tag
     */
    @JvmStatic
    fun getThreadPool(tag: String) = threadPoolMap[tag] ?: addThreadPool(tag, createDefaultThreadPool())

    /**
     * 创建一个默认的线程池
     *
     * corePoolSize 线程池中核心线程的数量
     * maximumPoolSize 线程池中最大线程数量
     * keepAliveTime 非核心线程的超时时长
     *               当系统中非核心线程闲置时间超时之后会被回收
     *               如果 allowCoreThreadTimeOut 属性设置为 true，则该参数也作用于核心线程
     * unit 时间计算单位，有纳秒、微秒、毫秒、秒、分、时、天等
     * workQueue 线程池中的任务队列，用来存储已经被提交但是尚未执行的任务，队列长度参考 AsyncTask 设置为 128
     * threadFactory 为线程池提供创建新线程的功能，这个我们一般使用默认即可
     */
    private fun createDefaultThreadPool() = ThreadPoolExecutor(
        CORE_POOL_SIZE,
        MAXIMUM_POOL_SIZE,
        KEEP_ALIVE_TIME,
        TimeUnit.SECONDS,
        ArrayBlockingQueue(WORK_QUEUE_SIZE),
        Executors.defaultThreadFactory()
    ) { _, _ ->
        ALog.w(TAG, "RejectedExecution!")
    }.apply {
        // 允许核心线程闲置超时时被回收
        allowCoreThreadTimeOut(true)
    }

    /**
     * 移除默认线程池中的对应任务
     *
     * @param runnable 任务
     */
    @JvmStatic
    fun removeRunnable(runnable: Runnable) = removeRunnable(TAG, runnable)

    /**
     * 移除对应 tag 的线程池中的对应任务
     *
     * @param tag 线程池 tag
     * @param runnable 任务
     */
    @JvmStatic
    fun removeRunnable(tag: String, runnable: Runnable) = getThreadPool(tag).queue?.remove(runnable) ?: false

    /**
     * 向默认线程池中添加任务
     *
     * @param runnable 任务
     */
    @JvmStatic
    fun addRunnable(runnable: Runnable) = addRunnable(TAG, runnable)

    /**
     * 向对应 tag 的线程池中添加任务
     *
     * @param tag 线程池 tag
     * @param runnable 任务
     */
    @JvmStatic
    fun addRunnable(tag: String, runnable: Runnable) = getThreadPool(tag).execute(runnable)

    /**
     * 添加一个定时执行任务
     *
     * @param runnable 任务
     * @param delayMillis 延迟执行时间(ms)
     */
    @JvmStatic
    fun addRunnableDelayed(runnable: Runnable, delayMillis: Long) = threadHandler.postDelayed(runnable, delayMillis)

    /**
     * 关闭对应 tag 的线程池，但不影响已经提交的任务
     *
     * @param tag 线程池 tag
     */
    @JvmStatic
    fun shutdown(tag: String = TAG) = threadPoolMap[tag]?.let {
        threadPoolMap.remove(tag)
        it.shutdown()
    }

    /**
     * 关闭对应 tag 的线程池，并尝试终止正在执行任务的线程
     *
     * @param tag 线程池 tag
     */
    @JvmStatic
    fun shutdownNow(tag: String = TAG): List<Runnable>? = threadPoolMap[tag]?.let {
        threadPoolMap.remove(tag)
        it.shutdownNow()
    }

    /**
     * 关闭所有线程池，并尝试终止正在执行任务的线程
     */
    @JvmStatic
    fun shutdownAll() = threadPoolMap.forEach {
        threadPoolMap.remove(it.key)
        it.value.shutdownNow()
    }
}