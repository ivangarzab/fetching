package com.ivangarzab.fetching.domain.usecase

import com.ivangarzab.fetching.domain.model.Hiring
import com.ivangarzab.fetching.domain.repository.HiringRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * The purpose of this class is to test the [GetHiringDataUseCase] class.
 */
class GetHiringDataUseCaseTest {

    private val mockRepository: HiringRepository = mockk()

    private val useCase: GetHiringDataUseCase = GetHiringDataUseCase(mockRepository)

    @Test
    fun testInvokeSuccess() = runTest {
        val expected: Result<List<Hiring>> = Result.success(testHiringList)
        coEvery { mockRepository.getHiringData() } returns expected

        val result = useCase.invoke()
        assertNotNull(result)
        assertTrue(result.matrix.count() == 3)
        // Continue dissecting the result as needed
    }

    @Test
    fun testInvokeFailure() = runTest {
        val exception = Exception("Error")
        val expected: Result<List<Hiring>> = Result.failure(exception)
        coEvery { mockRepository.getHiringData() } returns expected

        val result = useCase.invoke()
        assertNull(result)
    }

    companion object {
        val testHiringList: List<Hiring> = listOf(
            Hiring(1, 1, "1"), Hiring(2, 1, "2"),
            Hiring(3, 2, "3"), Hiring(4, 2, "4"),
            Hiring(5, 3, "5"), Hiring(6, 3, "6")
        )
    }
}