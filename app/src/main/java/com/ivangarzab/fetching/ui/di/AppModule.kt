package com.ivangarzab.fetching.ui.di

import com.ivangarzab.fetching.ui.MainViewModel
import com.ivangarzab.fetching.domain.usecase.GetHiringDataUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

/**
 * [org.koin.core.Koin] module for UI (app) layer injection.
 */
val appModule = module {
    viewModel {
        MainViewModel(
            getHiringDataUseCase = get(GetHiringDataUseCase::class.java)
        )
    }
}