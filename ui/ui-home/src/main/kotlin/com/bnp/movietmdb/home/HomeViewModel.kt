package com.bnp.movietmdb.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bnp.movietmdb.domain.model.Movie
import com.bnp.movietmdb.domain.usecase.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadMovies()
    }
    fun loadMovies() {
        if (_uiState.value.isLoading || !_uiState.value.canLoadMore) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val movies = getPopularMoviesUseCase(_uiState.value.currentPage)
                _uiState.update { currentState ->
                    currentState.copy(
                        movies = if (currentState.currentPage == 1) movies else currentState.movies + movies,
                        currentPage = currentState.currentPage + 1,
                        isLoading = false,
                        canLoadMore = movies.isNotEmpty()
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun onPullToRefresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true, currentPage = 1) }
            try {
                val movies = getPopularMoviesUseCase(1)
                _uiState.update {
                    it.copy(
                        movies = movies,
                        currentPage = 2,
                        isRefreshing = false,
                        isLoading = false,
                        canLoadMore = movies.isNotEmpty()
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isRefreshing = false, isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun resetError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

data class HomeUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val currentPage: Int = 1,
    val canLoadMore: Boolean = true,
    val errorMessage: String? = null
)