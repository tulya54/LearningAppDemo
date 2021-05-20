package com.thirtyeight.thirtyeight.data.remote.model.register

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
data class RegisterRequestDto(
        val email: String,
        val password: String,
        val avatar: String,
        val birthDate: String,
        val name: String
)