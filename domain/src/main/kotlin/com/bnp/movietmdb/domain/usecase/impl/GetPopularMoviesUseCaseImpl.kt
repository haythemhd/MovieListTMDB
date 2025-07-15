package com.bnp.movietmdb.domain.usecase.impl

import com.bnp.movietmdb.domain.model.Movie
import com.bnp.movietmdb.domain.repository.MovieRepository
import com.bnp.movietmdb.domain.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetPopularMoviesUseCase {
    override operator fun invoke(page: Int): Flow<List<Movie>> {
        return repository.getPopularMovies(page)
    }
}
