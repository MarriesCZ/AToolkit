package com.marries.atoolkit.android.debug

import android.app.Activity
import android.view.ViewGroup
import com.marries.atoolkit.android.debug.floatingButton.FloatingButtonGroupMediator

/**
 * An open source Android library for developer to debug extremely easy.
 *
 * The following snippet shows the simple usage:
 * <pre>
 * ADebugPack.addFloatingButtonGroup(activity)
 * .builder()
 * .addButton("1", v -> Toast.makeText(this, "1", Toast.LENGTH_SHORT).show())
 * .build();
 * </pre>
 *
 * @author dexiang.deng
 * @since 2021/8/14
 */
object ADebugPack {

    /**
     * Add a floating button group!
     *
     * @param activity An instance of Activity.
     * @return FloatingButtonMediator instance.
     */
    @JvmStatic
    fun addFloatingButtonGroup(activity: Activity): FloatingButtonGroupMediator {
        return FloatingButtonGroupMediator(activity)
    }

    /**
     * Add a floating button group!
     *
     * @param viewGroup An instance of ViewGroup.
     * @return FloatingButtonMediator instance.
     */
    @JvmStatic
    fun addFloatingButtonGroup(viewGroup: ViewGroup): FloatingButtonGroupMediator {
        return FloatingButtonGroupMediator(viewGroup)
    }
}