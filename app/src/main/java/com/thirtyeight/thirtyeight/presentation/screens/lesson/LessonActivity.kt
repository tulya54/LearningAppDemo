package com.thirtyeight.thirtyeight.presentation.screens.lesson

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.screens.lessondescription.LessonDescriptionFragment
import com.thirtyeight.thirtyeight.util.OpenFragmentListener

class LessonActivity : AppCompatActivity(), OpenFragmentListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)

        supportFragmentManager.beginTransaction()
            .add(R.id.lesson_nav_host, LessonFragment())
            .commit()
    }

    override fun openNewFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.lesson_nav_host, fragment)
            .addToBackStack(null)
            .commit()
    }
}