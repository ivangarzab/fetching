package com.ivangarzab.fetching.ui.di

import com.ivangarzab.fetching.data.di.dataModule
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify

/**
 * The purpose of this class is to test the [appModule] injection module.
 */
@OptIn(KoinExperimentalAPI::class)
class AppModuleTest {

    @Test
    fun verifyDataModule() {
        dataModule.verify()
    }
}