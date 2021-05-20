package com.thirtyeight.thirtyeight.presentation.screens.mechanics.tinder

import com.thirtyeight.thirtyeight.domain.entities.mechanics.tinder.TinderItemEntity

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
sealed class TinderWish {

    class DataLoaded(val list: List<TinderItemEntity>) : TinderWish()
    object IncrementPoint : TinderWish()
    object IncrementCurrentQuestionNumber : TinderWish()
}