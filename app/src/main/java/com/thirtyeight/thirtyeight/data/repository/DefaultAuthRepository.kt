package com.thirtyeight.thirtyeight.data.repository

import com.thirtyeight.thirtyeight.data.datasource.remote.AuthRemoteDataSource
import com.thirtyeight.thirtyeight.domain.DataResult
import com.thirtyeight.thirtyeight.domain.entities.auth.register.RegistrationParams
import com.thirtyeight.thirtyeight.domain.repository.AuthRepository

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
class DefaultAuthRepository(
        private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override fun register(params: RegistrationParams): DataResult<Unit> =
            authRemoteDataSource.register(params)
}