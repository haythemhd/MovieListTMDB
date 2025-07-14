package com.bnp.movietmdb.data.repository

import com.bnp.movietmdb.data.db.MovieDao
import com.bnp.movietmdb.data.db.mapper.toDomain
import com.bnp.movietmdb.data.db.mapper.toEntity
import com.bnp.movietmdb.data.remote.TmdbService
import com.bnp.movietmdb.data.remote.mapper.toDomain
import com.bnp.movietmdb.domain.model.Movie
import com.bnp.movietmdb.domain.model.MovieDetail
import com.bnp.movietmdb.domain.repository.MovieRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbService,
    private val movieDao: MovieDao
) : MovieRepository {

    override fun getPopularMovies(page: Int): Flow<List<Movie>> = flow {
        val response = api.getPopularMovies(page = page)
        val moviesFromDB = movieDao.getAllMovies().map { it.toDomain() }
        emit(response.results.map { it.toDomain() }.map { movie ->
            movie.copy(isSeen = moviesFromDB.any { it.id == movie.id })
        })
    }

    override fun getMovieDetail(id: Int): Flow<MovieDetail> = flow {
        try {
            val movieDetail = api.getMovieDetail(id)
            movieDao.insertMovie(movieDetail.toDomain().toEntity())
            emit(movieDetail.toDomain())
        } catch (e: Exception) {
            val local = movieDao.getMovieById(id)?.toDomain()
            if (local != null) {
                emit(local)
            } else {
                throw e
            }
        }
    }

    override fun getMovieDetailFromDB(id: Int): Flow<MovieDetail?> = flow {
        emit(
            movieDao.getMovieById(id)?.toDomain()
        )
    }

}