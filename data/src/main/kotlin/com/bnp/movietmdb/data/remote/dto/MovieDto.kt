package com.bnp.movietmdb.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val id: Int,
    val title: String?,
    @SerialName("poster_path")
    val posterPath: String?
)
