package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.wordsearch

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.orZero
import java.util.*

/**
 * Created by nikolozakhvlediani on 4/4/21.
 */
class LetterGrid @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : BaseMatrixItem(context, attrs), Observer {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }
    private val selectedPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }
    private val charBounds: Rect = Rect()
    private var gridDataAdapter: LetterGridDataAdapter? = SampleLetterGridDataAdapter(
            DEFAULT_LETTER_GRID_SAMPLE_SIZE,
            DEFAULT_LETTER_GRID_SAMPLE_SIZE
    )

    init {
        init(context, attrs)
    }

    var letterSize: Float
        get() = paint.textSize
        set(letterSize) {
            paint.textSize = letterSize
            invalidate()
        }

    var selectedLetterSize: Float
        get() = selectedPaint.textSize
        set(selectedLetterSize) {
            selectedPaint.textSize = letterSize
            invalidate()
        }

    var letterColor: Int
        get() = paint.color
        set(color) {
            paint.color = color
            invalidate()
        }

    var selectedLetterColor: Int
        get() = selectedPaint.color
        set(color) {
            selectedPaint.color = color
            invalidate()
        }

    var dataAdapter: LetterGridDataAdapter?
        get() = gridDataAdapter
        set(newDataAdapter) {
            requireNotNull(newDataAdapter) { "Data Adapater can't be null" }
            if (newDataAdapter !== gridDataAdapter) {
                gridDataAdapter?.deleteObserver(this)
                gridDataAdapter = newDataAdapter
                gridDataAdapter?.addObserver(this)
                invalidate()
                requestLayout()
            }
        }

    override fun getColCount(): Int {
        return gridDataAdapter?.getColCount().orZero()
    }

    override fun getRowCount(): Int {
        return gridDataAdapter?.getRowCount().orZero()
    }

    override fun setColCount(colCount: Int) {
        // do nothing
    }

    override fun setRowCount(rowCount: Int) {
        // do nothing
    }

    override fun update(o: Observable, arg: Any) {
        invalidate()
        requestLayout()
    }

    override fun onDraw(canvas: Canvas) {
        val gridColCount = getColCount()
        val gridRowCount = getRowCount()
        val halfWidth = gridWidth / 2
        val halfHeight = gridHeight / 2
        var x: Int
        var y = halfHeight + paddingTop

        for (i in 0 until gridRowCount) {
            x = halfWidth + paddingLeft
            for (j in 0 until gridColCount) {
                var thePaint = if (gridDataAdapter?.isLetterSelected(i, j) == true)
                    selectedPaint
                else
                    paint
                val letter = gridDataAdapter?.getLetter(i, j)
                thePaint.getTextBounds(letter.toString(), 0, 1, charBounds)
                canvas.drawText(
                        letter.toString(),
                        x - charBounds.exactCenterX(),
                        y - charBounds.exactCenterY(),
                        thePaint
                )
                x += gridWidth
            }
            y += gridHeight
        }
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        paint.textSize = DEFAULT_TEXT_SIZE
        selectedPaint.textSize = DEFAULT_TEXT_SIZE
        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.LetterGrid, 0, 0)
            paint.textSize = a.getDimension(R.styleable.LetterGrid_letterSize, paint.textSize)
            selectedPaint.textSize =
                    a.getDimension(R.styleable.LetterGrid_letterSize, selectedPaint.textSize)
            paint.color = a.getColor(R.styleable.LetterGrid_letterColor, Color.GRAY)
            selectedPaint.color = a.getColor(R.styleable.LetterGrid_letterColor, Color.GRAY)
            a.recycle()
        }
    }

    companion object {
        private const val DEFAULT_LETTER_GRID_SAMPLE_SIZE = 8
        private const val DEFAULT_TEXT_SIZE = 32f
    }
}