package com.thirtyeight.thirtyeight.domain.usecase.base

import com.thirtyeight.thirtyeight.domain.DataResult

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */
abstract class BaseResultUseCase<Input, Output> : BaseUseCase<Input, DataResult<Output>>()