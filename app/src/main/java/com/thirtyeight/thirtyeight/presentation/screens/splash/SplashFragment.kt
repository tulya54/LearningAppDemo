package com.thirtyeight.thirtyeight.presentation.screens.splash

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentSplashBinding
import com.thirtyeight.thirtyeight.presentation.ui.base.BaseFragment

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_splash

    override fun createBinding(view: View): FragmentSplashBinding =
            FragmentSplashBinding.bind(view)

    private val handler = Handler(Looper.getMainLooper())
    private val runnable: Runnable = Runnable {
        val direction = SplashFragmentDirections.actionNavSplashToNavWelcome()
        findNavController().navigate(direction)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler.postDelayed(runnable, SPLASH_TIME)
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity?.window?.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    override fun onDetach() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity?.window?.insetsController?.show(WindowInsets.Type.statusBars())
        } else {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        super.onDetach()
    }

    companion object {

        private const val SPLASH_TIME = 2_500L
    }
}