package com.thirtyeight.thirtyeight.domain.usecase.mechanics

import com.thirtyeight.thirtyeight.domain.entities.mechanics.MechanicDataEntity
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase

/**
 * Created by nikolozakhvlediani on 4/10/21.
 */
class GetMechanicDataByTypeUseCase(
        private val getAllMechanicsDataUseCase: GetAllMechanicsDataUseCase
) : BaseUseCase<Class<*>, MechanicDataEntity>() {

    override fun execute(input: Class<*>): MechanicDataEntity {
        val data = getAllMechanicsDataUseCase.execute(Unit)
        return data.first { it::class.java == input }
    }
}