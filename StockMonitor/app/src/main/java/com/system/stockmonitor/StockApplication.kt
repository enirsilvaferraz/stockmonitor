package com.system.stockmonitor

import android.app.Application
import com.system.stockmonitor.infra.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StockApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initDI()
    }

    private fun initDI() {

        startKoin {
            androidContext(this@StockApplication)
            modules(listOf(KoinModules.infra, KoinModules.viewModels))
        }
    }
}