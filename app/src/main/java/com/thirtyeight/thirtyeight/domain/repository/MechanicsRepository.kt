package com.thirtyeight.thirtyeight.domain.repository

import com.thirtyeight.thirtyeight.domain.entities.mechanics.tinder.TinderItemEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaQuestionEntity
import com.thirtyeight.thirtyeight.domain.valueobjects.AppearingCaptureType
import com.thirtyeight.thirtyeight.domain.valueobjects.RoamingCaptureType

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */
interface MechanicsRepository {

    fun getTinderData(): List<TinderItemEntity>

    fun getRoamingCaptureData(): List<Pair<RoamingCaptureType, Int>>

    fun getAppearingCaptureTypes(): List<Pair<AppearingCaptureType, Int>>

    fun getTriviaQuestion(): TriviaQuestionEntity
}