package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.wordsearch

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.valueobjects.Coordinate
import com.thirtyeight.thirtyeight.domain.valueobjects.Direction
import com.thirtyeight.thirtyeight.domain.valueobjects.Direction.Companion.fromLine
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.wordsearch.StreakView.SnapType.Companion.fromId
import java.util.*
import kotlin.math.abs
import kotlin.math.max

/**
 * Created by nikolozakhvlediani on 4/4/21.
 */
class WordSearchLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : CenterLayout(context, attrs), Observer {

    val gridLineBackground: GridLine = GridLine(context)
    val streakView: StreakView = StreakView(context)
    private val letterGrid: LetterGrid = LetterGrid(context)
    private var initialized = false
    private lateinit var _dataAdapter: LetterGridDataAdapter

    var selectionListener: OnLetterSelectionListener? = null

    init {
        init(context, attrs)
    }

    override fun update(observable: Observable, arg: Any) {
        if (observable == _dataAdapter) {
            gridLineBackground.setColCount(_dataAdapter.getColCount())
            gridLineBackground.setRowCount(_dataAdapter.getRowCount())

            streakView.invalidate()
            streakView.requestLayout()
        }
    }

    fun scale(scaleX: Float, scaleY: Float) {
        if (initialized) {
            gridLineBackground.gridWidth = (gridLineBackground.gridWidth * scaleX).toInt()
            gridLineBackground.gridHeight = (gridLineBackground.gridHeight * scaleY).toInt()

            letterGrid.gridWidth = (letterGrid.gridWidth * scaleX).toInt()
            letterGrid.gridHeight = (letterGrid.gridHeight * scaleY).toInt()
            letterGrid.letterSize = letterGrid.letterSize * scaleY
            letterGrid.selectedLetterSize = letterGrid.letterSize * scaleY
            streakView.streakWidth = (streakView.streakWidth * scaleY).toInt()

            removeAllViews()
            attachAllViews()
            streakView.invalidateStreakLine()
        }
    }

    val gridColCount: Int
        get() = _dataAdapter.getColCount()
    val gridRowCount: Int
        get() = _dataAdapter.getRowCount()

    var dataAdapter: LetterGridDataAdapter
        get() = _dataAdapter
        set(dataAdapter) {
            if (dataAdapter != _dataAdapter) {
                _dataAdapter.deleteObserver(this)
                _dataAdapter = dataAdapter
                _dataAdapter.addObserver(this)
                letterGrid.dataAdapter = _dataAdapter
                gridLineBackground.setColCount(_dataAdapter.getColCount())
                gridLineBackground.setRowCount(_dataAdapter.getRowCount())
            }
        }

    fun addStreakLines(streakLines: List<StreakView.StreakLine>) {
        streakView.addStreakLines(streakLines, false)
    }

    fun addStreakLine(streakLine: StreakView.StreakLine?) {
        if (streakLine != null) streakView.addStreakLine(streakLine, true)
    }

    fun popStreakLine() {
        val line = streakView.popStreakLine()
        val list = _dataAdapter.selectedLetters.toMutableList()
        line?.let {
            val coordinates = getCoordinatesOfRange(line.startIndex, line.endIndex)
            list.removeAll(coordinates)
            _dataAdapter.selectedLetters = list
        }
    }

    fun removeAllStreakLine() {
        streakView.removeAllStreakLine()
    }

    private fun setGridWidth(width: Int) {
        gridLineBackground.gridWidth = width
        letterGrid.gridWidth = width
    }

    private fun setGridHeight(height: Int) {
        gridLineBackground.gridHeight = height
        letterGrid.gridHeight = height
    }

    private fun setGridLineVisibility(visible: Boolean) {
        if (!visible) gridLineBackground.visibility = INVISIBLE else gridLineBackground.visibility =
                VISIBLE
    }

    private fun setGridLineColor(color: Int) {
        gridLineBackground.lineColor = color
    }

    private fun setGridLineWidth(width: Int) {
        gridLineBackground.lineWidth = width
    }

    private fun setLetterSize(size: Float) {
        letterGrid.letterSize = size
        letterGrid.selectedLetterSize = size
    }

    private fun setLetterColor(color: Int) {
        letterGrid.letterColor = color
    }

    private fun setSelectedLetterColor(color: Int) {
        letterGrid.selectedLetterColor = color
    }

