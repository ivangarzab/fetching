package com.ivangarzab.fetching.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ivangarzab.fetching.ui.components.HiringItem
import com.ivangarzab.fetching.domain.model.Hiring
import com.ivangarzab.fetching.domain.model.HiringMatrix
import com.ivangarzab.fetching.domain.model.HiringSection
import com.ivangarzab.fetching.ui.theme.FetchingTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchingTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val mainViewModel: MainViewModel = koinViewModel()

    val uiState by mainViewModel.uiState.collectAsState()

    MainScreenContent(
        modifier = modifier,
        state = uiState,
        onRetryClick = { mainViewModel.loadHiringData() }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    state: MainViewModel.UiState,
    onRetryClick: () -> Unit = { }
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Fetch\nTake-Home Assessment",
                style = MaterialTheme.typography.headlineLarge.copy(
                    textAlign = TextAlign.Center
                )
            )
            when (state) {
                is MainViewModel.UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is MainViewModel.UiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.CenterHorizontally),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Error: ${state.message}")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onRetryClick) {
                            Text("Retry")
                        }
                    }
                }
                is MainViewModel.UiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (section in state.result.matrix) {
                            stickyHeader {
                                Text(
                                    modifier = Modifier
                                        .background(color = MaterialTheme.colorScheme.background)
                                        .fillMaxWidth(),
                                    text = "List ID: ${section.listId}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            items(section.data) {
                                HiringItem(data = it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenContentLoadingPreview() {
    FetchingTheme {
        MainScreenContent(
            state = MainViewModel.UiState.Loading
        )
    }
}

@Preview
@Composable
fun MainScreenContentErrorPreview() {
    FetchingTheme {
        MainScreenContent(
            state = MainViewModel.UiState.Error("Something went wrong")
        )
    }
}

@Preview
@Composable
fun MainScreenContentPreview() {
    FetchingTheme {
        MainScreenContent(
            state = MainViewModel.UiState.Success(mockMatrix)
        )
    }
}

val mockMatrix: HiringMatrix = HiringMatrix(
    matrix = listOf(
        HiringSection("1", listOf(Hiring(1, 1, "Item 1"), Hiring(2, 1, "Item 2"), Hiring(3, 1, "Item 3"))),
        HiringSection("2", listOf(Hiring(4, 2, "Item 4"), Hiring(5, 2, "Item 5"), Hiring(6, 2, "Item 6")))
    )
)