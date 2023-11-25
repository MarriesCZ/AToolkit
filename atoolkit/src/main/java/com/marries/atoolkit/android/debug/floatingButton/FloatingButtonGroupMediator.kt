package com.marries.atoolkit.android.debug.floatingButton

import android.app.Activity
import android.view.ViewGroup

/**
 * A class that init a floating button group!
 *
 * @author dexiang.deng
 * @since 2021/8/14
 */
class FloatingButtonGroupMediator {
    private var mActivity: Activity? = null
    private var mViewGroup: ViewGroup? = null

    constructor(activity: Activity) {
        mActivity = activity
    }

    constructor(viewGroup: ViewGroup) {
        mViewGroup = viewGroup
    }

    /**
     * Init the floating button group!
     *
     * @return FloatingButtonBuilder itself.
     */
    fun builder() = if (mViewGroup == null) {
        FloatingButtonGroupBuilder(mActivity!!)
    } else {
        FloatingButtonGroupBuilder(mViewGroup!!)
    }

    /**
     * Init the floating button group for Kotlin!
     *
     * @return Unit
     */
    fun build(block: FloatingButtonGroupBuilder.() -> Unit) {
        builder().apply(block).build()
    }
}