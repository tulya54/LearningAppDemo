package com.thirtyeight.thirtyeight.presentation.screens.mechanics.tinder

import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.navigation.fragment.navArgs
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentTinderBinding
import com.thirtyeight.thirtyeight.domain.valueobjects.TinderType
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionUiAction
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import com.yuyakaido.android.cardstackview.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */
class TinderFragment : ViewStateViewModelFragment<FragmentTinderBinding, TinderViewModel>() {

    private val args: TinderFragmentArgs by navArgs()

    override val layoutRes: Int
        get() = R.layout.fragment_tinder

    override val viewModel by viewModel<TinderViewModel> {
        parametersOf(args.data.items)
    }

    private val sharedViewModel by sharedViewModel<MechanicSessionViewModel>()

    override fun createBinding(view: View) = FragmentTinderBinding.bind(view)

    private lateinit var cardStackLayoutManager: CardStackLayoutManager

    private val swipeRight = SwipeAnimationSetting.Builder()
            .setDirection(Direction.Right)
            .setDuration(Duration.Slow.duration)
            .build()
    private val swipeLeft = SwipeAnimationSetting.Builder()
            .setDirection(Direction.Left)
            .setDuration(Duration.Slow.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()

    override fun initViews(view: View) {
        super.initViews(view)
        with(binding) {
            personButton.setOnClickListener {
                cardStackLayoutManager.setSwipeAnimationSetting(swipeLeft)
                cardStackView.swipe()
            }
            objectButton.setOnClickListener {
                cardStackLayoutManager.setSwipeAnimationSetting(swipeRight)
                cardStackView.swipe()
            }
            cardStackView.layoutManager = CardStackLayoutManager(context, cardStackListener).apply {
                setStackFrom(StackFrom.Bottom)
                setCanScrollHorizontal(true)
                setCanScrollVertical(true)
                setMaxDegree(0f)
                cardStackLayoutManager = this
            }
        }
    }

    override fun onViewModelCreated(viewModel: TinderViewModel) {
        super.onViewModelCreated(viewModel)
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            with(viewState) {
                if (binding.cardStackView.adapter == null && list.isNotEmpty()) {
                    binding.cardStackView.adapter = CardsAdapter(list.map { it.image })
                }
            }
        }
        viewModel.navigationLiveData.observe(viewLifecycleOwner) { navigateTo ->
            when (navigateTo) {
                is NavigateTo.Result -> {
                    sharedViewModel.processUiAction(
                            MechanicSessionUiAction.NavigateToResult(
                                    navigateTo.points,
                                    navigateTo.from
                            )
                    )
                }
            }
        }
    }

    private val cardStackListener = object : AbsCardStackListener() {

        override fun onCardSwiped(direction: Direction?) {
            val answer = if (direction == Direction.Left) {
                TinderType.PERSON
            } else {
                TinderType.OBJECT
            }
            viewModel.processUiAction(TinderUiAction.AnswerChosen(answer))
        }
    }

    companion object {

        fun createInstance(args: TinderFragmentArgs) =
                TinderFragment().apply {
                    arguments = args.toBundle()
                }
    }
}