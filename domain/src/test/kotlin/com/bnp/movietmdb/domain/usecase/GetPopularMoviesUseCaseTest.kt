package com.bnp.movietmdb.domain.usecase

import com.bnp.movietmdb.domain.model.Movie
import com.bnp.movietmdb.domain.repository.MovieRepository
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
class GetPopularMoviesUseCaseTest {

    private val repository: MovieRepository = mockk()
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Before
    fun setup() {
        getPopularMoviesUseCase = GetPopularMoviesUseCase(repository)
    }

    @Test
    fun `invoke returns movie list from repository`() = runTest {
        // Given
        val movieList = listOf(
            Movie(id = 1, title = "Inception", posterUrl = "", isSeen = false),
            Movie(id = 2, title = "Matrix", posterUrl = "", isSeen = true),
            Movie(id = 3, title = "Interstellar", posterUrl = "", isSeen = false),
        )

        coEvery { repository.getPopularMovies(page = 1) } returns flowOf(movieList)

        // When
        val result = getPopularMoviesUseCase.invoke(page = 1).first()

        // Then
        assertEquals(3, result.size)
        assertEquals("Inception", result[0].title)
        assertEquals(true, result[1].isSeen)

        coVerify(exactly = 1) { repository.getPopularMovies(1) }
    }


}
