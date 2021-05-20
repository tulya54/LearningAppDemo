package com.thirtyeight.thirtyeight.domain.usecase.mechanics.appearingcapture

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.AppearingCaptureDataEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.AppearingCaptureItemEntity
import com.thirtyeight.thirtyeight.domain.repository.MechanicsRepository
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.util.GenerateId

/**
 * Created by nikolozakhvlediani on 3/20/21.
 */
class GetAppearingCaptureDataUseCase(
        private val mechanicsRepository: MechanicsRepository
) : BaseUseCase<Unit, AppearingCaptureDataEntity>() {

    override fun execute(input: Unit): AppearingCaptureDataEntity {
        val types = mechanicsRepository.getAppearingCaptureTypes()
        val list = mutableListOf<AppearingCaptureItemEntity>()
        val currentTime = System.currentTimeMillis()
        types.forEachIndexed { index, type ->
            val offset = 1500L * index
            val time = currentTime + (3000L + offset)
            list.add(AppearingCaptureItemEntity(GenerateId(), type.second, type.first, time))
            list.add(
                    AppearingCaptureItemEntity(
                            GenerateId(),
                            type.second,
                            type.first,
                            time + 500L
                    )
            )
            list.add(
                    AppearingCaptureItemEntity(
                            GenerateId(),
                            type.second,
                            type.first,
                            time + 1000L
                    )
            )
        }
        return AppearingCaptureDataEntity(
                R.string.tap_only_on_butterfly,
                list.shuffled()
        )
    }
}