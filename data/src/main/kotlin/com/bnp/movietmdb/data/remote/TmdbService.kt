package com.bnp.movietmdb.data.remote

import com.bnp.movietmdb.data.remote.response.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): PopularMoviesResponse

}