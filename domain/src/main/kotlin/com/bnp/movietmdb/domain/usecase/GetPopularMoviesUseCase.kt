package com.bnp.movietmdb.domain.usecase

import com.bnp.movietmdb.domain.model.Movie
import com.bnp.movietmdb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(page: Int = 1): Flow<List<Movie>> {
        return repository.getPopularMovies(page)
    }
}
