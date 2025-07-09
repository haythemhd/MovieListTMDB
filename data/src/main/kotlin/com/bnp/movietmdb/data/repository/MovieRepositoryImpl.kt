package com.bnp.movietmdb.data.repository

import com.bnp.movietmdb.data.remote.TmdbService
import com.bnp.movietmdb.data.remote.dto.toDomain
import com.bnp.movietmdb.domain.model.Movie
import com.bnp.movietmdb.domain.repository.MovieRepository
import javax.inject.Inject
import kotlin.collections.map

class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbService
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        val apiKey = "1ca7d04c4f37a915e21b57d4e04a6b20"
        val response = api.getPopularMovies(page = page, apiKey = apiKey)
        return response.results.map { it.toDomain() }
    }

}