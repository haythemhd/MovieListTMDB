package com.bnp.movietmdb.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
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
    val movieList by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Popular Movies") })
        }
    ) { padding ->
        MovieListContent(movieList, padding)
    }
}

@Composable
private fun MovieListContent(
    movieList: List<Movie>,
    padding: PaddingValues
) {
    when {
        movieList.isEmpty() -> {
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movieList) { movie ->
                    MovieItem(movie)
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
    val movieList = listOf(
        Movie(id = 1, title = "Movie 1", posterUrl = "https://image.tmdb.org/t/p/w500/pathToPoster1.jpg" ),
        Movie(id = 2, title = "Movie 2", posterUrl = "https://image.tmdb.org/t/p/w500/pathToPoster2.jpg")
    )
    MovieListContent(
        movieList = movieList,
        padding = PaddingValues(8.dp)
    )
}