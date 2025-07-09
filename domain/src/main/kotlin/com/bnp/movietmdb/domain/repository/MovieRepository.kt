package com.bnp.movietmdb.domain.repository

import com.bnp.movietmdb.domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(age: Int): List<Movie>
}