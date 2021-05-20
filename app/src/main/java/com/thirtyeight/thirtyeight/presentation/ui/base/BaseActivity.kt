package com.thirtyeight.thirtyeight.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.AnimRes
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Created by nikolozakhvlediani on 4/9/21.
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB

    abstract fun createBinding(layoutInflater: LayoutInflater): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = createBinding(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    protected fun replaceFragment(
            id: Int,
            fragment: Fragment,
            @AnimRes animIn: Int? = null,
            @AnimRes animOut: Int? = null
    ) {
        val ft = supportFragmentManager.beginTransaction()
        if (animIn != null && animOut != null)
            ft.setCustomAnimations(animIn, animOut)
        ft.replace(id, fragment, fragment::class.java.simpleName)
                .commitAllowingStateLoss()
    }

    protected fun removeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
    }

    @CallSuper
    protected open fun initViews() {
    }
}