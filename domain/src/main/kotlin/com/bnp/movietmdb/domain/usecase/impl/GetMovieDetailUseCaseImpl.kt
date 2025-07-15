package com.bnp.movietmdb.domain.usecase.impl

import com.bnp.movietmdb.domain.model.MovieDetail
import com.bnp.movietmdb.domain.repository.MovieRepository
import com.bnp.movietmdb.domain.usecase.GetMovieDetailUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMovieDetailUseCase {
    override operator fun invoke(movieId: Int): Flow<MovieDetail> {
        return repository.getMovieDetail(movieId)
    }
}
