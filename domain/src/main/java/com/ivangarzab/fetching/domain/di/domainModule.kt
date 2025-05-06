package com.ivangarzab.fetching.domain.di

import com.ivangarzab.fetching.domain.usecase.GetHiringDataUseCase
import com.ivangarzab.fetching.domain.repository.HiringRepository
import org.koin.dsl.module

/**
 * [org.koin.core.Koin] module for domain layer injection.
 */
val domainModule = module {
    factory {
        GetHiringDataUseCase(
            get(HiringRepository::class)
        )
    }
}