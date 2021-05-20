package com.thirtyeight.thirtyeight.presentation.screens.mechanics

import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentMechanicsBinding
import com.thirtyeight.thirtyeight.domain.entities.mechanics.MechanicDataEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.Mechanics
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.GetAllMechanicsDataUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.GetMechanicDataByTypeUseCase
import com.thirtyeight.thirtyeight.presentation.ui.CTextView
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewModelFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by nikolozakhvlediani on 3/19/21.
 */
class MechanicsFragment : ViewModelFragment<FragmentMechanicsBinding, MechanicsViewModel>() {

    override val layoutRes: Int
        get() = R.layout.fragment_mechanics

    override fun createBinding(view: View) = FragmentMechanicsBinding.bind(view)

    override val viewModel by viewModel<MechanicsViewModel>()
    val getAllMechanicsDataUseCase: GetAllMechanicsDataUseCase by inject()
    val getMechanicDataByTypeUseCase: GetMechanicDataByTypeUseCase by inject()

    override fun initViews(view: View) {
        super.initViews(view)
        val contextWrapper =
                ContextThemeWrapper(context, R.style.MechPrimaryButtonStyle)
        upperData.forEachIndexed { index, destination ->
            binding.linearLayout.addView(
                    CTextView(contextWrapper, null, R.style.MechPrimaryButtonStyle).apply {
                        text = destination.title
                        setOnClickListener {
                            findNavController().navigate(
                                    MechanicsFragmentDirections.actionNavMainToNavMechanicSession(
                                            Mechanics(destination.mechanics)
                                    )
                            )
                        }
                    },
                    index,
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 45.asPx).apply {
                        bottomMargin = 5.asPx
                    }
            )
        }
        lowerData.forEachIndexed { index, destination ->
            binding.linearLayout.addView(
                    CTextView(contextWrapper, null, R.style.MechPrimaryButtonStyle).apply {
                        text = destination.title
                        setOnClickListener {
                            findNavController().navigate(
                                    MechanicsFragmentDirections.actionNavMainToNavMechanicSession(
                                            Mechanics(listOf(getMechanicDataByTypeUseCase.execute(destination.clazz)))
                                    )
                            )
                        }
                    },
                    index + 1 + upperData.size,
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 45.asPx).apply {
                        bottomMargin = 5.asPx
                    }
            )
        }
    }

    private val upperData = listOf(
            Destination(
                    "Simulate",
                    getAllMechanicsDataUseCase.execute(Unit)
            )
    )

    private val lowerData = listOf(
            Tmp("Tinder", MechanicDataEntity.TinderMechanicDataEntity::class.java),
            Tmp("Roaming Capture", MechanicDataEntity.RoamingCaptureMechanicDataEntity::class.java),
            Tmp("Appearing Capture", MechanicDataEntity.AppearingCaptureMechanicDataEntity::class.java),
            Tmp("Trivia", MechanicDataEntity.TriviaMechanicDataEntity::class.java),
            Tmp(
                    "Sentence Gap",
                    MechanicDataEntity.GapMechanicDataEntity.GapSentenceMechanicDataEntity::class.java
            ),
            Tmp(
                    "Image-Text Gap",
                    MechanicDataEntity.GapMechanicDataEntity.GapImageTextMechanicDataEntity::class.java
            ),
            Tmp(
                    "Image-Image Gap",
                    MechanicDataEntity.GapMechanicDataEntity.GapImageImageMechanicDataEntity::class.java
            ),
            Tmp(
                    "Image Bins",
                    MechanicDataEntity.BinsMechanicDataEntity.BinsImagesMechanicDataEntity::class.java
            ),
            Tmp(
                    "Word Bins",
                    MechanicDataEntity.BinsMechanicDataEntity.BinsWordsMechanicDataEntity::class.java
            ),
            Tmp(
                    "Captcha",
                    MechanicDataEntity.CaptchaMechanicDataEntity::class.java
            ),
            Tmp(
                    "True or False",
                    MechanicDataEntity.TrueOrFalseMechanicDataEntity::class.java
            ),
            Tmp(
                    "Order Images",
                    MechanicDataEntity.GapMechanicDataEntity.GapImageOrderMechanicDataEntity::class.java
            ),
            Tmp(
                    "Order Words",
                    MechanicDataEntity.GapMechanicDataEntity.GapWordOrderMechanicDataEntity::class.java
            ),
            Tmp(
                    "Arm Wrestling",
                    MechanicDataEntity.GapMechanicDataEntity.ArmWrestlingMechanicDataEntity::class.java
            ),
            Tmp(
                    "Columns",
                    MechanicDataEntity.ColumnsMechanicDataEntity::class.java
            ),
            Tmp(
                    "Balance Weights",
                    MechanicDataEntity.BalanceWeightMechanicDataEntity::class.java
            ),
            Tmp(
                    "Word Search",
                    MechanicDataEntity.GapMechanicDataEntity.GapWordOrderMechanicDataEntity::class.java
            )
    )

    data class Destination(
            val title: String,
            val mechanics: List<MechanicDataEntity>
    )

    data class Tmp(
            val title: String,
            val clazz: Class<*>
    )
}