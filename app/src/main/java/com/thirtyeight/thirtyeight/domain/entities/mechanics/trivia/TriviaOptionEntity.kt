package com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
data class TriviaOptionEntity(
        val id: Long,
        val text: String
) : Serializable