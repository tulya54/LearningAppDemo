package com.thirtyeight.thirtyeight.data.remote.datasource.base

import com.thirtyeight.thirtyeight.data.remote.safeApiResultCall
import com.thirtyeight.thirtyeight.domain.DataResult
import retrofit2.Response

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
open class BaseRemoteDataSource {

    // TODO map to correct exceptions
    protected fun <I, O> executeApiCall(
            call: () -> Response<I>,
            successMapper: (i: I) -> O
    ): DataResult<O> {
        return safeApiResultCall(call).let {
            return@let when (it) {
                is DataResult.Success -> {
                    DataResult.Success(successMapper(it.data))
                }
                is DataResult.Error -> {
                    it
                }
            }
        }
    }
}