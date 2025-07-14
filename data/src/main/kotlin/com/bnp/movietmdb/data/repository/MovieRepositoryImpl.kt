package com.bnp.movietmdb.data.repository

import com.bnp.movietmdb.data.BuildConfig
import com.bnp.movietmdb.data.db.MovieDao
import com.bnp.movietmdb.data.db.toEntity
import com.bnp.movietmdb.data.db.toDomain
import com.bnp.movietmdb.data.remote.TmdbService
import com.bnp.movietmdb.data.remote.dto.toDomain
import com.bnp.movietmdb.domain.model.Movie
import com.bnp.movietmdb.domain.model.MovieDetail
import com.bnp.movietmdb.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbService, private val movieDao: MovieDao
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        val response = api.getPopularMovies(page = page, apiKey = BuildConfig.TMDB_API_KEY)
        val moviesFromDB = movieDao.getAllMovies().map { it.toDomain() }
        return response.results.map { it.toDomain() }.map { movie ->
            movie.copy(isSeen = moviesFromDB.any { it.id == movie.id })
        }
    }

    override suspend fun getMovieDetail(id: Int): MovieDetail {
        val movieDetail = api.getMovieDetail(id, BuildConfig.TMDB_API_KEY)
        movieDao.insertMovie(movieDetail.toDomain().toEntity())
        return movieDetail.toDomain()
    }

    override suspend fun getMovieDetailFromDB(id: Int): MovieDetail? {
        return movieDao.getMovieById(id)?.toDomain()
    }
}