    private fun setStreakWidth(width: Int) {
        streakView.streakWidth = width
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        var gridWidth = DEFAULT_GRID_WIDTH_PIXEL
        var gridHeight = DEFAULT_GRID_HEIGHT_PIXEL
        var gridColCount = DEFAULT_GRID_SIZE
        var gridRowCount = DEFAULT_GRID_SIZE
        var lineColor = Color.GRAY
        var lineWidth = DEFAULT_LINE_WIDTH_PIXEL
        var letterSize = DEFAULT_LETTER_SIZE_PIXEL
        var letterColor = Color.GRAY
        var selectedLetterColor = Color.GRAY
        var streakWidth = DEFAULT_STREAK_WIDTH_PIXEL
        var snapToGrid = 0
        var gridLineVisibility = true
        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.WordSearchLayout, 0, 0)
            gridWidth = a.getDimensionPixelSize(R.styleable.WordSearchLayout_gridWidth, gridWidth)
            gridHeight =
                    a.getDimensionPixelSize(R.styleable.WordSearchLayout_gridHeight, gridHeight)
            gridColCount = a.getInteger(R.styleable.WordSearchLayout_gridColumnCount, gridColCount)
            gridRowCount = a.getInteger(R.styleable.WordSearchLayout_gridRowCount, gridRowCount)
            lineColor = a.getColor(R.styleable.WordSearchLayout_lineColor, lineColor)
            lineWidth = a.getDimensionPixelSize(R.styleable.WordSearchLayout_lineWidth, lineWidth)
            letterSize = a.getDimension(R.styleable.WordSearchLayout_letterSize, letterSize)
            letterColor = a.getColor(R.styleable.WordSearchLayout_letterColor, letterColor)
            selectedLetterColor =
                    a.getColor(R.styleable.LetterGrid_selectedLetterColor, selectedLetterColor)
            streakWidth =
                    a.getDimensionPixelSize(R.styleable.WordSearchLayout_streakWidth, streakWidth)
            snapToGrid = a.getInteger(R.styleable.WordSearchLayout_snapToGrid, snapToGrid)
            gridLineVisibility =
                    a.getBoolean(R.styleable.WordSearchLayout_gridLineVisibility, gridLineVisibility)
            setGridWidth(gridWidth)
            setGridHeight(gridHeight)
            setGridLineColor(lineColor)
            setGridLineWidth(lineWidth)
            setLetterSize(letterSize)
            setLetterColor(letterColor)
            setSelectedLetterColor(selectedLetterColor)
            setStreakWidth(streakWidth)
            setGridLineVisibility(gridLineVisibility)
            a.recycle()
        }
        _dataAdapter = SampleLetterGridDataAdapter(gridRowCount, gridColCount)
        gridLineBackground.setColCount(gridColCount)
        gridLineBackground.setRowCount(gridRowCount)
        streakView.grid = gridLineBackground
        streakView.isInteractive = true
        streakView.isRememberStreakLine = true
        streakView.isSnapToGrid = fromId(snapToGrid)
        streakView.setOnInteractionListener(StreakViewInteraction())
        attachAllViews()
        initialized = true
        scaleX = scaleX
        scaleY = scaleY
    }

    private fun attachAllViews() {
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        addView(gridLineBackground, layoutParams)
        addView(streakView, layoutParams)
        addView(letterGrid, layoutParams)
    }

    private inner class StreakViewInteraction : StreakView.OnInteractionListener {
        private fun getStringInRange(start: Coordinate, end: Coordinate): String {
            val dir = fromLine(start, end)
            if (dir === Direction.NONE) return ""
            val count = getIndexLength(start, end)
            val buff = CharArray(count)
            for (i in 0 until count) {
                buff[i] =
                        _dataAdapter.getLetter(start.row + dir.yOffset * i, start.col + dir.xOffset * i)
            }
            return String(buff)
        }

        override fun onTouchBegin(streakLine: StreakView.StreakLine) {
            selectionListener?.let {
                val coordinates = listOf(streakLine.startIndex)
                val idx = streakLine.startIndex
                val str = _dataAdapter.getLetter(idx.row, idx.col).toString()
                it.onSelectionBegin(streakLine, str, coordinates)
            }
        }

        override fun onTouchDrag(streakLine: StreakView.StreakLine) {
            selectionListener?.onSelectionDrag(
                    streakLine,
                    getStringInRange(streakLine.startIndex, streakLine.endIndex),
                    getCoordinatesOfRange(streakLine.startIndex, streakLine.endIndex)
            )
        }

        override fun onTouchEnd(streakLine: StreakView.StreakLine) {
            selectionListener?.let {
                val str = getStringInRange(streakLine.startIndex, streakLine.endIndex)
                val coordinates = getCoordinatesOfRange(streakLine.startIndex, streakLine.endIndex)
                it.onSelectionEnd(streakLine, str, coordinates)
            }
        }
    }

    private fun getCoordinatesOfRange(start: Coordinate, end: Coordinate): List<Coordinate> {
        val coordinates = mutableListOf<Coordinate>()
        val dir = fromLine(start, end)
        Log.d("Fachare3", "$start $end $dir")
        if (dir === Direction.NONE) return listOf(start, end).toSet().toList()
        val count = getIndexLength(start, end)
        for (i in 0 until count) {
            val row = start.row + dir.yOffset * i
            val col = start.col + dir.xOffset * i
            coordinates.add(Coordinate(row, col))
        }
        return coordinates
    }

    interface OnLetterSelectionListener {
        fun onSelectionBegin(
                streakLine: StreakView.StreakLine,
                str: String,
                coordinates: List<Coordinate>
        )

        fun onSelectionDrag(
                streakLine: StreakView.StreakLine,
                str: String,
                coordinatesOfRange: List<Coordinate>
        )

        fun onSelectionEnd(
                streakLine: StreakView.StreakLine,
                str: String,
                coordinates: List<Coordinate>
        )
    }

    companion object {
        private const val DEFAULT_GRID_WIDTH_PIXEL = 50
        private const val DEFAULT_GRID_HEIGHT_PIXEL = 50
        private const val DEFAULT_GRID_SIZE = 8
        private const val DEFAULT_LINE_WIDTH_PIXEL = 2
        private const val DEFAULT_LETTER_SIZE_PIXEL = 32.0f
        private const val DEFAULT_STREAK_WIDTH_PIXEL = 35
    }

    private fun getIndexLength(start: Coordinate, end: Coordinate): Int {
        val x = abs(start.col - end.col)
        val y = abs(start.row - end.row)
        return max(x, y) + 1
    }
}