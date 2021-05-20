package com.thirtyeight.thirtyeight.domain.entities.mechanics.wordsearch

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/4/21.
 */
data class WordSearchDataEntity(
        val titleRes: Int,
        val matrixEntity: MatrixEntity,
        val words: List<String>
) : Serializable