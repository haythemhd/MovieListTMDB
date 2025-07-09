package com.bnp.movietmdb.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bnp.movietmdb.domain.model.Movie
import com.bnp.movietmdb.domain.usecase.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<Movie>>(emptyList())
    val uiState: StateFlow<List<Movie>> = _uiState

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            val movies = getPopularMoviesUseCase()
            _uiState.value = movies
        }
    }
}