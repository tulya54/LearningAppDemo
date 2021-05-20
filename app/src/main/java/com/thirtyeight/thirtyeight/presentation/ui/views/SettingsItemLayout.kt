package com.thirtyeight.thirtyeight.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
class SettingsItemLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val imageView: ImageView
    private val textView: TextView

    init {
        val view = context.inflateLayout(R.layout.layout_settings_item, this, true)
        imageView = view.findViewById(R.id.image_view)
        textView = view.findViewById(R.id.text_view)
        attrs?.let {
            context.theme.obtainStyledAttributes(it, R.styleable.SettingsItemLayout, 0, 0).apply {
                setImage(getResourceId(R.styleable.SettingsItemLayout_android_src, 0))
                setText(getString(R.styleable.SettingsItemLayout_android_text))
            }
        }
    }

    fun setImage(@DrawableRes imageRes: Int) {
        imageView.setImageResource(imageRes)
    }

    fun setText(text: String?) {
        textView.text = text
    }
}