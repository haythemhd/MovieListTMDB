package com.bnp.movietmdb.domain.repository

import com.bnp.movietmdb.domain.model.Movie
import com.bnp.movietmdb.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(page: Int): Flow<List<Movie>>
    fun getMovieDetail(id: Int): Flow<MovieDetail>
    fun getMovieDetailFromDB(id: Int): Flow<MovieDetail?>
}