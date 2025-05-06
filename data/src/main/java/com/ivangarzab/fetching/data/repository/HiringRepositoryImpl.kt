package com.ivangarzab.fetching.data.repository

import com.ivangarzab.fetching.data.api.HiringApiService
import com.ivangarzab.fetching.data.model.HiringDto
import com.ivangarzab.fetching.domain.model.Hiring
import com.ivangarzab.fetching.domain.repository.HiringRepository
import retrofit2.Response

/**
 * The implementation of the [HiringRepository] interface that fetches hiring data from the API.
 */
class HiringRepositoryImpl (
    private val hiringApiService: HiringApiService
) : HiringRepository {

    override suspend fun getHiringData(): Result<List<Hiring>> {
        return try {
            val response: Response<List<HiringDto>> = hiringApiService.getHiringData()
            if (response.isSuccessful) {
                Result.success(response.body()?.map { it.toDomainModel() } ?: emptyList())
            } else {
                Result.failure(Exception("API request failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}