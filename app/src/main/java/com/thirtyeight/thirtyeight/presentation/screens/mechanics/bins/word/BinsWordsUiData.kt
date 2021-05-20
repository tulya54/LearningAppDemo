package com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.word

import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinsDataEntity
import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/9/21.
 */
data class BinsWordsUiData(
        val data: BinsDataEntity<String>
) : Serializable