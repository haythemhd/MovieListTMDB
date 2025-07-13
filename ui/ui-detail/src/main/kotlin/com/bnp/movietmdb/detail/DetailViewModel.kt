package com.bnp.movietmdb.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bnp.movietmdb.domain.model.MovieDetail
import com.bnp.movietmdb.domain.usecase.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState
    fun loadMovieDetail(id: Int) {
        viewModelScope.launch {
            _uiState.update { DetailUiState(isLoading = true) }
            try {
                val movieDetail = getMovieDetailUseCase(id)
                _uiState.update { currentState ->
                    currentState.copy(
                        movieDetail = movieDetail, isLoading = false, errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false, errorMessage = e.message ?: "An error occurred"
                    )
                }
            }
        }
    }

    fun resetError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

data class DetailUiState(
    val movieDetail: MovieDetail? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
