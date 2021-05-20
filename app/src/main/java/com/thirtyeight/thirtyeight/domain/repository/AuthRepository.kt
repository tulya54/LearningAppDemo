package com.thirtyeight.thirtyeight.domain.repository

import com.thirtyeight.thirtyeight.domain.DataResult
import com.thirtyeight.thirtyeight.domain.entities.auth.register.RegistrationParams

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
interface AuthRepository {

    fun register(params: RegistrationParams): DataResult<Unit>
}