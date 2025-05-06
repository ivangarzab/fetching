package com.ivangarzab.fetching.data.di

import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify

/**
 * The purpose of this class is to test the [dataModule] injection module.
 */
@OptIn(KoinExperimentalAPI::class)
class DataModuleTest {

    @Test
    fun verifyDataModule() {
        dataModule.verify()
    }
}