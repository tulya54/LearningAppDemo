package com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
data class TriviaQuestionEntity(
        val titleRes: Int,
        val text: String,
        val multipleAnswers: Boolean,
        val options: List<TriviaOptionEntity>,
        val rightAnswers: List<Long>,
        val timeLimit: Long
) : Serializable