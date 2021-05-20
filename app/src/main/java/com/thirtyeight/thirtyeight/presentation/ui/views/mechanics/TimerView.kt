package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.convertToPresentableString
import com.thirtyeight.thirtyeight.presentation.ext.getColorFromAttr
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by nikolozakhvlediani on 3/24/21.
 */
class TimerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val imageView: ImageView
    private val textView: TextView

    private var viewState = ViewState(0)

    private val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())

    init {
        val view = context.inflateLayout(R.layout.view_timer, this, true)
        imageView = view.findViewById(R.id.image_view)
        textView = view.findViewById(R.id.text_view)
    }

    fun updateTime(time: Long) {
        viewState = viewState.copy(time = time)
        invalidateViewState()
    }

    private fun invalidateViewState() {
        with(viewState) {
            textView.text = time.convertToPresentableString(dateFormat)
            val color = if (time <= 10_000L)
                ContextCompat.getColor(context, R.color.red_700)
            else
                context.getColorFromAttr(R.attr.colorPrimary)
            textView.setTextColor(color)
            DrawableCompat.setTint(
                    DrawableCompat.wrap(imageView.drawable),
                    color
            )
        }
    }

    private data class ViewState(
            val time: Long
    )
}