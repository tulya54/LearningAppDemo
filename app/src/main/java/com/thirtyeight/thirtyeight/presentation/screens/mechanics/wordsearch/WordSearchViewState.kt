package com.thirtyeight.thirtyeight.presentation.screens.mechanics.wordsearch

import com.thirtyeight.thirtyeight.domain.entities.mechanics.wordsearch.MatrixEntity
import com.thirtyeight.thirtyeight.domain.valueobjects.Coordinate
import com.thirtyeight.thirtyeight.presentation.logic.ViewState

/**
 * Created by nikolozakhvlediani on 4/4/21.
 */
data class WordSearchViewState(
        val matrix: MatrixEntity?,
        val words: List<String>,
        val lockedInWords: List<String>,
        val lockedInCoordinates: List<Coordinate>,
        val selectedLetters: List<Coordinate>
) : ViewState()