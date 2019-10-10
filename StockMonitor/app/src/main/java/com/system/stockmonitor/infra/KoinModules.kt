package com.system.stockmonitor.infra

import androidx.room.Room
import com.system.stockmonitor.repository.ApiRepository
import com.system.stockmonitor.repository.AppDatabase
import com.system.stockmonitor.repository.StockRepository
import com.system.stockmonitor.views.StockListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModules {

    val infra = module {

        factory {
            Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                "mydb"
            ).allowMainThreadQueries().build().stockStoredDao() as StockRepository
        }

        single { ApiRepository() }
    }

    val viewModels = module {
        viewModel { StockListViewModel(repository = get(), business = get()) }
    }
}