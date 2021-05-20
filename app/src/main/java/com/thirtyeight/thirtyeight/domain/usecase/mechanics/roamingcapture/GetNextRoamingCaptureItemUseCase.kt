package com.thirtyeight.thirtyeight.domain.usecase.mechanics.roamingcapture

import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.RoamingCaptureItemEntity
import com.thirtyeight.thirtyeight.domain.repository.MechanicsRepository
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.util.GenerateId
import kotlin.random.Random

/**
 * Created by nikolozakhvlediani on 3/21/21.
 */
class GetNextRoamingCaptureItemUseCase(
        private val mechanicsRepository: MechanicsRepository
) : BaseUseCase<Unit, RoamingCaptureItemEntity>() {

    override fun execute(input: Unit): RoamingCaptureItemEntity {
        val currentTime = System.currentTimeMillis()
        val types = mechanicsRepository.getRoamingCaptureData()
        val index = Random.nextInt(0, types.size - 1)
        val type = types[index]
        return RoamingCaptureItemEntity(GenerateId(), type.second, type.first, currentTime + 3000L)
    }
}