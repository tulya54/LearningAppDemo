package com.thirtyeight.thirtyeight.data.model

/**
 * Created by nikolozakhvlediani on 4/7/21.
 */
data class QuestionDto<QuestionData, AnswerData>(
        val id: Long,
        val questionType: QuestionTypeDto,
        val condition: String,
        val acceptability: Int, // [0..100]
        val mandatory: Boolean,
        val data: QuestionData,
        val answers: List<AnswerDto<AnswerData>>,
        val timeLimit: Long? = null
)