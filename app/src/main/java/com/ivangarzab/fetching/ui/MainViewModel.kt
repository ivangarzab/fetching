package com.ivangarzab.fetching.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivangarzab.fetching.domain.model.HiringMatrix
import com.ivangarzab.fetching.domain.usecase.GetHiringDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * The purpose of this [ViewModel] is to take care of the UI logic of the [MainScreen].
 */
class MainViewModel(
    private val getHiringDataUseCase: GetHiringDataUseCase,
) : ViewModel() {

    /**
     * The purpose of both of these [StateFlow] fields is to hold the current state of the UI.
     */
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadHiringData()
    }

    /**
     * The purpose of this function is to load the [HiringMatrix] data by using the [GetHiringDataUseCase].
     */
    fun loadHiringData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getHiringDataUseCase.invoke()?.let { result ->
                onLoadingHiringDataSuccess(result)
            } ?: onLoadingHiringDataFailure()
        }
    }

    private fun onLoadingHiringDataSuccess(result: HiringMatrix) {
//        Log.d("MainViewModel", "Success: $result")
        _uiState.value = UiState.Success(result)
    }

    private fun onLoadingHiringDataFailure() {
//        Log.e("MainViewModel", "Error retrieving data")
        _uiState.value = UiState.Error("Error retrieving data")
    }

    /**
     * This class represents the different states of the UI.
     */
    sealed class UiState {
        data object Loading: UiState()
        data class Success(val result: HiringMatrix): UiState()
        data class Error(val message: String): UiState()
    }
}