package com.marries.atoolkit.android.debug.floatingButton

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RelativeLayout.LayoutParams
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.marries.atoolkit.R
import com.marries.atoolkit.android.extensions.spinVisibility
import com.marries.atoolkit.android.extensions.toast
import com.marries.atoolkit.databinding.FloatingButtonListLayoutBinding

/**
 * Build button groups that developers need!
 *
 * @author dexiang.deng
 * @since 2021/8/14
 */
class FloatingButtonGroupBuilder {

    /**
     * Instance of Context for everything.
     */
    private val mContext: Context

    /**
     * Instance of layout.
     */
    private val mBinding: FloatingButtonListLayoutBinding

    /**
     * Button list of button group!
     */
    private val mButtonList: MutableList<Button> = ArrayList()

    /**
     * Default button layout id, which can be changed by [.setButtonLayout]!
     */
    @LayoutRes
    private var mButtonLayout = R.layout.floating_button_item_layout

    /**
     * Default button id in button layout, which can be changed by [.setButtonIdInLayout]
     */
    @IdRes
    private var mButtonIdInLayout = R.id.floating_button

    internal constructor(activity: Activity) {
        mContext = activity.baseContext
        mBinding = FloatingButtonListLayoutBinding.inflate(activity.layoutInflater)
        activity.addContentView(
            mBinding.root, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        )
        initView()
    }

    internal constructor(viewGroup: ViewGroup) {
        mContext = viewGroup.context
        mBinding = FloatingButtonListLayoutBinding.inflate(LayoutInflater.from(mContext))
        viewGroup.addView(
            mBinding.root, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        )
        initView()
    }

    private fun initView() {
        with(mBinding) {
            floatingButtonList.adapter = ArrayAdapter<Any>(
                mContext, R.layout.floating_button_list_layout, 0
            )
            floatingIndexButton.setOnClickListener {
                if (mButtonList.size == 0) {
                    mContext.toast("Maybe you need add a test button!")
                } else {
                    floatingButtonList.spinVisibility()
                }
            }
        }
    }

    /**
     * Change the default button layout, need to synchronize change [.mButtonIdInLayout].
     *
     * @param buttonLayout Button layout id.
     * @return FloatingButtonGroupBuilder itself.
     */
    fun setButtonLayout(buttonLayout: Int): FloatingButtonGroupBuilder {
        mButtonLayout = buttonLayout
        return this
    }

    /**
     * Change the default button id in layout.
     *
     * @param buttonIdInLayout Button id in layout.
     * @return FloatingButtonGroupBuilder itself.
     */
    fun setButtonIdInLayout(buttonIdInLayout: Int): FloatingButtonGroupBuilder {
        mButtonIdInLayout = buttonIdInLayout
        return this
    }

    fun addButton(
        buttonText: String, onClickListener: OnClickListener
    ) = addButton(buttonText, mButtonLayout, mButtonIdInLayout, onClickListener)

    fun addButton(
        buttonText: String, @IdRes buttonId: Int?, onClickListener: OnClickListener
    ) = addButton(buttonText, buttonId, mButtonLayout, mButtonIdInLayout, onClickListener)

    fun addButton(
        buttonText: String, @LayoutRes buttonLayout: Int, @IdRes buttonIdInLayout: Int, onClickListener: OnClickListener
    ) = addButton(buttonText, null, buttonLayout, buttonIdInLayout, onClickListener)

    /**
     * Add button to button list!
     *
     * @param buttonText The text to be displayed in button.
     * @param buttonId The identifier for this button.
     * @param buttonLayout The layout for this button.
     * @param buttonIdInLayout The id of the button in it's layout.
     * @param onClickListener Button clickListener!
     * @return FloatingButtonGroupBuilder itself.
     */
    fun addButton(
        buttonText: String,
        @IdRes buttonId: Int?,
        @LayoutRes buttonLayout: Int,
        @IdRes buttonIdInLayout: Int,
        onClickListener: OnClickListener
    ): FloatingButtonGroupBuilder {
        val view = View.inflate(mContext, buttonLayout, null)
        val button = view.findViewById<Button>(buttonIdInLayout).apply {
            text = buttonText
            buttonId?.let {
                id = it
            }
            setOnClickListener(onClickListener)
        }
        mButtonList.add(button)
        return this
    }

    /**
     * Add all button to group, the button group is created!
     */
    fun build() = mButtonList.forEach {
        mBinding.floatingButtonList.addFooterView(it)
    }
}