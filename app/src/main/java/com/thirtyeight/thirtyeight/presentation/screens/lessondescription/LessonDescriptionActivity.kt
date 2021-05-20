package com.thirtyeight.thirtyeight.presentation.screens.lessondescription

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.thirtyeight.thirtyeight.R

class LessonDescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_description)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        supportFragmentManager.beginTransaction()
            .add(R.id.lesson_description_nav_host, LessonDescriptionFragment())
            .commit()
    }
}