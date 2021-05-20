package com.thirtyeight.thirtyeight.data.datasource

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.tinder.TinderItemEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaOptionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaQuestionEntity
import com.thirtyeight.thirtyeight.domain.valueobjects.AppearingCaptureType
import com.thirtyeight.thirtyeight.domain.valueobjects.RoamingCaptureType
import com.thirtyeight.thirtyeight.domain.valueobjects.TinderType
import com.thirtyeight.thirtyeight.util.GenerateId

/**
 * Created by nikolozakhvlediani on 3/14/21.
 */
class DefaultMechanicsDataSource : MechanicsDataSource {

    override fun getTinderData(): List<TinderItemEntity> =
            listOf(
                    TinderItemEntity(1, R.drawable.person1, TinderType.PERSON),
                    TinderItemEntity(2, R.drawable.person2, TinderType.PERSON),
                    TinderItemEntity(3, R.drawable.person3, TinderType.PERSON),
                    TinderItemEntity(4, R.drawable.person4, TinderType.PERSON),
                    TinderItemEntity(5, R.drawable.person5, TinderType.PERSON),
                    TinderItemEntity(6, R.drawable.object1, TinderType.OBJECT),
                    TinderItemEntity(7, R.drawable.object2, TinderType.OBJECT),
                    TinderItemEntity(8, R.drawable.object3, TinderType.OBJECT),
                    TinderItemEntity(9, R.drawable.object4, TinderType.OBJECT),
                    TinderItemEntity(10, R.drawable.object5, TinderType.OBJECT)
            )

    override fun getRoamingCaptureData(): List<Pair<RoamingCaptureType, Int>> =
            listOf(
                    RoamingCaptureType.BUTTERFLY to R.drawable.butterfly,
                    RoamingCaptureType.DOG to R.drawable.dog,
                    RoamingCaptureType.COW to R.drawable.cow,
                    RoamingCaptureType.FROG to R.drawable.frog
            )

    override fun getAppearingCaptureData(): List<Pair<AppearingCaptureType, Int>> =
            listOf(
                    AppearingCaptureType.BUTTERFLY to R.drawable.butterfly,
                    AppearingCaptureType.DOG to R.drawable.dog,
                    AppearingCaptureType.COW to R.drawable.cow,
                    AppearingCaptureType.FROG to R.drawable.frog
            )

    override fun getTriviaQuestion(): TriviaQuestionEntity {
        val rightAnswers = listOf(
                TriviaOptionEntity(GenerateId(), "Shanghai"),
                TriviaOptionEntity(GenerateId(), "Beijing"),
        )
        val wrongAnswers = listOf(
                TriviaOptionEntity(GenerateId(), "Tokyo")
        )
        return TriviaQuestionEntity(
                R.string.what_is_the_answer_to_the_question,
                "Which city is located in China?",
                true,
                rightAnswers.plus(wrongAnswers).shuffled(),
                rightAnswers.map { it.id },
                10_000L
        )
    }
}