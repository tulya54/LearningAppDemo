package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.captcha

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout

/**
 * Created by nikolozakhvlediani on 4/1/21.
 */
class CaptchaAdapter(
        private val onItemClick: ((Long) -> Unit)?
) : RecyclerView.Adapter<CaptchaItemViewHolder>() {

    private val list: MutableList<CaptchaItemModel> = mutableListOf()

    fun updateList(newList: List<CaptchaItemModel>) {
        val diffResult = DiffUtil.calculateDiff(CaptchaItemDiffCallback(this.list, newList), true)
        diffResult.dispatchUpdatesTo(this)
        list.clear()
        list.addAll(newList)
    }

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaptchaItemViewHolder =
            CaptchaItemViewHolder(parent.context.inflateLayout(R.layout.view_holder_captcha, parent))

    override fun onBindViewHolder(holder: CaptchaItemViewHolder, position: Int) {
        holder.setContent(list[position], onItemClick)
    }

    override fun onBindViewHolder(
            holder: CaptchaItemViewHolder,
            position: Int,
            payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.update(payloads[0] as CaptchaItemModel)
        }
    }
}