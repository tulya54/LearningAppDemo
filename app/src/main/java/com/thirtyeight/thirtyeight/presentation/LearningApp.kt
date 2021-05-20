package com.thirtyeight.thirtyeight.presentation

import android.app.Application
import com.google.firebase.FirebaseApp
import com.thirtyeight.thirtyeight.data.di.dataSourceModule
import com.thirtyeight.thirtyeight.data.di.networkModule
import com.thirtyeight.thirtyeight.data.di.repositoryModule
import com.thirtyeight.thirtyeight.domain.di.useCaseModule
import com.thirtyeight.thirtyeight.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */
class LearningApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@LearningApp)
            modules(
                    listOf(
                            viewModelModule,
                            useCaseModule,
                            repositoryModule,
                            dataSourceModule,
                            networkModule
                    )
            )
        }
    }
}