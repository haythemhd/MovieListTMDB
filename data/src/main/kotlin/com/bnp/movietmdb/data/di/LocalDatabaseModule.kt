package com.bnp.movietmdb.data.di

import android.content.Context
import androidx.room.Room
import com.bnp.movietmdb.data.db.MovieDao
import com.bnp.movietmdb.data.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movies_db"
        ).build()

    @Provides
    fun provideMovieDao(db: MovieDatabase): MovieDao = db.movieDao()
}