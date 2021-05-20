package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.wordsearch

import com.thirtyeight.thirtyeight.domain.valueobjects.Coordinate

internal class SampleLetterGridDataAdapter(
        private val rowCount: Int,
        private val colCount: Int
) : LetterGridDataAdapter() {

    override fun getRowCount(): Int = rowCount

    override fun getColCount(): Int = colCount

    override fun getLetter(row: Int, col: Int): Char {
        return 'A'
    }

    override var selectedLetters: List<Coordinate>
        get() = emptyList()
        set(value) {}

    override fun isLetterSelected(row: Int, col: Int): Boolean {
        return false
    }
}