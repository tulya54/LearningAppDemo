package com.thirtyeight.thirtyeight.data.remote

import com.google.gson.Gson
import com.thirtyeight.thirtyeight.data.remote.exception.RequestFailedException
import com.thirtyeight.thirtyeight.data.remote.model.ErrorModel
import com.thirtyeight.thirtyeight.domain.DataResult
import com.thirtyeight.thirtyeight.domain.exception.NoNetworkException
import retrofit2.Response
import java.io.IOException

// TODO map to correct exceptions
fun <T> safeApiResultCall(
        call: () -> Response<T>
): DataResult<T> {
    return try {
        val response = call.invoke()
        if (response.isSuccessful) {
            val message = response.body()
            message?.let {
                DataResult.Success<T>(it)
            } ?: DataResult.Error(Exception())
        } else {
            val errorMessage = response.errorBody()?.let {
                val errorModel: ErrorModel = Gson().fromJson(it.string(), ErrorModel::class.java)
                errorModel.message
            }
            DataResult.Error(RequestFailedException(response.code(), errorMessage))
        }
    } catch (e: Exception) {
        var throwable = e
        if (throwable is IOException)
            throwable = NoNetworkException()
        DataResult.Error(throwable)
    }
}

