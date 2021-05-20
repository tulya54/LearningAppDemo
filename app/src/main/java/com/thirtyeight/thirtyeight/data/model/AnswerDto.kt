package com.thirtyeight.thirtyeight.data.model

/**
 * Created by nikolozakhvlediani on 4/7/21.
 */
data class AnswerDto<AnswerData>(
        val id: Long,
        val title: String,
        val isCorrect: Boolean,
        val data: AnswerData
)