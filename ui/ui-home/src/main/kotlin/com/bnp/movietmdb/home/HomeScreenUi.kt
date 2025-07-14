package com.bnp.movietmdb.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.bnp.movietmdb.domain.model.Movie
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.ui.draw.alpha
import androidx.hilt.navigation.compose.hiltViewModel
import com.bnp.movietmdb.common.theme.ThemeViewModel
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.bnp.movietmdb.common.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUi(
    onNavigateToMovieDetail: (id: Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel,

    ) {
    val uiState by viewModel.uiState.collectAsState()
    val isDarkMode by themeViewModel.isDarkMode.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = "Dismiss",
                    duration = SnackbarDuration.Short
                )
                viewModel.resetError()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = colorResource(id = R.color.brand_secondary)
                    )
                },
                actions = {
                    IconButton(onClick = { themeViewModel.toggleTheme() }) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = if (isDarkMode) "Switch to Light Mode" else "Switch to Dark Mode",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.brand_primary)
                )
            )
        }) { padding ->
        MovieListContent(
            uiState = uiState,
            onLoadMore = viewModel::loadMovies,
            onRefresh = viewModel::onPullToRefresh,
            onMovieClick = { movie ->
                viewModel.onMovieClick(movie.id)
                onNavigateToMovieDetail(movie.id)
            },
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
    onMovieClick: (Movie) -> Unit,
    padding: PaddingValues
) {
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
                MovieItem(movie, onClick = { onMovieClick(movie) })
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

@Composable
fun MovieItem(
    movie: Movie, onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .padding(4.dp)
            .clickable(onClick = onClick)

    ) {
        Image(
            painter = rememberAsyncImagePainter(movie.posterUrl),
            contentDescription = movie.title,
            modifier = Modifier
                .size(120.dp)
                .alpha(
                    if (movie.isSeen) 0.5f
                    else 1f

                ),
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
            Movie(
                id = 1,
                title = "Movie 1",
                posterUrl = "https://image.tmdb.org/t/p/w500/pathToPoster1.jpg"
            ), Movie(
                id = 2,
                title = "Movie 2",
                posterUrl = "https://image.tmdb.org/t/p/w500/pathToPoster2.jpg"
            )
        )
    )
    MovieListContent(
        uiState = uiState,
        onLoadMore = {},
        onRefresh = {},
        onMovieClick = {},
        padding = PaddingValues(8.dp)
    )
}