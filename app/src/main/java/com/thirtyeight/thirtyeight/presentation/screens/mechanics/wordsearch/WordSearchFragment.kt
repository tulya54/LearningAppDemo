package com.thirtyeight.thirtyeight.presentation.screens.mechanics.wordsearch

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.nikoloz14.myextensions.asPx
import com.nikoloz14.myextensions.getScreenDimensions
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.wordsearch.MatrixEntity
import com.thirtyeight.thirtyeight.domain.valueobjects.Coordinate
import com.thirtyeight.thirtyeight.presentation.ext.getColorFromAttr
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionUiAction
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.BaseMechanicFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.wordsearch.StreakView
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.wordsearch.WordSearchLayout
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 4/4/21.
 */
class WordSearchFragment : BaseMechanicFragment<WordSearchViewModel>() {

    private val args: WordSearchFragmentArgs by navArgs()

    private lateinit var wordSearchLayout: WordSearchLayout
    private lateinit var wordsTextView: TextView

    private var letterAdapter: ArrayLetterGridDataAdapter? = null

    override val viewModel by viewModel<WordSearchViewModel> {
        parametersOf(args.data)
    }

    private val sharedViewModel by sharedViewModel<MechanicSessionViewModel>()

    override fun createMiddleContainerView(): View? {
        val view = context.inflateLayout(R.layout.middle_part_word_search)
        wordSearchLayout = view.findViewById(R.id.word_search_layout)
        wordsTextView = view.findViewById(R.id.words_text_view)
        return view
    }

    override fun onViewModelCreated(viewModel: WordSearchViewModel) {
        super.onViewModelCreated(viewModel)
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            with(it) {
                matrix?.let { matrixEntity ->
                    if (letterAdapter == null) {
                        setUpGrid(matrixEntity)
                    }
                }
                val coordinatesToSelect =
                        createSelectedLettersList(selectedLetters, lockedInCoordinates)
                letterAdapter?.selectedLetters = coordinatesToSelect
                wordsTextView.text = generateWordsText(words, lockedInWords)
            }
        }
        viewModel.navigationLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NavigateTo.Result -> {
                    sharedViewModel.processUiAction(
                            MechanicSessionUiAction.NavigateToResult(
                                    it.points,
                                    it.from
                            )
                    )
                }
            }
        }
        viewModel.clearSelection.observe(viewLifecycleOwner) {
            wordSearchLayout.popStreakLine()
            val coordinatesToSelect =
                    createSelectedLettersList(it, emptyList())
            letterAdapter?.selectedLetters = coordinatesToSelect
        }
    }

    private fun createSelectedLettersList(
            coordinates: List<Coordinate>,
            lockedInCoordinates: List<Coordinate>
    ): List<Coordinate> {
        val list = mutableListOf<Coordinate>()
        list.addAll(coordinates)
        list.addAll(lockedInCoordinates)
        return list.toSet().toList()
    }

    private fun showLetterGrid(grid: Array<Array<Char>>) {
        if (letterAdapter == null) {
            letterAdapter = ArrayLetterGridDataAdapter(grid)
            letterAdapter?.let {
                wordSearchLayout.dataAdapter = it
            }
        } else {
            letterAdapter?.grid = grid
        }
    }

    private fun setUpGrid(matrixEntity: MatrixEntity) {
        showLetterGrid(matrixEntity.matrix)
        wordSearchLayout.post {
            val dimens = getScreenDimensions()
            val boardWidth = wordSearchLayout.width
            val screenWidth = dimens!!.width - 40.asPx
            val scale = screenWidth.toFloat() / boardWidth.toFloat()
            wordSearchLayout.scale(scale, scale)
        }
        setUpWordSearchListener()
    }

    private fun setUpWordSearchListener() {
        wordSearchLayout.selectionListener =
                object : WordSearchLayout.OnLetterSelectionListener {
                    override fun onSelectionBegin(
                            streakLine: StreakView.StreakLine,
                            str: String,
                            coordinates: List<Coordinate>
                    ) {
                        streakLine.color = requireContext().getColorFromAttr(R.attr.colorPrimary)
                        viewModel.processUiAction(WordSearchUiAction.SelectCoordinates(coordinates))
                    }

                    override fun onSelectionDrag(
                            streakLine: StreakView.StreakLine,
                            str: String,
                            coordinatesOfRange: List<Coordinate>
                    ) {
                        viewModel.processUiAction(
                                WordSearchUiAction.SelectCoordinates(
                                        coordinatesOfRange
                                )
                        )
                    }

                    override fun onSelectionEnd(
                            streakLine: StreakView.StreakLine,
                            str: String,
                            coordinates: List<Coordinate>
                    ) {
                        viewModel.processUiAction(WordSearchUiAction.CheckWord(str, coordinates))
                    }
                }
    }

    private fun generateWordsText(words: List<String>, foundWords: List<String>): CharSequence {
        val text = SpannableStringBuilder()
        words.forEachIndexed { index, word ->
            val offset = if (index == words.size - 1) "" else ", "
            val finalWord = word + offset
            text.append(SpannableString(finalWord).apply {
                if (foundWords.contains(word)) {
                    setSpan(
                            ForegroundColorSpan(requireContext().getColorFromAttr(R.attr.colorPrimary)),
                            0, finalWord.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            })
        }
        return text
    }

    companion object {

        fun createInstance(args: WordSearchFragmentArgs) =
                WordSearchFragment().apply {
                    arguments = args.toBundle()
                }
    }
}