package com.bnp.movietmdb.domain.usecase

import com.bnp.movietmdb.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailUseCase {
    operator fun invoke(movieId: Int): Flow<MovieDetail>
}