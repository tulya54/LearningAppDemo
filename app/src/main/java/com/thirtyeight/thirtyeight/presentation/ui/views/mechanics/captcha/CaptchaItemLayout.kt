package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.captcha

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.makeramen.roundedimageview.RoundedImageView
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import net.igenius.customcheckbox.CustomCheckBox

/**
 * Created by nikolozakhvlediani on 3/31/21.
 */
class CaptchaItemLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val imageView: RoundedImageView
    private val checkBox: CustomCheckBox

    private var viewState = ViewState(-1, false)
    private var previousViewState: ViewState = viewState

    init {
        val view = context.inflateLayout(R.layout.layout_captcha_item, this, true)
        imageView = view.findViewById(R.id.image_view)
        checkBox = view.findViewById(R.id.check_box)
        checkBox.isEnabled = false
        checkBox.isClickable = false
        checkBox.isFocusable = false
        checkBox.isFocusableInTouchMode = false
    }

    private fun invalidateViewState() {
        with(viewState) {
            if (image != previousViewState.image) {
                imageView.setImageResource(image)
            }
            if (selected != previousViewState.selected) {
                val borderWidth = if (selected)
                    R.dimen.captcha_item_selected_border_width
                else
                    R.dimen.captcha_item_unselected_border_width
                imageView.setBorderWidth(borderWidth)
                checkBox.setChecked(selected, true)
            }
            previousViewState = this
        }
    }

    fun setImage(imageRes: Int) {
        viewState = viewState.copy(image = imageRes)
        invalidateViewState()
    }

    fun select(selected: Boolean) {
        viewState = viewState.copy(selected = selected)
        invalidateViewState()
    }

    private data class ViewState(
            val image: Int,
            val selected: Boolean
    )
}