package com.thirtyeight.thirtyeight.domain.usecase.mechanics.roamingcapture

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.RoamingCaptureDataEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.RoamingCaptureItemEntity
import com.thirtyeight.thirtyeight.domain.repository.MechanicsRepository
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.util.GenerateId

/**
 * Created by nikolozakhvlediani on 3/20/21.
 */
class GetRoamingCaptureDataUseCase(
        private val mechanicsRepository: MechanicsRepository
) : BaseUseCase<Unit, RoamingCaptureDataEntity>() {

    override fun execute(input: Unit): RoamingCaptureDataEntity {
        val types = mechanicsRepository.getRoamingCaptureData()
        val list = mutableListOf<RoamingCaptureItemEntity>()
        val currentTime = System.currentTimeMillis()
        types.forEachIndexed { index, type ->
            val offset = 1500L * index
            val time = currentTime + (3000L + offset)
            list.add(RoamingCaptureItemEntity(GenerateId(), type.second, type.first, time))
            list.add(
                    RoamingCaptureItemEntity(
                            GenerateId(),
                            type.second,
                            type.first,
                            time + 500L
                    )
            )
            list.add(
                    RoamingCaptureItemEntity(
                            GenerateId(),
                            type.second,
                            type.first,
                            time + 1000L
                    )
            )
        }
        return RoamingCaptureDataEntity(
                R.string.tap_only_on_butterfly,
                list.shuffled()
        )
    }
}