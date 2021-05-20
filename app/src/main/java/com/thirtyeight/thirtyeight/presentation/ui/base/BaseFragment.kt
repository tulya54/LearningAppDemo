package com.thirtyeight.thirtyeight.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.AnimRes
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Created by nikolozakhvlediani on 3/19/21.
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    abstract val layoutRes: Int

    protected abstract fun createBinding(view: View): VB

    private var loadingFragment: LoadingFragment? = null
    private var isLoading: Boolean = false
    protected lateinit var binding: VB

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = FrameLayout(requireContext())
        val innerView = inflater.inflate(layoutRes, container, false)
        view.addView(innerView)
        binding = createBinding(innerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (view.id == View.NO_ID) {
            view.id = View.generateViewId()
        }
        initViews(view)
    }

    @CallSuper
    open fun initViews(view: View) {
    }

    private fun showFullScreenLoader(containerId: Int = requireView().id) {
        if (!isLoading) {
            loadingFragment = LoadingFragment.createInstance().also {
                replaceFragment(containerId, it)
            }
            isLoading = true
        }
    }

    private fun hideFullScreenLoader() {
        loadingFragment?.let(::removeFragment)
        isLoading = false
    }

    fun showOrHideFullScreenLoader(show: Boolean, containerId: Int = requireView().id) {
        if (show)
            showFullScreenLoader(containerId)
        else
            hideFullScreenLoader()
    }

    private fun replaceFragment(
            id: Int? = null,
            fragment: Fragment,
            @AnimRes animIn: Int? = null,
            @AnimRes animOut: Int? = null
    ) {
        val ft = childFragmentManager.beginTransaction()
        if (animIn != null && animOut != null)
            ft.setCustomAnimations(animIn, animOut)
        ft.replace(id ?: requireView().id, fragment, fragment::class.java.simpleName)
                .commitAllowingStateLoss()
    }

    private fun removeFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
    }
}