package com.thirtyeight.thirtyeight.domain.usecase.base

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */
abstract class BaseUseCase<Input, Output> {

    abstract fun execute(input: Input): Output
}