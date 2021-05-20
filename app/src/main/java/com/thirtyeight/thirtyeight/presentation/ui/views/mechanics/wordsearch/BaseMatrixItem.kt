package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.wordsearch

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.thirtyeight.thirtyeight.R
import kotlin.math.max
import kotlin.math.min

/**
 * Created by nikolozakhvlediani on 4/4/21.
 */
abstract class BaseMatrixItem @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var _gridWidth = DEFAULT_GRID_WIDTH_PIXEL
    private var _gridHeight = DEFAULT_GRID_HEIGHT_PIXEL

    var gridWidth: Int
        get() = (_gridWidth * scaleX).toInt()
        set(gridWidth) {
            _gridWidth = gridWidth
            invalidate()
        }

    var gridHeight: Int
        get() = (_gridHeight * scaleY).toInt()
        set(gridHeight) {
            _gridHeight = gridHeight
            invalidate()
        }

    abstract fun getColCount(): Int
    abstract fun setColCount(colCount: Int)
    abstract fun getRowCount(): Int
    abstract fun setRowCount(rowCount: Int)

    init {
        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.BaseMatrixItem, 0, 0)
            _gridWidth = a.getDimensionPixelSize(R.styleable.BaseMatrixItem_gridWidth, _gridWidth)
            _gridHeight = a.getDimensionPixelSize(R.styleable.BaseMatrixItem_gridHeight, _gridHeight)
            a.recycle()
        }
    }

    open val requiredWidth: Int
        get() = paddingLeft + paddingRight + getColCount() * gridWidth

    open val requiredHeight: Int
        get() = paddingTop + paddingBottom + getRowCount() * gridHeight

    fun getColIndex(screenPos: Int): Int {
        return max(min((screenPos - paddingLeft) / gridWidth, getColCount() - 1), 0)
    }

    fun getRowIndex(screenPos: Int): Int {
        return max(min((screenPos - paddingTop) / gridHeight, getRowCount() - 1), 0)
    }

    open fun getCenterColFromIndex(cIdx: Int): Int {
        return min(max(0, cIdx), getColCount() - 1) * gridWidth +
                gridWidth / 2 + paddingLeft
    }

    open fun getCenterRowFromIndex(rIdx: Int): Int {
        return min(max(0, rIdx), getRowCount() - 1) * gridHeight +
                gridHeight / 2 + paddingTop
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var measuredWidth = requiredWidth
        var measuredHeight = requiredHeight
        if (widthMode == MeasureSpec.EXACTLY) measuredWidth = width else if (widthMeasureSpec == MeasureSpec.AT_MOST) measuredWidth = min(measuredWidth, width)
        if (heightMode == MeasureSpec.EXACTLY) measuredHeight = height else if (heightMode == MeasureSpec.AT_MOST) measuredHeight = min(measuredHeight, height)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    companion object {
        private const val DEFAULT_GRID_WIDTH_PIXEL = 50
        private const val DEFAULT_GRID_HEIGHT_PIXEL = 50
    }
}