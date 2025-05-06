package com.ivangarzab.fetching.data.di

import com.ivangarzab.fetching.data.api.HiringApiService
import com.ivangarzab.fetching.data.repository.HiringRepositoryImpl
import com.ivangarzab.fetching.domain.repository.HiringRepository
import org.koin.dsl.module

/**
 * [org.koin.core.Koin] module for data layer injection.
 */
val dataModule = module {
    single<HiringApiService> {
        HiringApiService.create()
    }
    single<HiringRepository> {
        HiringRepositoryImpl(
            hiringApiService = get()
        )
    }
}