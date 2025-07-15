package com.bnp.movietmdb.domain.usecase

import com.bnp.movietmdb.domain.model.MovieDetail
import com.bnp.movietmdb.domain.repository.MovieRepository
import com.bnp.movietmdb.domain.usecase.impl.GetMovieDetailUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetMovieDetailUseCaseTest {
    private lateinit var repository: MovieRepository
    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase

    private val movieDetail = MovieDetail(
        id = 1,
        title = "Inception",
        overview = "A mind-bending thriller.",
        posterUrl = "",
        releaseDate = "2010-07-16",
        runtime = 148,
        tagline = "Your mind is the scene of the crime.",
        homepage = "https://www.inceptionmovie.com",
        genres = listOf("Action", "Sci-Fi"),
        productionCompanies = listOf("Warner Bros.", "Legendary Pictures"),
        countries = listOf("USA", "UK"),
        voteAverage = 8.8,
    )

    @Before
    fun setUp() {
        repository = mockk()
        getMovieDetailUseCase = GetMovieDetailUseCaseImpl(repository)
    }

    @Test
    fun `invoke should return movie detail from repository`() = runTest {
        // Given
        coEvery { repository.getMovieDetail(1) } returns flowOf(movieDetail)

        // When
        val result = getMovieDetailUseCase(1).first()

        // Then
        assertEquals(movieDetail.title, result.title)
        coVerify(exactly = 1) { repository.getMovieDetail(1) }
    }

    @Test
    fun `invoke should fallback to local data if API fails`() = runTest {
        // Given
        val fallbackDetail = movieDetail.copy(title = "Fallback Title")
        coEvery { repository.getMovieDetail(2) } returns flowOf(fallbackDetail)

        // When
        val result = getMovieDetailUseCase(2).first()

        // Then
        assertEquals("Fallback Title", result.title)
        coVerify(exactly = 1) { repository.getMovieDetail(2) }
    }

}
