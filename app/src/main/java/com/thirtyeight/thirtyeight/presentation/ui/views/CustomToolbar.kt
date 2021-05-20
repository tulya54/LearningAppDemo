package com.thirtyeight.thirtyeight.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout

/**
 * Created by nikolozakhvlediani on 4/18/21.
 */
class CustomToolbar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val titleTextView: TextView
    private val rightIconContainer: FrameLayout
    private val leftIconContainer: FrameLayout
    private val rightImageView: ImageView
    private val leftImageView: ImageView
    val toolbar: Toolbar

    var toolbarEventListener: ToolbarEventListener? = null

    var title: String? = null
        set(value) {
            field = value
            titleTextView.text = value
        }

    init {
        val view = context.inflateLayout(R.layout.toolbar_custom, this, true)
        titleTextView = view.findViewById(R.id.title_text_view)
        toolbar = view.findViewById(R.id.toolbar)
        rightIconContainer = view.findViewById(R.id.right_icon_container)
        leftIconContainer = view.findViewById(R.id.left_icon_container)
        rightImageView = view.findViewById(R.id.right_image_view)
        leftImageView = view.findViewById(R.id.left_image_view)

        rightIconContainer.setOnClickListener {
            rightIconContainerClicked()
        }

        leftIconContainer.setOnClickListener {
            leftIconContainerClicked()
        }
    }

    private fun leftIconContainerClicked() {
        toolbarEventListener?.onLeftIconClicked()
    }

    private fun rightIconContainerClicked() {
        toolbarEventListener?.onRightIconClicked()
    }

    fun getRightImageContainer(): View {
        return rightIconContainer
    }

    fun setImageResourceLeft(@DrawableRes imageResource: Int?) {
        if (imageResource == null) {
            leftIconContainer.visibility = INVISIBLE
        } else {
            leftIconContainer.visibility = VISIBLE
            leftImageView.setImageResource(imageResource)
        }
    }

    fun setImageResourceRight(@DrawableRes imageResource: Int?) {
        if (imageResource == null) {
            rightIconContainer.visibility = INVISIBLE
        } else {
            rightIconContainer.visibility = VISIBLE
            rightImageView.setImageResource(imageResource)
        }
    }

    interface ToolbarEventListener : RightIconClickEventListener {

        fun onLeftIconClicked()
    }

    interface RightIconClickEventListener {

        fun onRightIconClicked()
    }
}