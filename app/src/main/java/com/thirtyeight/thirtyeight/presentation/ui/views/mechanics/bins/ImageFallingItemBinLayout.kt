package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.bins

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.nikoloz14.myextensions.asPx

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
class ImageFallingItemBinLayout @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    FallingItemBinLayout<AppCompatImageView, Int>(context, attrs, defStyleAttr) {

    override val dimensions: Pair<Int, Int>
        get() = 24.asPx to 24.asPx

    override fun createView(): AppCompatImageView = AppCompatImageView(context)

    override fun setDataToView(binView: AppCompatImageView, data: Int) {
        binView.setImageResource(data)
    }
}