package com.thirtyeight.thirtyeight.presentation.screens.mechanics.wordsearch

import com.thirtyeight.thirtyeight.domain.entities.mechanics.wordsearch.WordSearchDataEntity
import com.thirtyeight.thirtyeight.domain.valueobjects.Coordinate

/**
 * Created by nikolozakhvlediani on 4/4/21.
 */
sealed class WordSearchWish {

    class DataLoaded(val data: WordSearchDataEntity) : WordSearchWish()
    class WordFound(val word: String, val coordinates: List<Coordinate>) : WordSearchWish()
    class SelectCoordinates(val coordinates: List<Coordinate>) : WordSearchWish()
}