package com.thirtyeight.thirtyeight.domain.usecase.mechanics.bins

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinCategoryEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinFallingItemEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinsDataEntity
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.util.GenerateId

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
class GetImageBinsDataUseCase : BaseUseCase<Unit, BinsDataEntity<Int>>() {

    override fun execute(input: Unit): BinsDataEntity<Int> {
        val butterflyCategory = BinCategoryEntity(GenerateId(), "Butterfly")
        val bullCategory = BinCategoryEntity(GenerateId(), "Bull")
        val frogCategory = BinCategoryEntity(GenerateId(), "Frog")
        return BinsDataEntity(
                R.string.sort_falling_objects_in_correct_categories,
                listOf(butterflyCategory, bullCategory, frogCategory),
                listOf(
                        BinFallingItemEntity(GenerateId(), butterflyCategory.id, R.drawable.ic_bug_yellow),
                        BinFallingItemEntity(GenerateId(), butterflyCategory.id, R.drawable.ic_bug_yellow),
                        BinFallingItemEntity(GenerateId(), bullCategory.id, R.drawable.ic_bug_red),
                        BinFallingItemEntity(GenerateId(), bullCategory.id, R.drawable.ic_bug_red),
                        BinFallingItemEntity(GenerateId(), frogCategory.id, R.drawable.ic_bug_green),
                        BinFallingItemEntity(GenerateId(), frogCategory.id, R.drawable.ic_bug_green)
                ).shuffled()
        )
    }
}