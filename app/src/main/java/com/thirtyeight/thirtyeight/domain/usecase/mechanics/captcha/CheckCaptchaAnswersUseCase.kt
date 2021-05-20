package com.thirtyeight.thirtyeight.domain.usecase.mechanics.captcha

import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
class CheckCaptchaAnswersUseCase() :
        BaseUseCase<CheckCaptchaAnswersUseCase.CaptchaAnswers, CheckCaptchaAnswersUseCase.CaptchaResult>() {

    override fun execute(input: CaptchaAnswers): CaptchaResult {
        var points = 0
        input.answers.forEach {
            if (input.rightAnswers.contains(it))
                points++
        }
        return CaptchaResult(
                points,
                input.rightAnswers.size
        )
    }

    data class CaptchaAnswers(
            val rightAnswers: List<Long>,
            val answers: List<Long>
    )

    data class CaptchaResult(
            val point: Int,
            val from: Int
    )
}