package com.thirtyeight.thirtyeight.domain.entities.mechanics.columns

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
data class ColumnsQuestionEntity(
        val titleRes: Int,
        val questionText: String,
        val firstColumn: List<ColumnOptionEntity>,
        val secondColumn: List<ColumnOptionEntity>,
        val rightAnswers: List<Pair<Long, Long>>
) : Serializable