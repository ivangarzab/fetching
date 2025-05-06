package com.ivangarzab.fetching

import android.app.Application
import com.ivangarzab.fetching.data.di.dataModule
import com.ivangarzab.fetching.ui.di.appModule
import com.ivangarzab.fetching.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

/**
 * The purpose of this file is to serve as the main entry point for this application.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeDependencyInjector()
    }

    private fun initializeDependencyInjector() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    dataModule,
                    domainModule
                )
            )
        }
    }
}