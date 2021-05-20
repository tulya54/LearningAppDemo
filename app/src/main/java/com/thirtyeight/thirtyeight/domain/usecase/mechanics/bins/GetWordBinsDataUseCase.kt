package com.thirtyeight.thirtyeight.domain.usecase.mechanics.bins

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinCategoryEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinFallingItemEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinsDataEntity
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.util.GenerateId

/**
 * Created by nikolozakhvlediani on 3/30/21.
 */
class GetWordBinsDataUseCase : BaseUseCase<Unit, BinsDataEntity<String>>() {

    override fun execute(input: Unit): BinsDataEntity<String> {
        val animalsCategory = BinCategoryEntity(GenerateId(), "Animals")
        val musicCategory = BinCategoryEntity(GenerateId(), "Music")
        val drinksCategory = BinCategoryEntity(GenerateId(), "Drinks")
        return BinsDataEntity(
                R.string.sort_falling_words_in_correct_categories,
                listOf(animalsCategory, musicCategory, drinksCategory),
                listOf(
                        BinFallingItemEntity(GenerateId(), animalsCategory.id, "Cat"),
                        BinFallingItemEntity(GenerateId(), animalsCategory.id, "Dog"),
                        BinFallingItemEntity(GenerateId(), animalsCategory.id, "Cow"),
                        BinFallingItemEntity(GenerateId(), musicCategory.id, "Guitar"),
                        BinFallingItemEntity(GenerateId(), musicCategory.id, "Song"),
                        BinFallingItemEntity(GenerateId(), musicCategory.id, "Piano"),
                        BinFallingItemEntity(GenerateId(), drinksCategory.id, "Water"),
                        BinFallingItemEntity(GenerateId(), drinksCategory.id, "Beer"),
                        BinFallingItemEntity(GenerateId(), drinksCategory.id, "Wine")
                ).shuffled()
        )
    }
}