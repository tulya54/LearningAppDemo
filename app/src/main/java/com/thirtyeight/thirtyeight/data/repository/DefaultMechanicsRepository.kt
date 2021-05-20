package com.thirtyeight.thirtyeight.data.repository

import com.thirtyeight.thirtyeight.data.datasource.MechanicsDataSource
import com.thirtyeight.thirtyeight.domain.entities.mechanics.tinder.TinderItemEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaQuestionEntity
import com.thirtyeight.thirtyeight.domain.repository.MechanicsRepository
import com.thirtyeight.thirtyeight.domain.valueobjects.AppearingCaptureType
import com.thirtyeight.thirtyeight.domain.valueobjects.RoamingCaptureType

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */
class DefaultMechanicsRepository(
        private val mechanicsDataSource: MechanicsDataSource
) : MechanicsRepository {

    override fun getTinderData(): List<TinderItemEntity> =
            mechanicsDataSource.getTinderData().shuffled()

    override fun getRoamingCaptureData(): List<Pair<RoamingCaptureType, Int>> =
            mechanicsDataSource.getRoamingCaptureData().shuffled()

    override fun getAppearingCaptureTypes(): List<Pair<AppearingCaptureType, Int>> =
            mechanicsDataSource.getAppearingCaptureData().shuffled()

    override fun getTriviaQuestion(): TriviaQuestionEntity =
            mechanicsDataSource.getTriviaQuestion()
}