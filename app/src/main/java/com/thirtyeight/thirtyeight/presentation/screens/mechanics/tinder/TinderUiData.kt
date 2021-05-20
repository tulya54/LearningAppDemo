package com.thirtyeight.thirtyeight.presentation.screens.mechanics.tinder

import com.thirtyeight.thirtyeight.domain.entities.mechanics.tinder.TinderItemEntity
import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/10/21.
 */
class TinderUiData(
        val items: List<TinderItemEntity>
) : Serializable