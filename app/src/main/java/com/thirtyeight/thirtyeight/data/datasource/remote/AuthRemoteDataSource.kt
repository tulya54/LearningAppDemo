package com.thirtyeight.thirtyeight.data.datasource.remote

import com.thirtyeight.thirtyeight.domain.DataResult
import com.thirtyeight.thirtyeight.domain.entities.auth.register.RegistrationParams

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
interface AuthRemoteDataSource {

    fun register(params: RegistrationParams): DataResult<Unit>
}