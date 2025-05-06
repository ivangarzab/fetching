package com.ivangarzab.fetching.domain.repository

import com.ivangarzab.fetching.domain.model.Hiring

/**
 * The purpose of this interface is to define the contract for the repository that will handle the
 * data operations related to the [Hiring] data.
 */
interface HiringRepository {

    /**
     * This function is responsible for fetching the hiring data from the data source.
     *
     * @return A [Result] object containing the list of [Hiring] data if the operation was successful,
     * or an error if the operation failed.
     */
    suspend fun getHiringData(): Result<List<Hiring>>
}