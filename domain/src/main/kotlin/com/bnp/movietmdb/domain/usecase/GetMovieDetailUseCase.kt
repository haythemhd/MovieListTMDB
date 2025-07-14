package com.bnp.movietmdb.domain.usecase

import com.bnp.movietmdb.domain.model.MovieDetail
import com.bnp.movietmdb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(id: Int): Flow<MovieDetail> {
        return repository.getMovieDetail(id)
    }
}