package com.thirtyeight.thirtyeight.domain.usecase.mechanics.captcha

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.captcha.CaptchaOptionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.captcha.CaptchaQuestionEntity
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.util.GenerateId

/**
 * Created by nikolozakhvlediani on 3/31/21.
 */
class GetCaptchaQuestionUseCase : BaseUseCase<Unit, CaptchaQuestionEntity>() {

    override fun execute(input: Unit): CaptchaQuestionEntity {
        val human1 = CaptchaOptionEntity(GenerateId(), R.drawable.person1)
        val human2 = CaptchaOptionEntity(GenerateId(), R.drawable.person2)
        val human3 = CaptchaOptionEntity(GenerateId(), R.drawable.person3)
        val human4 = CaptchaOptionEntity(GenerateId(), R.drawable.person4)
        return CaptchaQuestionEntity(
                R.string.pick_all_correct_images,
                "Which images show people?",
                listOf(
                        human1,
                        human2,
                        human3,
                        human4,
                        CaptchaOptionEntity(GenerateId(), R.drawable.object1),
                        CaptchaOptionEntity(GenerateId(), R.drawable.object2),
                        CaptchaOptionEntity(GenerateId(), R.drawable.object3),
                        CaptchaOptionEntity(GenerateId(), R.drawable.object4),
                        CaptchaOptionEntity(GenerateId(), R.drawable.object5)
                ).shuffled(),
                listOf(human1.id, human2.id, human3.id, human4.id)
        )
    }
}