package com.bnp.movietmdb.data.repository

import com.bnp.movietmdb.data.db.MovieDao
import com.bnp.movietmdb.data.db.entity.MovieEntity
import com.bnp.movietmdb.data.remote.TmdbService
import com.bnp.movietmdb.data.remote.dto.MovieDto
import com.bnp.movietmdb.data.remote.response.PopularMoviesResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryImplTest {
    private val api: TmdbService = mockk()
    private val dao: MovieDao = mockk(relaxed = true)
    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setup() {
        repository = MovieRepositoryImpl(api, dao)
    }

    @Test
    fun `getPopularMovies emits movies from API and updates DB`() = runTest {
        val movieDto1 = MovieDto(1, "title1", "poster1")
        val movieDto2  = MovieDto(2, "title2", "poster2")
        val movieDto3 = MovieDto(3, "title3", "poster3")

        val response = PopularMoviesResponse(listOf(movieDto1, movieDto2, movieDto3))
        val entity1  = MovieEntity(
            id = 1,
            title = "title1",
            posterUrl = "poster1",
            overview = "overview1",
            releaseDate = "2021-01-01",
            voteAverage = 7.5,
            runtime = 120,
            tagline = "tagline1",
            homepage = "https://example.com",
            genres = listOf("Drama"),
            null,
            null,
        )
        coEvery { api.getPopularMovies(any()) } returns response

        coEvery { dao.getAllMovies() } returns listOf(entity1)

        val result = repository.getPopularMovies(1).first()

        assertTrue(result[0].isSeen)
        assertEquals("title1", result[0].title)

    }

    @Test
    fun `getMovieDetail returns local fallback if API fails`() = runTest {
        val localEntity = MovieEntity(
            id = 1,
            title = "Fallback",
            posterUrl = "https://image.tmdb.org/t/p/w500/poster",
            overview = "Fallback overview",
            releaseDate = "2021-01-01",
            voteAverage = 7.5,
            runtime = 120,
            tagline = "Fallback tagline",
            homepage = "https://example.com",
            genres = listOf("Drama"),
            null,
            null,
        )
        coEvery { api.getMovieDetail(any()) } throws IOException()
        coEvery { dao.getMovieById(1) } returns localEntity

        val result = repository.getMovieDetail(1).first()
        assertEquals("Fallback", result.title)
    }
}

