package com.ivangarzab.fetching.data.api

import com.ivangarzab.fetching.data.model.HiringDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * The purpose of this interface is to define the API service for fetching [HiringDto] data
 * using [Retrofit].
 */
interface HiringApiService {

    /**
     * Get the hiring data from the API, as a list of [HiringDto].
     */
    @GET("hiring.json")
    suspend fun getHiringData(): Response<List<HiringDto>>

    companion object {
        private const val BASE_URL = "https://hiring.fetch.com/"

        /**
         * Create an instance of the [HiringApiService] using [Retrofit].
         */
        fun create(): HiringApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(HiringApiService::class.java)
        }
    }
}