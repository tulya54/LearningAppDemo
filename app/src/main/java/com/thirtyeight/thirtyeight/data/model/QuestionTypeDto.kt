package com.thirtyeight.thirtyeight.data.model

/**
 * Created by nikolozakhvlediani on 4/7/21.
 */
data class QuestionTypeDto(
        val id: Long,
        val name: String,
        val description: String,
        val category: String,
        val version: String
)