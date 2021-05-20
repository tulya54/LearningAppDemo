package com.thirtyeight.thirtyeight.presentation.screens.mechanics.wordsearch

import com.thirtyeight.thirtyeight.domain.valueobjects.Coordinate

/**
 * Created by nikolozakhvlediani on 4/4/21.
 */
sealed class WordSearchUiAction {

    class SelectCoordinates(val coordinates: List<Coordinate>) : WordSearchUiAction()
    class CheckWord(val word: String, val coordinates: List<Coordinate>) : WordSearchUiAction()
}