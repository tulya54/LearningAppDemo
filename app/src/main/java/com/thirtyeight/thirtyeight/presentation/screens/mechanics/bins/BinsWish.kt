package com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins

import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinsDataEntity

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
sealed class BinsWish<out BinData> {

    class DataLoaded<BinData>(val data: BinsDataEntity<BinData>) : BinsWish<BinData>()
    class UpdateSelectedBin(val index: Int) : BinsWish<Nothing>()
    object IncrementPoint : BinsWish<Nothing>()
    object SendNextItem : BinsWish<Nothing>()
}