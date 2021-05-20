package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.wordsearch

import com.thirtyeight.thirtyeight.domain.valueobjects.Coordinate
import java.util.*

abstract class LetterGridDataAdapter : Observable() {
    abstract fun getRowCount(): Int
    abstract fun getColCount(): Int
    abstract fun getLetter(row: Int, col: Int): Char

    abstract var selectedLetters: List<Coordinate>

    abstract fun isLetterSelected(row: Int, col: Int): Boolean
}