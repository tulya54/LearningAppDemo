package com.thirtyeight.thirtyeight.presentation.screens.main.mytopics.tabs.alltopics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.nikoloz14.myextensions.makeGone
import com.nikoloz14.myextensions.makeInvisible
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.data.model.MyTopicsMockDto


class AllTopicsAdapter(private val context: Context, private val onTopicClick: (id: Int) -> Unit) : RecyclerView.Adapter<AllTopicsAdapter.ViewHolder>() {
    private var topicsList: List<MyTopicsMockDto> = ArrayList()

    fun setTopicsList(list: List<MyTopicsMockDto>) {
        topicsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.my_topics_recycler_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = topicsList[position]

        holder.topicName.text = model.name
        holder.topicContainer.setOnClickListener {
            onTopicClick(position)
        }

        holder.topicProgressOwl.isEnabled = false
        holder.topicProgress.isEnabled = false

        if (model.progress == 0) {
            holder.topicProgress.makeInvisible()
            holder.topicProgressOwl.makeInvisible()
        } else {
            holder.topicProgressOwl.progress = model.progress
            holder.topicProgress.progress = model.progress
        }

        if (model.progress == 3) {
            holder.topicProgressOwl.thumb = AppCompatResources.getDrawable(context, R.drawable.owl_progress_three)
        } else if (model.progress == 5) {
            holder.topicProgressOwl.thumb = AppCompatResources.getDrawable(context, R.drawable.owl_progress_five)
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
        val topicProgress: AppCompatSeekBar = view.findViewById(R.id.topic_progress)
        val topicProgressOwl: AppCompatSeekBar = view.findViewById(R.id.topic_progress_owl)
        val topicContainer: ConstraintLayout = view.findViewById(R.id.topic_container)
        val topicName: TextView = view.findViewById(R.id.topic_name)
    }
}