package com.bnp.movietmdb.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.bnp.movietmdb.common.R
import com.bnp.movietmdb.domain.model.MovieDetail
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenUi(
    movieId: Int, onBackClick: () -> Unit, viewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(movieId) {
        viewModel.loadMovieDetail(movieId)
    }

    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = message, actionLabel = "Dismiss", duration = SnackbarDuration.Short
                )
                viewModel.resetError()
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, topBar = {
        TopAppBar(
            navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable {
                        onBackClick.invoke()
                    })
        },
            title = { Text(text = uiState.movieDetail?.title ?: "") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(id = R.color.brand_primary)
            )
        )
    }) { padding ->

        MovieDetailContent(
            movieDetail = uiState.movieDetail,
            isLoading = uiState.isLoading,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
    }
}

@Composable
fun MovieDetailContent(
    modifier: Modifier,
    movieDetail: MovieDetail?,
    isLoading: Boolean
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            movieDetail?.let {

                movieDetail.posterUrl?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = movieDetail.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                movieDetail.title.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                movieDetail.tagline?.let {
                    Text(
                        text = "\"$it\"",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Release: ${movieDetail.releaseDate}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Runtime: ${movieDetail.runtime} min",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "${movieDetail.voteAverage} / 10",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Genres
                if (movieDetail.genres?.isNotEmpty() == true) {
                    Text(
                        "Genres: ${movieDetail.genres!!.joinToString(", ")}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Countries
                if (!movieDetail.countries.isNullOrEmpty()) {
                    Text(
                        "Countries: ${movieDetail.countries!!.joinToString(", ")}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Production Companies
                if (movieDetail.productionCompanies?.isNotEmpty() == true) {
                    Text(
                        "Production: ${
                            movieDetail.productionCompanies!!.joinToString(
                                ", "
                            )
                        }", style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Overview
                Text("Synopsis", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    movieDetail.overview, style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))


                // Homepage Button
                movieDetail.homepage?.let {
                    if (it.contains("netflix")) {
                        Button(
                            onClick = {
                                // open URL if needed
                            }, colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black, contentColor = Color.Red
                            )
                        ) {
                            Text(
                                "Netflix",
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }
                    } else {
                        Button(
                            onClick = {
                                // open URL if needed
                            }, colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                "Visit Homepage",
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailContentPreview() {
    val movieDetail = MovieDetail(
        id = 1,
        title = "Inception",
        posterUrl = "https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg",
        overview = "Cobb, a skilled thief who commits corporate espionage by infiltrating the subconscious of his targets is offered a chance to regain his old life as payment for a task considered to be impossible: \"inception\", the implantation of another person's idea into a target's subconscious.",
        releaseDate = "2010-07-15",
        voteAverage = 8.3,
        runtime = 148,
        tagline = "Your mind is the scene of the crime.",
        homepage = "https://www.warnerbros.com/movies/inception/",
        genres = listOf("Action", "Science Fiction", "Adventure"),
        countries = listOf("United States of America", "United Kingdom"),
        productionCompanies = listOf("Legendary Pictures", "Syncopy", "Warner Bros. Pictures")
    )
    MovieDetailContent(
        modifier = Modifier,
        movieDetail = movieDetail,
        isLoading = false
    )
}
