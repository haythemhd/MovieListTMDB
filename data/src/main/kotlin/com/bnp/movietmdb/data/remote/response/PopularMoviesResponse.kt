package com.bnp.movietmdb.data.remote.response

import com.bnp.movietmdb.data.remote.dto.MovieDto
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class PopularMoviesResponse(
    @SerializedName("results")
    val results: List<MovieDto>
)