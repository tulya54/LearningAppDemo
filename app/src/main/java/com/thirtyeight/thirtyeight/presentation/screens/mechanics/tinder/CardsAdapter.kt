package com.thirtyeight.thirtyeight.presentation.screens.mechanics.tinder

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout

/**
 * Created by nikolozakhvlediani on 3/18/21.
 */
class CardsAdapter(
        private val images: List<Int>
) : RecyclerView.Adapter<CardsAdapter.CardViewHolder>() {

    override fun getItemCount(): Int = images.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
            CardViewHolder(parent.context.inflateLayout(R.layout.view_holder_card, parent))

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.setContent(images[position])
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val roundedImageView =
                itemView.findViewById<RoundedImageView>(R.id.rounded_image_view)

        fun setContent(imageRes: Int) {
            roundedImageView.setImageResource(imageRes)
        }
    }
}