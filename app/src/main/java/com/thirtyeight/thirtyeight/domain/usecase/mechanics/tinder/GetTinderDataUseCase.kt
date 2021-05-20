package com.thirtyeight.thirtyeight.domain.usecase.mechanics.tinder

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.tinder.TinderDataEntity
import com.thirtyeight.thirtyeight.domain.repository.MechanicsRepository
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */
class GetTinderDataUseCase(
        private val mechanicsRepository: MechanicsRepository
) : BaseUseCase<Unit, TinderDataEntity>() {

    override fun execute(input: Unit): TinderDataEntity =
            TinderDataEntity(
                    R.string.sort_images_correctly,
                    mechanicsRepository.getTinderData()
            )
}