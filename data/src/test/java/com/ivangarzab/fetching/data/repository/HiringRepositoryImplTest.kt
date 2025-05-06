package com.ivangarzab.fetching.data.repository

import com.ivangarzab.fetching.data.api.HiringApiService
import com.ivangarzab.fetching.data.model.HiringDto
import com.ivangarzab.fetching.domain.model.Hiring
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

/**
 * The purpose of this class is to test the [HiringRepositoryImpl] class.
 */
class HiringRepositoryImplTest {

    private val mockHiringApiService: HiringApiService = mockk()

    private val hiringRepository = HiringRepositoryImpl(mockHiringApiService)

    @Test
    fun testGetHiringDataSuccess() = runTest {
        val test = HiringDto(id = 1, listId = 1, name = "1")
        val expected = Hiring(id = 1, listId = 1, name = "1")
        val mockResponse: Response<List<HiringDto>> = Response.success(listOf(test))
        coEvery { mockHiringApiService.getHiringData() } returns mockResponse

        val result = hiringRepository.getHiringData()
        assertTrue(result.isSuccess)
        assertEquals(listOf(expected), result.getOrNull())
    }

    @Test
    fun testGetHiringDataFailure() = runTest {
        val expected: Response<List<HiringDto>> = Response.error(404, mockk(relaxed = true))
        coEvery { mockHiringApiService.getHiringData() } returns expected

        val result = hiringRepository.getHiringData()
        assertTrue(result.isFailure)
        assertEquals("API request failed", result.exceptionOrNull()?.message)
    }

    @Test
    fun testGetHiringDataException() = runTest {
        val expected = RuntimeException("Network error")
        coEvery { mockHiringApiService.getHiringData() } throws expected

        val result = hiringRepository.getHiringData()
        assertTrue(result.isFailure)
        assertEquals(expected, result.exceptionOrNull())
    }
}