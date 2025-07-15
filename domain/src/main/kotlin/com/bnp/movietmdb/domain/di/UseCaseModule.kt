package com.bnp.movietmdb.domain.di

import com.bnp.movietmdb.domain.usecase.GetMovieDetailUseCase
import com.bnp.movietmdb.domain.usecase.GetPopularMoviesUseCase
import com.bnp.movietmdb.domain.usecase.impl.GetMovieDetailUseCaseImpl
import com.bnp.movietmdb.domain.usecase.impl.GetPopularMoviesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindGetPopularMoviesUseCase(
        impl: GetPopularMoviesUseCaseImpl
    ): GetPopularMoviesUseCase

    @Binds
    @Singleton
    abstract fun bindGetMovieDetailUseCase(
        impl: GetMovieDetailUseCaseImpl
    ): GetMovieDetailUseCase
}
