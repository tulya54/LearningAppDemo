package com.thirtyeight.thirtyeight.domain.usecase.auth.register

import com.thirtyeight.thirtyeight.domain.DataResult
import com.thirtyeight.thirtyeight.domain.entities.auth.register.RegistrationParams
import com.thirtyeight.thirtyeight.domain.repository.AuthRepository
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseResultUseCase

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
class RegisterUseCase(
        private val authRepository: AuthRepository
) : BaseResultUseCase<RegistrationParams, Unit>() {

    override fun execute(input: RegistrationParams): DataResult<Unit> =
            authRepository.register(input)
}