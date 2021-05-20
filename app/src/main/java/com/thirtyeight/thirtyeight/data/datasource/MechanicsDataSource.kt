package com.thirtyeight.thirtyeight.data.datasource

import com.thirtyeight.thirtyeight.domain.entities.mechanics.tinder.TinderItemEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaQuestionEntity
import com.thirtyeight.thirtyeight.domain.valueobjects.AppearingCaptureType
import com.thirtyeight.thirtyeight.domain.valueobjects.RoamingCaptureType

/**
 * Created by nikolozakhvlediani on 3/14/21.
 */
interface MechanicsDataSource {

    fun getTinderData(): List<TinderItemEntity>

    fun getRoamingCaptureData(): List<Pair<RoamingCaptureType, Int>>

    fun getAppearingCaptureData(): List<Pair<AppearingCaptureType, Int>>

    fun getTriviaQuestion(): TriviaQuestionEntity
}