package com.bnp.movietmdb.domain.usecase

import com.bnp.movietmdb.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface GetPopularMoviesUseCase {
    operator fun invoke(page: Int = 1): Flow<List<Movie>>
}
