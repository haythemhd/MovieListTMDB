package com.bnp.movietmdb.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import android.content.Context
import com.bnp.movietmdb.data.db.entity.MovieEntity

@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class MovieDaoTest {

    private lateinit var db: MovieDatabase
    private lateinit var dao: MovieDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.movieDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insert_and_get_movie_by_id() = runTest {
        val entity = MovieEntity(
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
            null
        )

        dao.insertMovie(entity)
        val result = dao.getMovieById(1)

        assertEquals("title1", result?.title)
        assertEquals(listOf("Drama"), result?.genres)
    }
}