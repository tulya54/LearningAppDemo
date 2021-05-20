package com.thirtyeight.thirtyeight.domain.entities.mechanics.tinder

import com.thirtyeight.thirtyeight.domain.valueobjects.TinderType
import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */
data class TinderItemEntity(
        val id: Long,
        val image: Int,
        val tinderType: TinderType
) : Serializable