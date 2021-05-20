package com.thirtyeight.thirtyeight.data.remote.datasource

import com.thirtyeight.thirtyeight.data.datasource.remote.AuthRemoteDataSource
import com.thirtyeight.thirtyeight.data.remote.ServiceApi
import com.thirtyeight.thirtyeight.data.remote.datasource.base.BaseRemoteDataSource
import com.thirtyeight.thirtyeight.data.remote.model.register.RegisterRequestDto
import com.thirtyeight.thirtyeight.domain.DataResult
import com.thirtyeight.thirtyeight.domain.entities.auth.register.RegistrationParams

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
class DefaultAuthRemoteDataSource(
        private val serviceApi: ServiceApi
) : BaseRemoteDataSource(), AuthRemoteDataSource {

    override fun register(params: RegistrationParams): DataResult<Unit> =
            executeApiCall({
                serviceApi.register(
                        RegisterRequestDto(
                                params.email,
                                params.password,
                                params.avatar,
                                params.birthDate,
                                params.name
                        )
                ).execute()
            }, {})
}