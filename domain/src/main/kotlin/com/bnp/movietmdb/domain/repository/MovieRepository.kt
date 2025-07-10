package com.bnp.movietmdb.domain.repository

import com.bnp.movietmdb.domain.model.Movie
import com.bnp.movietmdb.domain.model.MovieDetail

interface MovieRepository {
    suspend fun getPopularMovies(age: Int): List<Movie>
    suspend fun getMovieDetail(id: Int): MovieDetail

}