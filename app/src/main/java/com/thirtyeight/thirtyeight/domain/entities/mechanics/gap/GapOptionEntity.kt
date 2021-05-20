package com.thirtyeight.thirtyeight.domain.entities.mechanics.gap

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
data class GapOptionEntity<OptionData>(
        val id: Long,
        val data: OptionData
) : Serializable