package com.thirtyeight.thirtyeight.domain.usecase.mechanics.appearingcapture

import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.AppearingCaptureItemEntity
import com.thirtyeight.thirtyeight.domain.repository.MechanicsRepository
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.util.GenerateId
import kotlin.random.Random

/**
 * Created by nikolozakhvlediani on 3/21/21.
 */
class GetNextAppearingCaptureItemUseCase(
        private val mechanicsRepository: MechanicsRepository
) : BaseUseCase<Unit, AppearingCaptureItemEntity>() {

    override fun execute(input: Unit): AppearingCaptureItemEntity {
        val currentTime = System.currentTimeMillis()
        val types = mechanicsRepository.getAppearingCaptureTypes()
        val index = Random.nextInt(0, types.size - 1)
        val type = types[index]
        return AppearingCaptureItemEntity(GenerateId(), type.second, type.first, currentTime + 3000L)
    }
}