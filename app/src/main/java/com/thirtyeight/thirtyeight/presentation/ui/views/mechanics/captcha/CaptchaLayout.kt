package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.captcha

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.captcha.CaptchaOptionEntity
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import com.thirtyeight.thirtyeight.util.AutoFitGridLayoutManager
import com.thirtyeight.thirtyeight.util.GridSpacingItemDecoration

/**
 * Created by nikolozakhvlediani on 3/31/21.
 */
class CaptchaLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var onItemClick: ((Long) -> Unit)? = null

    private lateinit var adapter: CaptchaAdapter

    private val titleTextView: TextView
    private val recyclerView: RecyclerView

    private var viewState = ViewState("", emptyList(), emptyList())
    private var previousViewState: ViewState = viewState

    init {
        val view = context.inflateLayout(R.layout.layout_captcha, this, true)
        titleTextView = view.findViewById(R.id.title_text_view)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = AutoFitGridLayoutManager(context, 110.asPx)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(3, 3.asPx, true, 0))
    }

    private fun invalidateViewState() {
        with(viewState) {
            if (questionText != previousViewState.questionText) {
                titleTextView.text = questionText
            }
            val newList = mapToCaptchaItemModelList(options, selectedOptionIds)
            if (!::adapter.isInitialized) {
                adapter = CaptchaAdapter(onItemClick)
                recyclerView.adapter = adapter
            }
            adapter.updateList(newList)
            previousViewState = this
        }
    }

    private fun mapToCaptchaItemModelList(
            options: List<CaptchaOptionEntity>,
            selectedList: List<Long>
    ): List<CaptchaItemModel> {
        return options.map {
            CaptchaItemModel(
                    it,
                    selectedList.contains(it.id)
            )
        }
    }

    fun setTitle(title: String, invalidate: Boolean = true) {
        viewState = viewState.copy(questionText = title)
        if (invalidate)
            invalidateViewState()
    }

    fun setOptions(options: List<CaptchaOptionEntity>, invalidate: Boolean = true) {
        viewState = viewState.copy(options = options)
        if (invalidate)
            invalidateViewState()
    }

    fun setSelectedItems(selectedOptionIds: List<Long>, invalidate: Boolean = true) {
        viewState = viewState.copy(selectedOptionIds = selectedOptionIds)
        if (invalidate)
            invalidateViewState()
    }

    private data class ViewState(
            val questionText: String,
            val options: List<CaptchaOptionEntity>,
            val selectedOptionIds: List<Long>
    )
}