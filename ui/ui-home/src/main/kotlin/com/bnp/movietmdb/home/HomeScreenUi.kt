package com.bnp.movietmdb.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.bnp.movietmdb.domain.model.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUi(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Popular Movies") })
        }
    ) { padding ->
        MovieListContent(
            uiState = uiState,
            onLoadMore = viewModel::loadMovies,
            onRefresh = viewModel::onPullToRefresh,
            padding = padding
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieListContent(
    uiState: HomeUiState,
    onLoadMore: () -> Unit,
    onRefresh: () -> Unit,
    padding: PaddingValues
) {
    when {
        uiState.movies.isEmpty() && uiState.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        else -> {
            val state = rememberPullToRefreshState()

            PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                onRefresh = onRefresh,
                state = state,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.movies) { movie ->
                        MovieItem(movie)
                    }

                    item(span = { GridItemSpan(maxLineSpan) }) {
                        if (uiState.isLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    if (!uiState.isLoading && uiState.canLoadMore) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            LaunchedEffect(Unit) {
                                onLoadMore()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .padding(4.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(movie.posterUrl),
            contentDescription = movie.title,
            modifier = Modifier
                .size(120.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = movie.title ?: "Unknown Title",
            style = MaterialTheme.typography.labelSmall,
            maxLines = 2
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreenUiPreview() {
    val uiState = HomeUiState(
        movies = listOf(
            Movie(id = 1, title = "Movie 1", posterUrl = "https://image.tmdb.org/t/p/w500/pathToPoster1.jpg"),
            Movie(id = 2, title = "Movie 2", posterUrl = "https://image.tmdb.org/t/p/w500/pathToPoster2.jpg")
        )
    )
    MovieListContent(
        uiState = uiState,
        onLoadMore = {},
        onRefresh = {},
        padding = PaddingValues(8.dp)
    )
}