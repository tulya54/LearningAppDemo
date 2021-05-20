package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.captcha

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by nikolozakhvlediani on 4/1/21.
 */
class CaptchaItemDiffCallback(
        private val oldList: List<CaptchaItemModel>,
        private val newList: List<CaptchaItemModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].captchaOptionEntity.id == newList[newItemPosition].captchaOptionEntity.id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val wasSelected = oldList[oldItemPosition].isSelected
        val isSelected = newList[newItemPosition].isSelected
        return if (isSelected == wasSelected)
            super.getChangePayload(oldItemPosition, newItemPosition)
        else
            newList[newItemPosition]
    }
}