package com.ivangarzab.fetching.ui

import com.ivangarzab.fetching.domain.model.Hiring
import com.ivangarzab.fetching.domain.model.HiringMatrix
import com.ivangarzab.fetching.domain.model.HiringSection
import com.ivangarzab.fetching.domain.usecase.GetHiringDataUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * The purpose of this class is to test the [MainViewModel] class.
 */
@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val mockUseCase: GetHiringDataUseCase = mockk()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testFirstLoadHiringDataCallSuccess() = runTest {
        val mockData = createMockHiringMatrix()
        coEvery { mockUseCase.invoke() } returns mockData

        viewModel = MainViewModel(mockUseCase)
        assertTrue(viewModel.uiState.value is MainViewModel.UiState.Loading)

        // Wait until the dispatcher is done working
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.uiState.first()
        assertTrue(result is MainViewModel.UiState.Success)
        assertEquals(mockData, (result as MainViewModel.UiState.Success).result)
    }

    @Test
    fun testFirstLoadHiringDataCallFailure() = runTest {
        coEvery { mockUseCase.invoke() } returns null

        viewModel = MainViewModel(mockUseCase)
        assertTrue(viewModel.uiState.value is MainViewModel.UiState.Loading)

        // Wait until the dispatcher is done working
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.uiState.first()
        assertTrue(result is MainViewModel.UiState.Error)
        assertEquals("Error retrieving data", (result as MainViewModel.UiState.Error).message)
    }

    @Test
    fun testManualSuccessfulLoadAfterInitialFailure() = runTest {
        coEvery { mockUseCase.invoke() } returns null

        viewModel = MainViewModel(mockUseCase)
        assertTrue(viewModel.uiState.value is MainViewModel.UiState.Loading)

        // Wait until the dispatcher is done working
        testDispatcher.scheduler.advanceUntilIdle()

        var result = viewModel.uiState.first()
        assertTrue(result is MainViewModel.UiState.Error)
        assertEquals("Error retrieving data", (result as MainViewModel.UiState.Error).message)

        // Set up a successful load after the initial failure
        val mockData = createMockHiringMatrix()
        coEvery { mockUseCase.invoke() } returns mockData

        viewModel.loadHiringData()
        // Wait until the dispatcher is done working
        testDispatcher.scheduler.advanceUntilIdle()

        result = viewModel.uiState.first()
        assertTrue(result is MainViewModel.UiState.Success)
        assertEquals(mockData, (result as MainViewModel.UiState.Success).result)
    }

    private fun createMockHiringMatrix(id: Int = 1): HiringMatrix {
        return HiringMatrix(
            matrix = listOf(
                HiringSection(
                    listId = id.toString(),
                    data = listOf(
                        Hiring(id = id, listId = id, name = "Test Item $id")
                    )
                )
            )
        )
    }
}