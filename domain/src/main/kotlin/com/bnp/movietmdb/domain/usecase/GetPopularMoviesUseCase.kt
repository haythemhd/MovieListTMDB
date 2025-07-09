package com.bnp.movietmdb.domain.usecase

import com.bnp.movietmdb.domain.model.Movie
import com.bnp.movietmdb.domain.repository.MovieRepository

import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int = 1): List<Movie> {
        return repository.getPopularMovies(page)
    }
}
