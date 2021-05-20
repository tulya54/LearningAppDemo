package com.thirtyeight.thirtyeight.data.remote

import com.thirtyeight.thirtyeight.data.remote.model.register.RegisterRequestDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
interface ServiceApi {

    @POST("/learner/auth/register")
    fun register(@Body request: RegisterRequestDto): Call<Any>
}