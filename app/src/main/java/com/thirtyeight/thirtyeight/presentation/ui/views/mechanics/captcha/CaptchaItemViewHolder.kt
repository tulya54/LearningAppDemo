package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.captcha

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.thirtyeight.thirtyeight.R

/**
 * Created by nikolozakhvlediani on 4/1/21.
 */
class CaptchaItemViewHolder(
        itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val captchaItemLayout: CaptchaItemLayout =
            itemView.findViewById(R.id.captcha_item_layout)

    fun setContent(captchaItemModel: CaptchaItemModel, onItemClick: ((id: Long) -> Unit)?) {
        with(captchaItemModel) {
            captchaItemLayout.setImage(captchaOptionEntity.data)
            update(captchaItemModel)
            itemView.setOnClickListener {
                onItemClick?.invoke(captchaOptionEntity.id)
            }
        }
    }

    fun update(captchaItemModel: CaptchaItemModel) {
        captchaItemLayout.select(captchaItemModel.isSelected)
    }
}