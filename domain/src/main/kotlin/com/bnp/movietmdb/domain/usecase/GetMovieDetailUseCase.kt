package com.bnp.movietmdb.domain.usecase

import com.bnp.movietmdb.domain.model.Movie
import com.bnp.movietmdb.domain.model.MovieDetail
import com.bnp.movietmdb.domain.repository.MovieRepository

import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(id: Int): MovieDetail {
        return repository.getMovieDetail(id)
    }
}