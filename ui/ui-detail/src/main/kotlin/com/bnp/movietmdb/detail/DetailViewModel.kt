package com.bnp.movietmdb.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bnp.movietmdb.domain.model.MovieDetail
import com.bnp.movietmdb.domain.usecase.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    var uiState by mutableStateOf<MovieDetail?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    fun loadMovieDetail(id: Int) {
        viewModelScope.launch {
            isLoading = true
            try {
                uiState = getMovieDetailUseCase(id)
            } catch (e: Exception) {
                // Handle error state
            } finally {
                isLoading = false
            }
        }
    }
}
