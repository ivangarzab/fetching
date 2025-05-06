package com.ivangarzab.fetching.domain.di

import com.ivangarzab.fetching.domain.repository.HiringRepository
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify

/**
 * The purpose of this class is to test the [domainModule] injection module.
 */
@OptIn(KoinExperimentalAPI::class)
class DomainModuleTest {

    @Test
    fun verifyDataModule() {
        domainModule.verify(
            extraTypes = listOf(HiringRepository::class)
        )
    }
}