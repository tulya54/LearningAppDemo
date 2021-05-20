package com.thirtyeight.thirtyeight.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Created by nikolozakhvlediani on 4/20/21.
 */
data class ErrorModel(
        @SerializedName("response")
        val response: String,
        @SerializedName("status")
        val status: Int,
        @SerializedName("message")
        val message: String,
)