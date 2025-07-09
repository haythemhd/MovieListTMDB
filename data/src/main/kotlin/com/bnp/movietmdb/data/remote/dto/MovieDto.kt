package com.bnp.movietmdb.data.remote.dto

import com.bnp.movietmdb.domain.model.Movie
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val id: Int,
    val title: String?,
    @SerialName("poster_path")
    val posterPath: String?
)

fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    title = title,
    posterUrl = "https://image.tmdb.org/t/p/w500$posterPath"
)