package com.thirtyeight.thirtyeight.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.nikoloz14.myextensions.makeVisibleOrGone
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout

/**
 * Created by nikolozakhvlediani on 4/18/21.
 */
class DefaultAvatarLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val imageView: ImageView
    private val checkBox: View

    init {
        val view = context.inflateLayout(R.layout.layout_default_avatar, this, true)
        imageView = view.findViewById(R.id.image_view)
        checkBox = view.findViewById(R.id.check_box)
        attrs?.let {
            context.theme.obtainStyledAttributes(it, R.styleable.DefaultAvatarLayout, 0, 0).apply {
                setImage(getResourceId(R.styleable.DefaultAvatarLayout_android_src, 0))
                setChecked(getBoolean(R.styleable.DefaultAvatarLayout_android_checked, false))
            }
        }
    }

    fun setImage(@DrawableRes imageRes: Int) {
        imageView.setImageResource(imageRes)
    }

    fun setChecked(checked: Boolean) {
        checkBox.makeVisibleOrGone(checked)
    }
}