package com.thirtyeight.thirtyeight.presentation.screens.mechanics.wordsearch

import com.thirtyeight.thirtyeight.domain.valueobjects.Coordinate
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.wordsearch.LetterGridDataAdapter

/**
 * Created by nikolozakhvlediani on 4/4/21.
 */
class ArrayLetterGridDataAdapter internal constructor(
        private var backedGrid: Array<Array<Char>>
) : LetterGridDataAdapter() {

    var grid: Array<Array<Char>>?
        get() = backedGrid
        set(grid) {
            if (grid != null && !grid.contentEquals(backedGrid)) {
                backedGrid = grid
                setChanged()
                notifyObservers()
            }
        }

    override var selectedLetters: List<Coordinate> = emptyList()
        set(value) {
            field = value
            setChanged()
            notifyObservers(selectedLetters)
        }

    override fun getColCount(): Int {
        return backedGrid[0].size
    }

    override fun getRowCount(): Int {
        return backedGrid.size
    }

    override fun getLetter(row: Int, col: Int): Char {
        return backedGrid[row][col]
    }

    override fun isLetterSelected(row: Int, col: Int): Boolean {
        selectedLetters.forEach {
            if (it.row == row && it.col == col)
                return true
        }
        return false
    }
}