package com.thirtyeight.thirtyeight.presentation.screens.main.discover

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.thirtyeight.thirtyeight.R


class DiscoverAdapter(private val context: Context, private val onTopicClick: (id: Int) -> Unit) : RecyclerView.Adapter<DiscoverAdapter.ViewHolder>() {
    private var topicsList: List<String> = ArrayList()

    fun setTopicsList(list: List<String>) {
        topicsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.topics_recycler_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DiscoverAdapter.ViewHolder, position: Int) {
        val model = topicsList[position]

        holder.topicName.text = model
        holder.topicContainer.setOnClickListener {
            onTopicClick(position)
        }

        when (position) {
            0 -> holder.topicContainer.background = ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.background_first_topic_item,
                null
            )
            1 -> holder.topicContainer.background = ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.background_second_topic_item,
                null
            )
            2 -> holder.topicContainer.background = ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.background_third_topic_item,
                null
            )
            3 -> holder.topicContainer.background = ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.background_fourth_topic_item,
                null
            )
        }
    }

    override fun getItemCount(): Int {
        return topicsList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topicContainer: LinearLayout = view.findViewById(R.id.topic_container)
        val topicName: TextView = view.findViewById(R.id.topic_name)
    }
}